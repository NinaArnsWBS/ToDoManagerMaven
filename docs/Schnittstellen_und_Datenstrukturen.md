
# Definition von Schnittstellen und Datenstrukturen

## Schnittstellen
### `TaskRepository`
CRUD‑ähnliche Methoden zur Persistenz.

### `TaskService`
Geschäftsvorgänge „Add/List/Complete/Delete“ mit Validierung.

## Datenstrukturen
### `ToDo`
- `id:int` – eindeutige ID
- `description:String` – Pflichtfeld
- `dueDate:String|null` – optionales Datum im Format `yyyy-mm-dd`
- `completed:boolean` – Status
