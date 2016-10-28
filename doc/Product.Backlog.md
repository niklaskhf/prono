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
    - Verwerfen von temporärer Aufnahme
    - Abspielen von temporärer Aufnahme
    - Eintrag erstellen
    - Eintrag einsehen
    - Aufnahme abspielen


### Feature *Aufnehmen von Aussprache*

> Als *Vertriebsmitarbeiter* möchte ich *die Aussprache von Namen aufnehmen können*, um *eine temporäre Audio-Aufnahme abspielen zu können*.

- Aufwandsschätzung: M-L
- Akzeptanztests:
    - [ ] Audio-Aufnahme muss abspielbar sein
    
- Enthaltene Implementable Stories:
    - Temporäre Aufnahme erstellen  
    - Temporäre Aufnahme speichern/verwerfen
    - Temporäre Aufnahme abspielen
    
  
### Implementable Story *Temporäre Aufnahme erstellen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporäre Aufnahme erstellen*, um *diese später speichern zu können*.

- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Temporäre Aufnahme ist im Speicher vorhanden 

- Enthaltene Tasks:
    - Record-Button klicken
    - Namen aussprechen
    - Stop-Button klicken
    
### Implementable Story *Temporäre Aufnahme verwerfen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme verwerfen*, um *eine neue Aufnahme erstellen zu können*.

- Aufwandsschätzung: 20
- Akzeptanztests:
    - [ ] Aufnahme ist nicht mehr vorhanden
    - [ ] Aufforderung zu neuer Aufnahme 

- Enthaltene Tasks:
    - Verwerfen-Button klicken
    - Verwerfen bestätigen

### Implementable Story *Temporäre Aufnahme akzeptieren*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme akzeptieren*, um *sie danach in einem Eintrag speichern zu können*.

- Aufwandsschätzung: 10
- Akzeptanztests:
    - [ ] Temporäre Aufnahme nicht gelöscht
    - [ ] Temporäre Aufname ist im temporärem Speicher
    
- Enthaltene Tasks:
    - Klicke auf Accept-Button

### Implementable Story *Temporäre Aufnahme abspielen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine temporär erstellte Aufnahme abspielen*, um *sie auf Qualität zu testen*.

- Aufwandsschätzung: 20
- Akzeptanztests:
    - [ ] Temporäre Aufnahme wird von dem Android-Gerät einmal abgespielt 
    
- Enthaltene Tasks:
    - Klicke auf Play-Button









### Feature *Eintrag erstellen*

> Als *Vertriebsmitarbeiter* möchte ich *einen Eintrag mit Titel, Vor-/Zweit-/Nachnamen, Geschlecht, Aussprache und Land erstellen*, um *die Informationen später verfügbar zu haben*.

- Aufwandsschätzung: M 
- Akzeptanztests:
    - [ ] Eintrag muss nach Schließen der App noch existieren
    - [ ] Eintrag muss mindestens Nachname und Aussprache enthalten

- Enthaltene Implementable Stories:
    - Editor öffnen 
    - Informationen eingeben
    - Aufnahme speichern, siehe Feature 'Temporäre Aufnahme erstellen'
    - Eintrag speichern
 
### Implementable Story *Editor öffnen*
 
> Als *Vertriebsmitarbeiter* möchte ich *den Editor öffnen*, um *einen neuen Eintrag zu erstellen*.

- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Ein neuer Eintrag kann erstellt werden
    - [ ] Android-Gerät-Bildschirm zeigt Editor
    
- Enthaltene Tasks:
    - Klicke auf Create-Button

### Implementable Story *Informationen eingeben*
 
> Als *Vertriebsmitarbeiter* möchte ich *in dem Editor Informationen über den Kontakt eingeben*, um *diese später zu speichern*.

- Aufwandsschätzung: 5
- Akzeptanztests:
    - [ ] Eingabe wird angezeigt
    
- Enthaltene Tasks:
    - Gib Titel ein
    - Gib Vorname ein
    - Gib Zweitname ein
    - Gib Nachname ein
    - Wähle Geschlecht
    - Wähle Land


### Implementable Story *Eintrag speichern*
 
> Als *Vertriebsmitarbeiter* möchte ich *die eingegebenen Informationen speichern*, um *später auf sie zugreifen zu können*.

- Aufwandsschätzung: 90 
- Akzeptanztests:
    - [ ] Eintrag ist in permanenten Speicher vorhanden
    - [ ] Eintrag ist auch nach Neustart des Geräts verfügbar

- Enthaltene Tasks:
    - Save-Button klicken











### Feature *Eintrag einsehen*

> Als *Vertriebsmitarbeiter* möchte ich *einen existierenden Eintrag aufrufen*, um *die gespeicherten Informationen einzusehen*.

