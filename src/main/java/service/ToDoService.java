package service;

import model.ToDo;
import repository.ToDoRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Geschäftslogik / Business Logic Layer
public class ToDoService
{
	private final ToDoRepository repo;

	/**
	 * Konstruktor. Ihm wird die Referenz auf die Datenzugriffsschicht übergeben, damit CRUD-Operationen in der dafür vorgesehenen Schicht ausgeführt werden können.
	 * @param repo Referenz auf ein Objekt der Datenzugriffsschicht.
	 */
	public ToDoService(ToDoRepository repo)
	{
		this.repo = repo;
	}

	// Bildschirm-Ausgaben dürfen NUR in der Präsentationsschicht gemacht werden. Fehlermeldungen werden über Exceptions an die Präsentationsschicht weitergeleitet. Ich habe in der Praxis aber auch schon gesehen, dass Fehlermeldungen als Objekte einer Klasse (z.B. Result, welches das Ergebnis, oder die Fehlermeldung beinhaltet) zurückgegeben werden.

	/**
	 * Prüft die übergebenen Daten auf Korrektheit, erzeugt das Objekt und leitet es an die Datenzugriffsschicht weiter.
	 * @param description Die Beschreibung darf nicht leer sein.
	 * @param dueDate Das Fälligkeitsdatum darf leer sein, muss sonst aber dem Format yyyy-mm-dd entsprechen.
	 * @throws IOException Bei Fehlern mit dem Dateizugriff.
	 * @throws IllegalArgumentException Wenn die Beschreibung leer ist, oder das Datum im falschen Format ist.
	 */
	public void addToDo(String description, String dueDate) throws IOException, IllegalArgumentException
	{
		if (description == null || description.isBlank())
		{
			throw new IllegalArgumentException("Beschreibung darf nicht leer sein.");
		}
		if (dueDate != null && !dueDate.isBlank())
		{
			try
			{
				LocalDate d = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);

				// Optional: Datum nicht in der Vergangenheit
			}
			catch (DateTimeParseException e)
			{
				throw new IllegalArgumentException("Fälligkeitsdatum muss yyyy-mm-dd sein.", e);
			}
		}

		int id = nextId();
		String d = dueDate == null || dueDate.isBlank() ? null : dueDate;
		ToDo t = new ToDo(id, description, d, false);

		repo.save(t);
		// Logging hinzufügen
	}

	public List<ToDo> listToDos() throws IOException
	{
		List<ToDo> list = new ArrayList<>(repo.findAll());

		// Sortiert die Liste der To-Do-Objekte.
		// Zuerst wird nach Completed sortiert. Die abgeschlossenen To-dos stehen als Letztes in der Liste.
		// Danach wird nach Datum sortiert. Da ein leeres Datum als Null gespeichert wird, müssen wir hier unbedingt auf Null-Werte prüfen. To-dos ohne Fälligkeitsdatum sollen am Ende stehen.
		// Als Letztes wird noch nach ID sortiert.
		list.sort(Comparator.comparing(ToDo::isCompleted)
				  .thenComparing((o1, o2) -> {
					  if (o1.getDueDate() != null && o2.getDueDate() != null)
						  return o1.getDueDate().compareTo(o2.getDueDate());
					  else if (o1.getDueDate() == null && o2.getDueDate() != null)
						  return 1;
					  else if (o1.getDueDate() != null && o2.getDueDate() == null)
						  return -1;
					  return 0;
				  })
				  .thenComparing(ToDo::getId));

		return list;

	}

	public void completeToDo(int id) throws IOException, IllegalArgumentException
	{
		// Das Repository ist nur dafür da, die Daten abzufragen. Was getan werden soll, wenn die Daten nicht gefunden werden, ist Aufgabe des Services (Geschäftslogik).
		ToDo t = repo.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Nicht gefunden: " + id));

		if (t.isCompleted())
			return;

		t.setCompleted(true);
		repo.save(t);
	}

	public void deleteToDo(int id) throws IOException
	{
		// Optional: Prüfung ob ID überhaupt gültig ist
		repo.deleteById(id);
	}

	/**
	 * Ermittelt die nächste freie ID für To-dos.
	 * Dabei werden alle To-Do-Objekte geladen, der größte ID-Wert ermittelt und dann um 1 erhöht.
	 * @return Die nächste freie ID.
	 * @throws IOException Wenn beim Laden der To-dos ein Fehler auftritt.
	 */
	private int nextId() throws IOException
	{
		// findAll() liefert alle To-Do-Objekte aus der Datei.
		// stream() macht im Prinzip eine Schleife über diese Liste
		// mapToInt() wandelt die Objekte in Integer um
		// max() ermittelt dann den größten Wert dieser Integer
		// mit orElse() können wir angeben was passieren soll, wenn die Liste leer ist
		// das Ergebnis erhöhen wir um 1, weil die ja die nächste ID wollen.
		return repo.findAll().stream().mapToInt(ToDo::getId).max().orElse(0) + 1;
	}

}
