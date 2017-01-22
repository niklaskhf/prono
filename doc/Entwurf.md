# Einführung

*Dieser Entwurf legt die prinzipielle Lösungsstruktur fest und enthält alles, was man benötigt, um einem Außenstehenden den prinzipiellen Aufbau der App erklären zu können.* (**keep it simple**)


Die ursprüngliche Idee des Entwurfs ware eine Adaptierung des Model-View-Controller Entwurfmusters. 
Weitere Komponenten wurden anschließend hinzugefügt, um einzelne Zuständigkeitsbereiche abzudecken (z.B. Audio).

Die ursprüngliche GUI-Komponente ist in die einzelnen Komponenten MainView (zuständig für die Darstellung der Kontaktliste/Settings/About/..) und ContactView (zuständig für die Erstellung/Bearbeitung/Einsicht von einzelnen Kontakten).
Die ursprüngliche Controller-Komponente wurde in einen allgemeinen DataController ('Schnittstelle' zur SQL-Datenbank), Audio (zuständig für alles hörbare) und ExportImport (zuständig für das Exportieren/Importieren von Daten) aufgeteilt.
Die Model-Komponente besteht aus einer simplen Verwaltungsklasse der SQL-Datenbank.


Verwendete Entwurfsmuster: Model-View-Controller, Singleton

# Komponentendiagramm

![Komponentendiagramm](sketches/Komponentendiagramm.png)

## Komponente 1: Model

Baustein der Datenhaltung. Stellt Datenbanken für andere Module bereit. Wird von Controller manipuliert.
Datenhaltung beinhaltet: 
- Datenbank für Kontakte
- Sprachprofile
- Einstellungen

## Komponente 2: Controller

Manipuliert Daten aus der Model-Komponente. 
Wird von der View-Komponente aufgerufen, um Aktionen auszuführen. 

## Komponente 3: View

Bildet die Schnittstelle zu dem Benutzer. Zeigt von dem Controller bereitgestellte Daten aus dem Model in GUI an.
Interagiert mit dem Controller, um von dem User gestartete Aktionen auszuführen.

## Externe Komponente 1

Material.io/icons

Verwendung in View um Benutzerfreundlichkeit zu fördern. (Material Design)


# Klassendiagramme

## Klassendiagramm Controller

![Klassendiagramm Controller](sketches/cd_Controller.png)

## Klassendiagramm View
![Klassendiagramm View](sketches/cd_View.png)

## Klassendiagramm Model
![Klassendiagramm Model](sketches/cd_Model.png)


# Beschreibung der wichtigen Klassenhierarchie


## ContactManager:
Manipuliert die Kontakt-Datenbank direkt, stellt Daten für den View bereit. 
"Schnittstelle" zwischen Daten und Darstellung. 
Erstellt Kontakte, editiert Kontakte, löscht Kontakte, provided Cursor.

## NewContactActivity:
Android-Activity.
Erlaubt es dem Nutzer Kontakte zu editieren/erstellen. 
Implementiert wichtige Critical Features "Kontakt erstellen", "Kontakt editieren".
Kontakterstellungs / -editierungs-UI.

## HomeActivity:
Android-Activity.
Kern der View-Komponente.
Implementiert wichtige Critical Features "Kontakte dursuchen", "Kontakte einsehen".
Bildet Ankerpunkt für andere Activities.
UI für Suche, Kontaktliste, Settings, Info.

# GUI-Skizze

![](sketches/Skizze-1.png)
![](sketches/Skizze-2.png)
![](sketches/Skizze-3.png)
![](sketches/Skizze-4.png)
![](sketches/Skizze-5.png)
![](sketches/Skizze-6.png)
