# Product Backlog


Hier werden **alle** Anforderungen in Form von **User Stories** geordnet aufgelistet.


## Epic 1 *Aufnahme-/Speicher- und Abspiel-Möglichkeit für die Aussprache von Namen*


> Als *Vertriebsmitarbeiter* möchte ich *die Aussprache von Namen aufnehmen/speichern und abspielen* können, um *jederzeit Zugriff auf die korrekte Aussprache zu haben*.


- Aufwandsschätzung: XL
- Akzeptanztests: 
    - [ ] Audio-Aufnahme der Aussprache kann mit der Angabe des Titel, Namen (Vor-/Zweit-/Nachname) und der Region (mind. Land), sowie Geschlecht gespeichert werden
    - [ ] Die Audio-Aufnahme muss nach dem vollständigen Schließen der App gespeichert bleiben
    - [ ] Die gespeicherten Audio-Aufnahmen können in der App abgespielt werden


- Enthaltene Features:
    - Aufnehmen von Aussprache
    - Eintrag erstellen
    - Eintrag einsehen
    - Aufnahme abspielen






### Feature 1 *Aufnehmen von Aussprache*


> Als *Vertriebsmitarbeiter* möchte ich *die Aussprache von Namen aufnehmen können*, um *eine temporäre Audio-Aufnahme abspielen zu können*.


- Aufwandsschätzung: M-L
- Akzeptanztests:
    - [ ] Audio-Aufnahme muss abspielbar sein


- Enthaltene Implementable Stories:
    - Temporäre Aufnahme erstellen
    - Temporäre Aufnahme speichern
    - Temporäre Aufnahme verwerfen
    - Temporäre Aufnahme abspielen




### Implementable Story 1 *Temporäre Aufnahme erstellen*


> Als *Vertriebsmitarbeiter* möchte ich *eine temporäre Aufnahme erstellen*, um *diese später speichern zu können*.


- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Temporäre Aufnahme ist im Speicher vorhanden


- Enthaltene Tasks:
    - Aufnahme starten
    - Aufnahme aufzeichnen
    - Aufnahme stoppen
    

    
### Task 1 *Aufnahme starten*

> Als *Vertriebsmitarbeiter* möchte ich *die Aufnahme starten*, um *die Aussprache eines Kontaktes im Android-Gerät zu speichern*.


- Aufwandsschätzung: 4 Stunden 
- Tatsächliche Zeit:




### Task 2 *Aufnahme aufzeichnen*

> Als *Vertriebsmitarbeiter* möchte ich *die Aufnahme aufzeichnen*, um *sie später abhören zu können*.


- Aufwandsschätzung: 6 Stunden 
- Tatsächliche Zeit:


### Task 3 *Aufnahme stoppen*

> Als *Vertriebsmitarbeiter* möchte ich *die Aufnahme stoppen*, um *sie nicht unnötig lang zu machen*.


- Aufwandsschätzung: 4 Stunden 
- Tatsächliche Zeit:






   
### Implementable Story 2 *Temporäre Aufnahme verwerfen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme verwerfen*, um *eine neue Aufnahme erstellen zu können*.


- Aufwandsschätzung: 20
- Akzeptanztests:
    - [ ] Aufnahme ist nicht mehr vorhanden
    - [ ] Aufforderung zu neuer Aufnahme


- Enthaltene Tasks:
    - Verwerfen-Button klicken
    - Verwerfen bestätigen




### Task 1 *Verwerfen-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *auf den Verwerfen-Button klicken*, um *eine qualitativ schlechte Aufnahme zu verwerfen*.


- Aufwandsschätzung: 4 Stunden 
- Tatsächliche Zeit:






### Task 2 *Verwerfen bestätigen*

> Als *Vertriebsmitarbeiter* möchte ich *auf den Verwerfen-bestätigen Button klicken*, um *sicher zu gehen, dass man sich zuvor nicht aus versehen auf verwerfen geklickt hat*.


- Aufwandsschätzung: 3 Stunden 
- Tatsächliche Zeit:




### Implementable Story 3 *Temporäre Aufnahme speichern*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme speichern*, um *die aufgenommene Aufnahme speichern zu können*.


- Aufwandsschätzung: 20
- Akzeptanztests:
    - [ ] Aufnahme ist im temporären Speicher vorhanden


