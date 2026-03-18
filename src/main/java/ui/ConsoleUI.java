package ui;

import model.ToDo;
import service.ToDoService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Darstellung / Presentation Layer
// Alle Ausgaben und Benutzer-Eingaben in unserem Programm geschehen NUR über die Darstellungsschicht!

public class ConsoleUI
{
	private final ToDoService service;
	private final Scanner scanner = new Scanner(System.in);

	/**
	 * Konstruktor. Ihm wird die Referenz auf die Geschäftslogikschicht übergeben, damit Interaktionen mit der UI an die Geschäftslogik weitergegeben werden können.
	 * @param service Referenz auf ein Objekt der Geschäftslogikschicht.
	 */
	public ConsoleUI(ToDoService service)
	{
		this.service = service;
	}

	/**
	 * Diese Methode startet das eigentliche Programm und zeigt das Hauptmenü an.
	 */
	public void run()
	{
		while (true)
		{
			System.out.print("""
			==== To-Do-Manager ====
			1) Aufgabe anlegen
			2) Aufgaben anzeigen
			3) Aufgabe erledigt markieren
			4) Aufgabe löschen
			0) Ende
			Auswahl:\s""");
			String choice = scanner.nextLine().trim();

			switch (choice) // Switch-Expression
			{
				case "1" -> addToDo();
				case "2" -> listToDos();
				case "3" -> completeToDo();
				case "4" -> deleteToDo();
				case "0" ->
				{
					System.out.println("Tschüss!");
					return;
				}
				default -> System.out.println("Ungültige Auswahl.");
			}
		}
	}

	private void addToDo()
	{
		System.out.print("Beschreibung: ");
		String description = scanner.nextLine().trim();
		System.out.print("Fälligkeitsdatum (yyyy-mm-dd, optional, Enter für leer): ");
		String dueDate = scanner.nextLine().trim();

		try
		{
			service.addToDo(description, dueDate);
			System.out.println("Angelegt.");
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("Fehler beim Anlegen.");
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Laden oder Speichern.");
			System.out.println(e.getMessage());
		}
	}

	private void listToDos()
	{
		try
		{
			List<ToDo> toDos = service.listToDos();

			if (toDos.isEmpty())
			{
				System.out.println("Keine Aufgaben vorhanden.");
				return;
			}

			// Formatierte Ausgabe als Tabelle
			System.out.println("ID | Erledigt | Fällig bis | Beschreibung ");
			for (ToDo t : toDos)
			{
				System.out.printf("%2d | %-8s | %-10s | %-12s%n",
								  t.getId(), t.isCompleted() ? "X" : " ",
								  t.getDueDate() == null ? "-" : t.getDueDate(),
								  t.getDescription());
			}
			System.out.println();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Laden oder Speichern.");
			System.out.println(e.getMessage());
		}
	}

	private void completeToDo()
	{
		try
		{
			System.out.print("ID : ");
			int id = Integer.parseInt(scanner.nextLine());
			service.completeToDo(id);
			System.out.println("Erledigt markiert.");
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Laden oder Speichern.");
			System.out.println(e.getMessage());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabe war keine Zahl.");
		}
	}

	private void deleteToDo()
	{
		try
		{
			System.out.print("ID : ");
			int id = Integer.parseInt(scanner.nextLine());
			service.deleteToDo(id);
			System.out.println("Gelöscht.");
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Laden oder Speichern.");
			System.out.println(e.getMessage());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Eingabe war keine Zahl.");
		}
	}
}
