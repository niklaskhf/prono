package com.team16.sopra.sopra16team16.Controller;

import android.media.MediaPlayer;
import android.os.Environment;

import java.io.IOException;

/**
 * Created by Martin on 22.11.2016.
 */

public class Player {

    private static Player currentInstance = null;
    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private MediaPlayer player = null;

    private boolean is_playing = false;


    public static Player getCurrentInstance() {
        if (currentInstance == null) {
            currentInstance = new Player();
            return currentInstance;
        } else {
            return currentInstance;
        }
    }

    /*
     * Gibt die Aufnahme des Namens aus
     */
    public void startPlaying(int id) {

        changestatus(true);
        String filename = path + id + ".3gp";

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
    public void stopPlaying() {
        changestatus(false);
        player.release();
        player = null;
    }

    public boolean isPlaying() {
        return is_playing;
    }

    public void changestatus(boolean status) {
        is_playing = status;
    }

}
