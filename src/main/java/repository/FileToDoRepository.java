package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.ToDo;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Datenzugriff / Data Access Layer
public class FileToDoRepository implements ToDoRepository
{
	// Der Pfad zur Datei. Wird per Konstruktor übergeben.
	private final Path file;

	// Wir verwenden Gson zur Serialisierung der To-Do-Objekte in JSON.
	// Durch "PrettyPrinting" erhalten wir eine JSON-Datei mit Zeilenumbrüchen.
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	// Wir speichern die ganze Liste der To-Do-Objekte in JSON mit Gson.
	// Gson möchte den Datentypen der Liste als TypeToken-Objekt.
	// Das brauchen wir für das Serialisieren und De-Serialisieren.
	private final Type listType = new TypeToken<List<ToDo>>() {}.getType();

	/**
	 * Konstruktor. Er erzeugt benötigte Ordner für die JSON-Datei und bereitet die Datei für die Serialisierung vor.
	 * @param file
	 * @throws IOException Falls benötigte Ordner nicht angelegt werden können, oder die Datei nicht beschrieben werden kann.
	 */
	public FileToDoRepository(Path file) throws IOException
	{
		this.file = file;

		try
		{
			if (Files.notExists(file))
			{
				Files.createDirectories(file.getParent());
				Files.writeString(file, "[]");
			}
		}
		catch (IOException e)
		{
			// Logging hinzufügen
			throw e;
		}
	}

	@Override
	public void save(ToDo toDo) throws IOException
	{
		List<ToDo> all = new ArrayList<>(findAll());
		all.removeIf(t -> t.getId() == toDo.getId());
		all.add(toDo);
		writeAll(all);
	}

	@Override
	public Optional<ToDo> findById(int id) throws IOException
	{
		return findAll().stream().filter(t -> t.getId() == id).findFirst();
	}

	@Override
	public List<ToDo> findAll() throws IOException
	{
		try (Reader r = Files.newBufferedReader(file))
		{
			List<ToDo> list = gson.fromJson(r, listType);
			return list != null ? list : new ArrayList<>();
		}
	}

	@Override
	public void deleteById(int id) throws IOException
	{
		List<ToDo> all = new ArrayList<>(findAll());
		all.removeIf(t -> t.getId() == id);
		writeAll(all);
	}

	/**
	 * Schreibt die Objekte der übergebenen Liste in eine JSON-Datei.
	 * @param list Liste mit To-Do-Objekten.
	 * @throws IOException
	 */
	private void writeAll(List<ToDo> list) throws IOException
	{
		try (Writer w = Files.newBufferedWriter(file))
		{
			gson.toJson(list, listType, w);
		}
	}
}
