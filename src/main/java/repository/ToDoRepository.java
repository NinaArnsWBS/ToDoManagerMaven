package repository;

import model.ToDo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// Wir verwenden ein Interface, damit die Datenhaltung einfach ausgetauscht werden kann. JSON, CSV, XML, Relationale oder Nicht-Relationale Datenbank, zum Beispiel.
public interface ToDoRepository
{
	void save(ToDo toDo) throws IOException;

	// Rückgabe 'Optional', da wir in der Implementierung das Stream-Interface verwenden. Bei einigen Methoden dieses Stream-Interfaces ist die Rückgabe 'Optional'. Das heißt: Wurde das gesuchte Element nicht gefunden, wird ein leeres 'Optional' zurückgegeben. Alternativ könnte man auch einfach null zurückgeben, aber das wird manchmal als schlechter Stil angesehen, weil es leicht zu NullPointer Exceptions führen kann.
	Optional<ToDo> findById(int id) throws IOException;
	List<ToDo> findAll() throws IOException;
	void deleteById(int id) throws IOException;

}