- Enthaltene Tasks:
    - Stopp-Button klicken (automatische Speicherung nach Betätigung des Buttons


### Task 1 *Stopp-Button klicken (automatische Speicherung nach Betätigung des Buttons)*

> Als *Vertriebsmitarbeiter* möchte ich *auf Aufnahme stoppen klicken*, um *sie später abhören zu können*.


- Aufwandsschätzung: 2 Stunden 
- Tatsächliche Zeit:




### Implementable Story 4 *Temporäre Aufnahme akzeptieren*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme akzeptieren*, um *sie danach in einem Eintrag speichern zu können*.


- Aufwandsschätzung: 10
- Akzeptanztests:
    - [ ] Temporäre Aufnahme nicht gelöscht
    - [ ] Temporäre Aufname ist im temporärem Speicher
    
- Enthaltene Tasks:
    - Klicke auf Accept-Button




### Task 1 *Klicke auf Accept-Button*

> Als *Vertriebsmitarbeiter* möchte ich *eine temporäre Aufname akzeptieren*, um *sie im Android-Gerät gespeichert zu haben*.


- Aufwandsschätzung: 6 Stunden 
- Tatsächliche Zeit:




### Implementable Story 5 *Temporäre Aufnahme abspielen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme abspielen*, um *sie auf Qualität zu testen*.


- Aufwandsschätzung: 20
- Akzeptanztests:
    - [ ] Temporäre Aufnahme wird von dem Android-Gerät einmal abgespielt 
    
- Enthaltene Tasks:
    - Klicke auf Play-Button




### Task 1 *Klicke auf Play-Button*

> Als *Vertriebsmitarbeiter* möchte ich *auf den Play-Button klicken*, um *überprüfen zu können, ob die Aufnahme den Erwartungen entspricht*.


- Aufwandsschätzung:  Stunden 
- Tatsächliche Zeit:
















### Feature 2 *Eintrag erstellen*


> Als *Vertriebsmitarbeiter* möchte ich *einen Eintrag mit Titel, Vor-/Zweit-/Nachnamen, Geschlecht, Aussprache und Land erstellen*, um *die Informationen später verfügbar zu haben*.


- Aufwandsschätzung: L 
- Akzeptanztests:
    - [ ] Eintrag muss nach Schließen der App noch existieren
    - [ ] Eintrag muss mindestens Nachname und Aussprache enthalten


- Enthaltene Implementable Stories:
    - Editor öffnen 
    - Informationen eingeben
    - Eintrag speichern
    - Aufnahme speichern, siehe Implementable Story 'Temporäre Aufnahme erstellen'
 
### Implementable Story 1 *Editor öffnen*
 
> Als *Vertriebsmitarbeiter* möchte ich *den Editor öffnen*, um *einen neuen Eintrag zu erstellen*.


- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Ein neuer Eintrag kann erstellt werden
    - [ ] Android-Gerät-Bildschirm zeigt Editor
    
- Enthaltene Tasks:
    - Klicke auf Create-Button




### Task 1 *Klicke auf Create-Button*

> Als *Vertriebsmitarbeiter* möchte ich *einen neuen Eintrag erstellen können*, um *diesen in meiner Datenbank hinzufügen zu können*.


- Aufwandsschätzung: 6 Stunden 
- Tatsächliche Zeit:




### Implementable Story 2 *Informationen eingeben*
 
> Als *Vertriebsmitarbeiter* möchte ich *in dem Editor Informationen über den Kontakt eingeben*, um *diese später zu speichern*.


- Aufwandsschätzung: 5
- Akzeptanztests:
    - [ ] Eingabe wird angezeigt
    
- Enthaltene Tasks:
    - Gib Informationen ein




### Task 1 *Gib Informationen ein*

> Als *Vertriebsmitarbeiter* möchte ich *meinen Kontakten Daten zuweisen*, um *sie später abrufen zu können*.


- Aufwandsschätzung: 2 Stunden 
- Tatsächliche Zeit:






### Implementable Story 3 *Eintrag speichern*
 
> Als *Vertriebsmitarbeiter* möchte ich *die eingegebenen Informationen speichern*, um *später auf sie zugreifen zu können*.


- Aufwandsschätzung: 90 
- Akzeptanztests:
    - [ ] Eintrag ist in permanenten Speicher vorhanden
    - [ ] Eintrag ist auch nach Neustart des Geräts verfügbar


- Enthaltene Tasks:
    - Save-Button klicken






### Task 1 *Save-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *meinen neu erstellten Kontakt speichern*, um *ihn permanent gespeichert zu haben*.


- Aufwandsschätzung: 12 Stunden 
- Tatsächliche Zeit:


















### Feature 3 *Eintrag einsehen*


> Als *Vertriebsmitarbeiter* möchte ich *einen existierenden Eintrag aufrufen*, um *die gespeicherten Informationen einzusehen*.


- Aufwandsschätzung: M 
- Akzeptanztests:
    - [ ] Navigierbare Liste aller Einträge ist navigierbar
    - [ ] Kontakt wird auf Android-Geraet-Display sichtbar
 
- Enthaltene Implementable Stories:
    - Einträge in Liste anzeigen
    - Eintrag öffnen
    - Eintrag schließen
    
### Implementable Story 1 *Einträge in Liste anzeigen*
 
> Als *Vertriebsmitarbeiter* möchte ich *die existierenden Einträge in einer Liste sehen*, um *sie öffnen zu können*.


- Aufwandsschätzung: 40
- Akzeptanztests:
    - [ ] Alle existierenden Einträge werden in Listenform auf Android-Gerät-Bildschirm angezeigt


- Enthaltene Tasks:
    - Einträge anzeigen




### Task 1 *Einträge anzeigen*

> Als *Vertriebsmitarbeiter* möchte ich *meine Einträge einsehen*, um *Informationen von ihnen zu bekommen*.


- Aufwandsschätzung: 4 Stunden 
- Tatsächliche Zeit:






### Implementable Story 2 *Eintrag öffnen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen Eintrag öffnen*, um *die gespeicherten Informationen einzusehen*.


- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Android-Gerät-Bildschirm zeigt den Eintrag an 


- Enthaltene Tasks:
    - Auf Eintrag klicken




### Task 1 *Auf Eintrag klicken*

> Als *Vertriebsmitarbeiter* möchte ich *auf einen Eintrag klicken*, um *genauere Informationen von ihm zu bekommen*.


- Aufwandsschätzung: 2 Stunden 
- Tatsächliche Zeit:




### Implementable Story 3 *Eintrag schließen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen geöffneten Eintrag schließen*, um *auf andere Einträge zugreifen zu können*.


- Aufwandsschätzung: 10
- Akzeptanztests:
    - [ ] Android-Gerät-Bildschirm nimmt Zustand vor Öffnen an 


- Enthaltene Tasks:
    - Zurück-Button klicken




### Task 1 *Zurück-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Zurück-Button klicken*, um *auf die HomePage zurückzukehren*.


- Aufwandsschätzung: 2 Stunden
- Tatsächliche Zeit: 


















### Feature 4 *Aufnahme abspielen*


> Als *Vertriebsmitarbeiter* möchte ich *eine gespeicherte Aufnahme abspielen*, um *die korrekte Aussprache eines Namen zu erfahren*.


- Aufwandsschätzung: S
- Akzeptanztests: 
   - [ ] Das Android-Gerät spielt die Aufnahme ab.
 
- Enthaltene Implementable Stories:
    - Gespeicherte Datei abspielen
 




### Implementable Story 1 *Gespeicherte Datei abspielen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine zu einem Eintrag assoziierte Aussprache abspielen*, um *die Person richtig anreden zu können*.


- Aufwandsschätzung: 40
- Akzeptanztests:
    - [ ] Android-Gerät spielt vorher gespeicherte Aussprache genau einmal ab


- Enthaltene Tasks:
    - Eintrag finden
    - Play-Button klicken




### Task 1 *Eintrag finden*

> Als *Vertriebsmitarbeiter* möchte ich *einen Eintrag in der angezeigten Liste finden*, um *die dazugehörige Aussprache abspielen zu können*.

- Aufwandsschätzung: 1 Stunden
- Tatsächliche Zeit: 




### Task 2 *Play-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *auf den Play-Button klicken*, um *die Aussprache abzuspielen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 






























## Epic 2 *Existierenden Eintrag editieren/löschen/verwalten/durchsuchen*


> Als *Vertriebsmitarbeiter* möchte ich *die bereits existierenden Einträge editieren/löschen/verwalten*, um *Informationen zu ändern/korrigieren*.


- Enthaltene Features:
    - Eintrag editieren
    - Eintrag löschen
    - Einträge durchsuchen
    - Einträge sortieren
    - Einträge filtern
    - Sprache anpassen
    - Favoriten verwalten
    - Letzte Aktion rückgängig machen


- Aufwandsschätzung: XL
- Akzeptanztests: 
   - [ ] Audio-Aufnahmen können nach Namen dursucht werden
   - [ ] Audio-Aufnahmen können erstellt werden, siehe Epic 1
   - [ ] Audio-Aufnahmen können gelöscht werden
   - [ ] Audio-Aufnahmen können bearbeitet werden (Name/Land/... und Aufnahme selbst)
   - [ ] Audio-Aufnahmen können gefiltert werden
   - [ ] Oberflächensprache kann angepasst werden
   - [ ] Favoriten können verwalten werden
   - [ ] Editier/Lösch-Aktionen können rückgängig gemacht werden






### Feature 5 *Eintrag editieren*


> Als *Vertriebsmitarbeiter* möchte ich *in einem existierenden Eintrag gespeicherte Informationen Titel, (Vor-/Zweit-/Nachname, Geschlecht, Land und Aussprache) edtieren*, um *die Informationen zu aktualisieren*.


- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Eintrag auch nach Schließen der App aktualisiert


- Enthaltene Implementable Stories:
    - Eintrag öffnen, siehe Feature 'Eintrag einsehen'
    - Editiermodus starten
    - Informationen ändern
    - Eintrag speichern


### Implementable Story 1 *Editiermodus starten*
 
> Als *Vertriebsmitarbeiter* möchte ich *den Editiermodus starten*, um *Informationen und Aussprache eines Eintrags zu verändern*.


- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Android-Gerät-Bildschirm zeigt Editor 
    - [ ] Informationen lassen sich in Eingabefeldern ändern


- Enthaltene Tasks:
    - Edit-Button klicken


### Task 1 *Edit-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Edit-Button klicken*, um *den Editiermodus zu starten*.


- Aufwandsschätzung: 4 Stunden 
- Tatsächliche Zeit:




### Implementable Story 2 *Informationen ändern*
 
> Als *Vertriebsmitarbeiter* möchte ich *bereits gespeicherte Informationen ändern*, um *sie zu korrigieren*.


- Aufwandsschätzung: 15
- Akzeptanztests:


    - [ ] Die Eingabefelder zeigen veränderte Informationen an
    - [ ] Die geänderten Informationen befinden sich im flüchtigen Speicher 


- Enthaltene Tasks:
    - Informationen ändern
    - Aussprache ändern


### Task 1 *Informationen ändern*

> Als *Vertriebsmitarbeiter* möchte ich *die Informationen einen Eintrags ändern*, um *den Kontakt zu aktualisieren*.


- Aufwandsschätzung: 3 Stunden 
- Tatsächliche Zeit: 


### Task 2 *Aussprache ändern*

> Als *Vertriebsmitarbeiter* möchte ich *die Aussprache ändern*, um *die vorhandene Aussprache zu aktualisieren*.


- Aufwandsschätzung: 4 Stunden 
- Tatsächliche Zeit:




### Implementable Story 3 *Eintrag speichern*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen veränderten Eintrag speichern*, um *die veränderten Informationen beizubehalten*.


- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Der alte Eintrag wurde durch den veränderten Eintrag im permanenten Speicher ersetzt


- Enthaltene Tasks:
    - Save-Button klicken


### Task 1 *Save-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Save-Button klicken*, um *den aktualisierten Eintrag zu speichern*.

- Aufwandsschätzung: 6 Stunden 
- Tatsächliche Zeit:














### Feature 6 *Eintrag löschen*


> Als *Vertriebsmitarbeiter* möchte ich *einen existierenden Eintrag löschen*, um *den Eintrag nicht mehr angezeigt zu bekommen*.


- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Eintrag ist nach Ablauf des Countdowns für die Möglichkeit der Rückgängigoption (Feature 13) permanent von dem Android-Geräte-Speicher gelöscht
    - [ ] Eintrag wird nicht mehr in Listen angezeigt
    - [ ] Eintrag ist nicht Ergebnis von Suche
    
- Enthaltene Implementable Stories:
    - Eintrag öffnen, siehe Implementable Story in Feature 'Eintrag einsehen', Epic 1 
    - Eintrag löschen


### Implementable Story 1 *Eintrag löschen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen existierenden Eintrag löschen*, um *ihn nicht mehr verfügbar zu haben*.


- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Eintrag ist nach Ablauf des Countdowns für die Möglichkeit der Rückgängigoption (Feature 13) permanent von dem Android-Geräte-Speicher gelöscht
    - [ ] Eintrag wird nicht mehr in Listen angezeigt
    - [ ] Eintrag ist nicht Ergebnis von Suche
    
- Enthaltene Tasks:
    - Delete-Button klicken
    - Löschen Bestätigen


### Task 1 *Delete-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Delete-Button klicken*, um *den ausgewählten Eintrag zu löschen*.


- Aufwandsschätzung: 6 Stunden 
- Tatsächliche Zeit:


# Task 2 *Löschen bestätigen*


> Als *Vertriebsmitarbeiter* möchte ich *den Löschvorgang bestätigen müssen*, um *den Eintrag nicht aus Versehen gelöscht zu haben*.


- Aufwandsschätzung: 2 Stunden 
- Tatsächliche Zeit:






















### Feature 7 *Einträge durchsuchen*


> Als *Vertriebsmitarbeiter* möchte ich *die Einträge durch Eingabe des Namen durchsuchen*, um *meinen gesuchten Eintrag zu finden*.


- Aufwandsschätzung: L
- Akzeptanztests: 
    - [ ] Nach Eingabe einer Suche werden alle Einträge, die mit der Suche übereinstimmen, angezeigt
    


- Enthaltene Implementable Stories:
    - Suche durchführen
    - Suche abbrechen
    
    

### Implementable Story 1 *Suche durchführen* 
 
> Als *Vertriebsmitarbeiter* möchte ich *einen Suchbegriff eingeben*, um *alle Einträge danach zu durchsuchen*.


- Aufwandsschätzung: 75
- Akzeptanztests:
    - [ ] Suchbegriff wird erfolgreich gelesen
    - [ ] Alle Einträge, die mit dem Suchbegriff übereinstimmen, werden angezeigt


- Enthaltene Tasks:
    - Suchbegriff eingeben
    - Suche starten




### Task 1 *Suchbegriff eingeben*

> Als *Vertriebsmitarbeiter* möchte ich *einen Suchbegriff eingeben*, um *die Einträge nach diesem zu durchsuchen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Task 2 *Suche starten*

> Als *Vertriebsmitarbeiter* möchte ich *die Suche starten*, um *Einträge, die dem Suchbegriff entsprechen, angezeigt zu bekommen*.


- Aufwandsschätzung: 8 Stunden
- Tatsächliche Zeit: 




### Implementable Story 2 *Suche verlassen*
 
> Als *Vertriebsmitarbeiter* möchte ich *die Suche verlassen*, um *die vorherigen Einträge angezeigt zu bekommen*.


- Aufwandsschätzung: 5
- Akzeptanztests:
    - [ ] Vorheriger Bildschirm wird angezeigt 


- Enthaltene Tasks:
    - Zurück-Button klicken








### Task 1 *Zurück-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Zurück-Button klicken*, um *die Suche abzubrechen*.


- Aufwandsschätzung: 2 Stunden
- Tatsächliche Zeit: 














### Feature 8 *Einträge sortieren*


> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach einem Kriterium alphabetisch sortieren*, um *meine gewünschte Reihenfolge angezeigt zu bekommen*.


- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Die Liste ist nach ausgewählten Kriterium (Vorname/Zweitname/Nachname/Land/Geschlecht)  sortiert


- Enthaltene Implementable Stories:
    - Einträge nach gewünschtem Kriterium sortieren


### Implementable Story 1 *Einträge nach gewünschtem Kriterium sortieren*
 
> Als *Vertriebsmitarbeiter* möchte ich *die Einträge sortieren*, um *eine bessere Übersicht zu haben*.


- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Angezeigte Reihenfolge der Einträge entspricht Kriterium (Vorname/Zweitname/Nachname/Land/Geschlecht) 


- Enthaltene Tasks:
    - Nach Vornamen sortieren
    - Nach Nachnamen sortieren
    - Nach Land sortieren




### Task 1 *Nach Vornamen sortieren*


> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach Vornamen sortieren*, um *einen Kontakt schneller finden zu können*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Task 2 *Nach Nachnamen sortieren*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach Nachnamen sortieren*, um *einen Kontakt schneller finden zu können*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Task 3 *Nach Land sortieren*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach Land sortieren*, um *einen Kontakt schneller finden zu können*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 
















### Feature 9 *Einträge filtern*


> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach einem Filter-Kriterium anpassen*, um *diese nach dem ausgewählten Filter-Kriterium entsprechend angezeigt zu bekommen*.


- Aufwandsschätzung: M
- Akzeptanztests: 
	- [ ] Einträge erfolgreich nach Land gefiltert
	- [ ] Einträge erfolgreich nach Geschlecht gefiltert
    - [ ] Alle angezeigten Einträge entsprechen dem Filter-Kriterium
    - [ ] Einträge, die nicht dem Filter-Kriterium entsprechen, werden nicht angezeigt 


- Enthaltene Implementable Stories:
    - Einträge nach Kriterium filtern


### Implementable Story 2 *Einträge nach Kriterium filtern*
 
> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach einem Kriterium filtern*, um *nur gewünschte Einträge angezeigt zu bekommen*.


- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Alle angezeigten Einträge entsprechen dem Kriterium


- Enthaltene Tasks:
    - Einträge nach Land filtern
    - Einträge nach Geschlecht filtern
    - Einträge nach Favoriten filtern, siehe Feature 'Favoriten verwalten'




### Task 1 *Einträge nach Land filtern*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach Land filtern*, um *nur Einträge des entsprechenden Landes angezeigt zu bekommen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Task 2 *Einträge nach Geschlecht filtern*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach Geschlecht filtern*, um *nur Einträge des entsprechenden Geschlechtes angezeigt zu bekommen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 










### Feature 10 *Sprache anpassen*


> Als *Vertriebsmitarbeiter* möchte ich *die Sprache der Oberfläche ändern*, um *eine andere Sprache zu lesen*.


- Aufwandsschätzung: L
- Akzeptanztests: 
    - [ ] Alle Oberflächenelemente sind in der ausgewählten Sprache
 
- Enthaltene Implementable Stories:
    - Einstellungen öffnen 
    - Sprache auswählen
    
    

### Implementable Story 1 *Einstellungen öffnen*
 
> Als *Vertriebsmitarbeiter* möchte ich *das Einstellungs-Menü öffnen*, um *sie zu verändern*.


- Aufwandsschätzung: 35
- Akzeptanztests:
    - [ ] Einstellungs-Menü wird angezeigt 


- Enthaltene Tasks:
    - Auf Settings-Button klicken






### Task 1 *Auf Settings-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *auf den Settings-Button klicken*, um *Einstellungen verändern zu können*.


- Aufwandsschätzung: 3 Stunden
- Tatsächliche Zeit: 


### Implementable Story 2 *Sprache auswählen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine Sprache auswählen*, um *die Oberflächensprache zu verändern*.


- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Die Oberflächensprache entspricht der Auswahl 


- Enthaltene Tasks:
    - Englisch auswählen
    - Deutsch auswählen
    - Einstellungs-Menü schließen
    


### Task 1 *Englisch auswählen*

> Als *Vertriebsmitarbeiter* möchte ich *das Sprachprofil Englisch auswählen*, um *die Oberfläche in englischer Sprache zu sehen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Task 2 *Deutsch auswählen*

> Als *Vertriebsmitarbeiter* möchte ich *das Sprachprofil Deutsch wählen*, um *die Oberfläche in deutscher Sprache zu sehen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Task 3 *Eintstellungs-Menü schließen*

> Als *Vertriebsmitarbeiter* möchte ich *das Einstellungs-Menü schließen*, um *auf die HomePage zu sehen*.


- Aufwandsschätzung: 2 Stunden
- Tatsächliche Zeit: 




















### Feature 11 *Favoriten verwalten*


> Als *Vertriebsmitarbeiter* möchte ich *aus den existierenden Einträgen Favoriten wählen*, um *diese schnell abrufen zu können*.


- Aufwandsschätzung: M
- Akzeptanztests: 
    - [ ] Akzeptanstests der Implementables erfüllt
 
- Enthaltene Implementable Stories:
    - Favorit erstellen
    - Favorit entfernen
    - Favoriten abrufen 


### Implementable Story 1 *Favorit erstellen*


> Als *Vertriebsmitarbeiter* möchte ich *aus den existierenden Einträgen Favoriten wählen*, um *diese schnell abrufen zu können*.


- Aufwandsschätzung: 20
- Akzeptanztests: 
    - [ ] Der ausgewählte Eintrag ist als Favorit markiert
    - [ ] Der Favorite-Button des Eintrags ist gelb
    
- Enthaltene Tasks:
    - Favorite-Button klicken     




### Task 1 *Favorite-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Favorite-Button klicken*, um *einen Eintrag als Favoriten zu markieren*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 




### Implementable Story 2 *Favorit entfernen*


> Als *Vertriebsmitarbeiter* möchte ich *einen als Favorit markierten Eintrag unfavorisieren*, um *diesen nicht mehr als Favorit angezeigt zu bekommen*.


- Aufwandsschätzung: 20
- Akzeptanztests:
    - [ ] Der Eintrag ist nicht mehr als Favorit markiert 
    - [ ] Der Favorite-Button des Eintrags ist grau
    
- Enthaltene Tasks:
    - Favorite-Button klicken




### Task 1 *Favorite-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Favorite-Button klicken*, um *einen Favoriten nicht mehr als Favorit angezeigt zu bekommen*.


- Aufwandsschätzung: 4 Stunden
- Tatsächliche Zeit: 






### Implementable Story 3 *Favoriten abrufen*


> Als *Vertriebsmitarbeiter* möchte ich *meine vorher als Favoriten markierte Einträge abrufen*, um *diese einsehen zu können*.


- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Alle angezeigten Einträge sind als Favoriten markiert
    - [ ] Alle nicht angezeigten Einträge sind nicht als Favoriten markiert
 
- Enthaltene Tasks:
    - Liste nach Favoriten filtern 
     


### Task 1 *Liste nach Favoriten filtern*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigte Liste nach Favoriten filtern*, um *nur Favoriten angezeigt zu bekommen*.


- Aufwandsschätzung: 5 Stunden
- Tatsächliche Zeit: 
     
    




### Feature 12 *Letzte Aktion rückgängig machen*


> Als *Vertriebsmitarbeiter* möchte ich *eine durchgeführte Aktion rückgängig machen*, um *den Eintrag unverändert zu haben*.


- Aufwandsschätzung: L
- Akzeptanztests:
    - [ ] Der Eintrag ist identisch zu der vorherigen Version
    - [ ] Undo-Button wird nicht angezeigt 
    
- Enthaltene Implementable Stories:
    - Löschen von Eintrag rückgängig machen
    - Editieren von Eintrag rückgängig machen




### Implementable Story 1 *Löschen von Eintrag rückgängig machen*


> Als *Vertriebsmitarbeiter* möchte ich *einen gelöschten Eintrag wiederherstellen*, um *nicht gelöscht zu haben*.


- Aufwandsschätzung: 70
- Akzeptanztests:
    - [ ] Datenbank entspricht Zustand vor Aktion
    - [ ] Undo-Button wird nicht angezeigt 


- Enthaltene Tasks:
    - Undo-Button klicken




### Task 1 *Undo-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Undo-Button klicken*, um *das Löschen eines Eintrages rückgängig zu machen*.


- Aufwandsschätzung: 10 Stunden
- Tatsächliche Zeit: 




### Implementable Story 2 *Editieren von Eintrag rückgängig machen* 
 
> Als *Vertriebsmitarbeiter* möchte ich *das Editieren eines Eintrages rückgängig machen*, um *meine Änderungen zu verwerfen*.


- Aufwandsschätzung: 70
- Akzeptanztests:
    - [ ] Datenbank entspricht Zustand vor Aktion
    - [ ] Undo-Button wird nicht angezeigt 


- Enthaltene Tasks:
    - Undo-Button klicken


### Task 1 *Undo-Button klicken*

> Als *Vertriebsmitarbeiter* möchte ich *den Undo-Button klicken*, um *das Editieren eines Eintrages rückgängig zu machen*.


- Aufwandsschätzung: 12 Stunden
- Tatsächliche Zeit: 




   




