
# Modellierung der Softwarearchitektur

```
Benutzereingabe (Konsole)
     ↓
ui/ConsoleUI (nimmt Eingaben entgegen)
     ↓
service/ToDoService (prüft Regeln & verarbeitet)
     ↓
repository/FileToDoRepository (liest/schreibt JSON)
     ↓
service/ToDoService (bereitet Ergebnis vor)
     ↓
ui/ConsoleUI (zeigt Ergebnis an)
```

Paketstruktur in IntelliJ:
```
src/main/java/
  ui/
  service/
  repository/
  model/
  app/
```