- Aufwandsschätzung: M 
- Akzeptanztests:
    - [ ] Navigierbare Liste aller Einträge ist navigierbar
    - [ ] Kontakt wird auf Android-Geraet-Display sichtbar
 
- Enthaltene Implementable Stories:
    - Einträge in Liste anzeigen
    - Eintrag öffnen
    - Eintrag schließen
    
### Implementable Story *Einträge in Liste anzeigen*
 
> Als *Vertriebsmitarbeiter* möchte ich *die existierenden Einträge in einer Liste sehen*, um *sie öffnen zu können*.

- Aufwandsschätzung: 40
- Akzeptanztests:
    - [ ] Alle existierenden Einträge werden in Listenform auf Android-Gerät-Bildschirm angezeigt

- Enthaltene Tasks:
    - Einträge anzeigen

### Implementable Story *Eintrag öffnen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen Eintrag öffnen*, um *die gespeicherten Informationen einzusehen*.

- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Android-Gerät-Bildschirm zeigt den Eintrag an 

- Enthaltene Tasks:
    - Auf Eintrag klicken

### Implementable Story *Eintrag schließen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen geöffneten Eintrag schließen*, um *auf andere Einträge zugreifen zu können*.

- Aufwandsschätzung: 5
- Akzeptanztests:
    - [ ] Android-Gerät-Bildschirm nimmt Zustand vor Öffnen an 

- Enthaltene Tasks:
    - Zurück-Button klicken










### Feature *Aufnahme abpspielen*

> Als *Vertriebsmitarbeiter* möchte ich *eine gespeicherte Aufnahme abspielen*, um *die korrekte Aussprache eines Namen zu erfahren*.

- Aufwandsschätzung: S
- Akzeptanztests: 
   - [ ] Das Android-Gerät spielt die Aufnahme ab.
 
- Enthaltene Implementable Stories:
    - Eintrag auswählen
    - Gespeicherte Datei abspielen
 


### Implementable Story *Gespeicherte Datei abspielen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine zu einem Eintrag assoziierte Aussprache abspielen*, um *die Person richtig anreden zu können*.

- Aufwandsschätzung: 40
- Akzeptanztests:
    - [ ] Android-Gerät spielt vorher gespeicherte Aussprache genau einmal ab

- Enthaltene Tasks:
    - Eintrag finden
    - Play-Button klicken
















## Epic 2 *Existierenden Eintrag editieren/löschen/verwalten/durchsuchen*

> Als *Vertriebsmitarbeiter* möchte ich *die bereits existierenden Einträge editieren/löschen/verwalten*, um *Informationen zu ändern/korrigieren*.
- Enthaltene Features:
    - Eintrag editieren
    - Eintrag löschen
    - Einträge durchsuchen
    - Einträge sortieren
    - Einträge filtern
    - Sprache ändern
    - Favoriten verwalten
    - Löschen von Eintrag rückgängig machen

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



### Feature *Eintrag editieren*

> Als *Vertriebsmitarbeiter* möchte ich *in einem existierenden Eintrag gespeicherte Informationen Titel, (Vor-/Zweit-/Nachname, Geschlecht, Land und Aussprache) edtieren*, um *die Informationen zu aktualisieren*.

- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Eintrag auch nach Schließen der App aktualisiert

- Enthaltene Implementable Stories:
    - Eintrag öffnen, siehe Feature 'Eintrag einsehen'
    - Editiermodus starten
    - Informationen ändern
    - Eintrag speichern

### Implementable Story *Editiermodus starten*
 
> Als *Vertriebsmitarbeiter* möchte ich *den Editiermodus starten*, um *Informationen und Aussprache eines Eintrags zu verändern*.

- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Android-Gerät-Bildschirm zeigt Editor 
    - [ ] Informationen lassen sich in Eingabefelder ändern

- Enthaltene Tasks:
    - Edit-Button klicken

### Implementable Story *Informationen ändern*
 
> Als *Vertriebsmitarbeiter* möchte ich *bereits gespeicherte Informationen ändern*, um *sie zu korrigieren*.

- Aufwandsschätzung: 15
- Akzeptanztests:
    - [ ] Die Eingabe-Felder zeigen veränderte Informationen an 

- Enthaltene Tasks:
    - Titel ändern
    - Vorname ändern
    - Zweitname ändern
    - Nachname ändern
    - Geschlecht ändern
    - Land ändern
    - Aussprache ändern

### Implementable Story *Eintrag speichern*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen veränderten Eintrag speichern*, um *die veränderten Informationen beizubehalten*.

- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Der alte Eintrag wurde durch den veränderten Eintrag im permanenten Speicher ersetzt

