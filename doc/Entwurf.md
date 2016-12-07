# Einführung

*Dieser Entwurf legt die prinzipielle Lösungsstruktur fest und enthält alles, was man benötigt, um einem Außenstehenden den prinzipiellen Aufbau der App erklären zu können.* (**keep it simple**)

Die Datenbank (Model) liefert den Controller und dem View Informationen. Der Kontroller steuert den Ablauf des Programms. Der View dient zur Darstellung der Daten.

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


# Klassendiagramm

![Klassendiagramm Controller](sketches/cd_Controller.png)
![Klassendiagramm View](sketches/cd_View.png)
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
