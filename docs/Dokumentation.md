
# Dokumentation der Programmspezifikationen

- **Lastenheft**: siehe `docs/Lastenheft.md`
- **Pflichtenheft**: siehe `docs/Pflichtenheft.md`
- **Architektur**: siehe `docs/Architektur.md`
- **Schnittstellen & Datenstrukturen**: siehe `docs/Schnittstellen_und_Datenstrukturen.md`

## Betrieb
- Start über `app.Main`
- Daten in `data/toDos.json`

## Build
- Maven Projekt: `mvn -q compile exec:java` (falls `exec-maven-plugin` ergänzt wird) oder über IntelliJ „Run Main“

## Hinweise
- JSON-Persistenz via **Gson** (externe Bibliothek über Maven)