- Enthaltene Tasks:
    - Save-Button klicken








### Feature *Eintrag löschen*

> Als *Vertriebsmitarbeiter* möchte ich *einen existierenden Eintrag löschen*, um *den Eintrag nicht mehr angezeigt zu bekommen*.

- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Eintrag ist permanent von dem Android-Geräte-Speicher gelöscht
    - [ ] Eintrag wird nicht mehr in Listen angezeigt
    - [ ] Eintrag ist nicht Ergebnis von Suche
    
- Enthaltene Implementable Stories:
    - Eintrag öffnen, siehe Implementable Story in Feature 'Eintrag einsehen', Epic 1 
    - Eintrag löschen

### Implementable Story *Eintrag löschen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen existierenden Eintrag löschen*, um *ihn nicht mehr verfügbar zu haben*.

- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Eintrag ist permanent von dem Android-Geräte-Speicher gelöscht
    - [ ] Eintrag wird nicht mehr in Listen angezeigt
    - [ ] Eintrag ist nicht Ergebnis von Suche
    
- Enthaltene Tasks:
    - Delete-Button klicken
    - Löschen Bestätigen










### Feature *Einträge durchsuchen*

> Als *Vertriebsmitarbeiter* möchte ich *die Einträge durch Eingabe des Namen durchsuchen*, um *meinen gesuchten Eintrag zu finden*.

- Aufwandsschätzung: L
- Akzeptanztests: 
    - [ ] Nach Eingabe einer Suche werden alle Einträge, die teilweise mit der Suche übereinstimmen, angezeigt
    

- Enthaltene Implementable Stories:
    - Suche durchführen
    - Suche abbrechen
    
    
### Implementable Story *Suche durchführen*
 
> Als *Vertriebsmitarbeiter* möchte ich *einen Suchbegriff eingeben*, um *alle Einträge danach zu durchsuchen*.

- Aufwandsschätzung: 75
- Akzeptanztests:
    - [ ] Suchbegriff wird erfolgreich gelesen
    - [ ] Alle Einträge, die mit dem Suchbegriff übereinstimmen, werden angezeigt

- Enthaltene Tasks:
    - Suchbegriff eingeben
    - Suche starten


### Implementable Story *Suche verlassen*
 
> Als *Vertriebsmitarbeiter* möchte ich *die Suche verlassen*, um *die vorherigen Einträge angezeigt zu bekommen*.

- Aufwandsschätzung: 5
- Akzeptanztests:
    - [ ] Vorheriger Bildschirm wird angezeigt 

- Enthaltene Tasks:
    - Zurück-Button klicken










### Feature *Einträge sortieren*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach einem Kriterium alphabetisch sortieren*, um *meine gewünschte Reihenfolge angezeigt zu bekommen*.

- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Die Liste ist nach Kriterium sortiert

- Enthaltene Implementable Stories:
    - Einträge nach gewünschtem Krtierium sortieren

### Implementable Story *Einträge nach gewünschtem Kriterium sortieren*
 
> Als *Vertriebsmitarbeiter* möchte ich *die Einträge sortieren*, um *eine bessere Übersicht zu haben*.

- Aufwandsschätzung: 60
- Akzeptanztests:
    - [ ] Angezeigte Reihenfolge der Einträge enspricht Kriterium

- Enthaltene Tasks:
    - Nach Vornamen sortieren
    - Nach Nachnamen sortieren
    - Nach Land sortieren









### Feature *Einträge filtern*

> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach einem Filter-Kriterium anpassen*, um *die Einträge, die dem gewünschten Filter-Kriterium entsprechen, angezeigt zu bekommen*.

- Aufwandsschätzung: M
- Akzeptanztests: 
    - [ ] Alle angezeigten Einträge entsprechen dem Filter-Kriterium
    - [ ] Einträge, die nicht dem Filter-Kriterium entsprechen, werden nicht angezeigt 

- Enthaltene Implementable Stories:
    - Einträge nach Kriterium filtern

### Implementable Story *Einträge nach Kriterium filtern*
 
> Als *Vertriebsmitarbeiter* möchte ich *die angezeigten Einträge nach einem Kriterium filtern*, um *nur gewünschte Einträge angezeigt zu bekommen*.

- Aufwandsschätzung: 50
- Akzeptanztests:
    - [ ] Alle angezeigten Einträge entsprechen dem Kriterium

- Enthaltene Tasks:
    - Einträge nach Land filtern
    - Einträge nach Geschlecht filtern
    - Einträge nach Favoriten filtern, siehe Feature 'Favoriten verwalten'










### Feature *Sprache anpassen*

> Als *Vertriebsmitarbeiter* möchte ich *die Sprache der Oberfläche ändern*, um *eine andere Sprache zu lesen*.

