package com.team16.sopra.sopra16team16.Controller;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.IOException;

/**
 * Created by Martin on 22.11.2016.
 */

public class Player {
    private static String filename = null;
    private MediaPlayer player = null;

    /*
     * Konstruktur: Der Pfad zur Datei wird hier definiert
     */
    public Player() {
        filename = Environment.getExternalStorageDirectory().getAbsolutePath();

        //ToDo: Filename ist noch flasch. name sollte die id des Kontaktes aus der Datenbank sein
        filename += "000001.3gp";
    }

    /*
     * Gibt die Aufnahme des Namens aus
     */
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
        } catch (IOException e) {

        }
    }

    /*
     * Stopt die Aufnahme des Namens
     */
    private void stopPlaying() {
        player.release();
        player = null;
    }


}
