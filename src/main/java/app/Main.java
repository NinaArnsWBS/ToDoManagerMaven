package app;

import repository.FileToDoRepository;
import repository.ToDoRepository;
import service.ToDoService;
import ui.ConsoleUI;

import java.io.IOException;
import java.nio.file.Path;

// https://www.jetbrains.com/help/idea/maven-support.html
// http://google.github.io/gson/UserGuide.html

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			// Zuerst erstellen wir die Datenhaltungsschicht, da wir diese an die Geschäftslogikschicht übergeben müssen.
			ToDoRepository repo = new FileToDoRepository(Path.of("data", "toDos.json"));

			// Dann erstellen wir die Geschäftslogikschicht, da wir diese an die Präsentationsschicht übergeben müssen.
			ToDoService service = new ToDoService(repo);

			// Als Letztes erstellen wir die Präsentationsschicht.
			ConsoleUI ui = new ConsoleUI(service);

			// Die run()-Methode zeigt das Hauptmenü an.
			ui.run();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