- Aufwandsschätzung: L
- Akzeptanztests: 
    - [ ] Alle Oberflächenelemente sind in der ausgewählten Sprache
 
- Enthaltene Implementable Stories:
    - Einstellungen öffnen 
    - Sprache auswählen
    
    
### Implementable Story *Einstellungen öffnen*
 
> Als *Vertriebsmitarbeiter* möchte ich *das Einstellungs-Menü öffnen*, um *sie zu verändern*.

- Aufwandsschätzung: 35
- Akzeptanztests:
    - [ ] Einstellungs-Menü wird angezeigt 

- Enthaltene Tasks:
    - Auf Settings-Button klicken

### Implementable Story *Sprache auswählen*
 
> Als *Vertriebsmitarbeiter* möchte ich *eine Sprache auswählen*, um *die Oberflächensprache zu verändern*.

- Aufwandsschätzung: 
- Akzeptanztests:
    - [ ] Die Oberflächensprache entspricht der Auswahl 

- Enthaltene Tasks:
    - Englisch auswählen
    - Deutsch auswählen
    - Einstellungs-Menü schließen
    









### Feature *Favoriten verwalten*

> Als *Vertriebsmitarbeiter* möchte ich *aus den existierenden Einträgen Favoriten wählen*, um *diese schnell abrufen zu können*.

- Aufwandsschätzung: S
- Akzeptanztests: 
    - [ ] Akzeptanstests der Implementables erfüllt
 
- Enthaltene Implementable Stories:
    - Favorit erstellen
    - Favorit entfernen
    - Favoriten abrufen 

### Implementable Story *Favorit erstellen*

> Als *Vertriebsmitarbeiter* möchte ich *aus den existierenden Einträgen Favoriten wählen*, um *diese schnell abrufen zu können*.

- Aufwandsschätzung: S
- Akzeptanztests: 
    - [ ] Der ausgewählte Eintrag ist als Favorit markiert
    - [ ] Der Favorite-Button des Eintrags ist gelb
    
- Enthaltene Tasks:
    - Favorite-Button klicken     



### Implementable Story *Favorit entfernen*

> Als *Vertriebsmitarbeiter* möchte ich *einen als Favorit markierten Eintrag unfavorisieren*, um *diesen nicht mehr als Favorit angezeigt zu bekommen*.

- Aufwandsschätzung: S
- Akzeptanztests:
    - [ ] Der Eintrag ist nicht mehr als Favorit markiert 
    - [ ] Der Favorite-Button des Eintrags ist grau
    
- Enthaltene Tasks:
    - Favorite-Button klicken



### Implementable Story *Favoriten abrufen*

> Als *Vertriebsmitarbeiter* möchte ich *meine vorher als Favoriten markierte Einträge abrufen*, um *diese einsehen zu können*.

- Aufwandsschätzung: M
- Akzeptanztests:
    - [ ] Alle angezeigten Einträge sind als Favoriten markiert
    - [ ] Alle nicht angezeigten Einträge sind nicht als Favoriten markiert
 
- Enthaltene Tasks:
    - Liste nach Favoriten filtern 
     
     
    


### Feature *Letzte Aktion rückgängig machen*

> Als *Vertriebsmitarbeiter* möchte ich *eine durchgeführte Aktion rückgängig machen*, um *den Eintrag unverändert zu haben*.

- Aufwandsschätzung: XL
- Akzeptanztests:
    - [ ] Der Eintrag ist identisch zu der vorigen Version
    - [ ] Undo-Button wird nicht angezeigt 
    
- Enthaltene Implementable Stories:
    - Löschen von Eintrag rückgängig machen
    - Editieren von Eintrag rückgängig machen


### Implementable Story *Löschen von Eintrag rückgängig machen*

> Als *Vertriebsmitarbeiter* möchte ich *einen gelöschten Eintrag wiederherstellen*, um *nicht gelöscht zu haben*.

- Aufwandsschätzung: 70
- Akzeptanztests:
    - [ ] Datenbank entspricht Zustand vor Aktoin
    - [ ] Undo-Button wird nicht angezeigt 

- Enthaltene Tasks:
    - Undo-Button klicken


### Implementable Story *Editieren von Eintrag rückgängig machen* 
 
> Als *Vertriebsmitarbeiter* möchte ich *das Editieren eines Eintrages rückgängig machen*, um *meine Änderungen zu verwerfen*.

- Aufwandsschätzung: 70
- Akzeptanztests:
    - [ ] Datenbank entspricht Zustand vor Aktion
    - [ ] Undo-Button wird nicht angezeigt 

- Enthaltene Tasks:
    - Undo-Button klicken


