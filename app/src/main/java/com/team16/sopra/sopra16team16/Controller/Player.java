package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageButton;

import com.team16.sopra.sopra16team16.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by Martin on 22.11.2016.
 */

public class Player {

    private static Player currentInstance = null;
    //private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String path;
    private MediaPlayer player = null;

    private boolean is_playing = false;

    private Player(Context context) {
        path = context.getApplicationContext().getFilesDir().getPath();
    }

    public static Player getCurrentInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new Player(context);
            return currentInstance;
        } else {
            return currentInstance;
        }
    }


    /*
     * Gibt die Aufnahme des Namens aus
     */
    public void startPlaying(int id, final ImageButton playButton) {

        changeStatus(true);
        String filename = path + id + ".3gp";

        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                changeStatus(false);
                playButton.setBackgroundResource(R.drawable.play_icon);
            }
        });

        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
            playButton.setBackgroundResource(R.drawable.cancel_icon);

        } catch (IOException e) {
            Log.d("PlayerIOException", e.getMessage());
        }
    }

    /*
     * Stopt die Aufnahme des Namens
     */
    public void stopPlaying(ImageButton playButton) {
        playButton.setBackgroundResource(R.drawable.play_icon);
        changeStatus(false);
        player.release();
        player = null;
    }

    public boolean isPlaying() {
        return is_playing;
    }

    public void changeStatus(boolean status) {
        is_playing = status;
    }

}
