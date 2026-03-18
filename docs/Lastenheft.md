
# Lastenheft – To-Do-Listen‑Manager (Konsole)

## Zielsetzung
Der To-Do-Manager ermöglicht Nutzerinnen und Nutzern das Anlegen, Auflisten, Markieren und Löschen von Aufgaben.   
Die Anwendung läuft lokal als Konsolenprogramm.

## Produktleistungen (Was?)
- Aufgaben anlegen mit Beschreibung und optionalem Fälligkeitsdatum (yyyy‑mm‑dd)
- Aufgaben anzeigen (mit Status „erledigt“/„offen“)
- Aufgabe als erledigt markieren
- Aufgabe löschen
- Persistente Speicherung auf dem lokalen Dateisystem

## Nutzer und Umfeld
- Einzelarbeitsplatz, Windows/macOS/Linux, Java17
- Keine Netzwerkverbindung notwendig

## Qualitätsanforderungen (nicht-funktional)
- Datenpersistenz in einer lesbaren JSON-Datei
- Startzeit < 2Sek., Antwortzeit für Standardoperationen < 200ms
- Robuste Validierung von Eingaben (z.B. Datumsformat)

## Randbedingungen
- IntelliJ IDEA Community, Maven als Build-Tool
- Externe Bibliothek: **Gson** für JSON-Persistenz

## Abgrenzung
- Keine Benutzerverwaltung, keine Prioritäten, keine Wiederholungen

## Abnahme-/Akzeptanzkriterien
- Alle oben genannten Funktionen sind über die Konsole bedienbar
- Daten überdauern Programmneustart (JSON-Datei vorhanden/aktualisiert)
