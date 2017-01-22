package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageButton;

import com.team16.sopra.sopra16team16.R;

import java.io.File;
import java.io.IOException;

/**
 * Player contains methods for playing stored audio
 */

public class Player {

    private static Player currentInstance = null;
    //private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String path = FileUtils.PATH;
    private MediaPlayer player = null;
    private ImageButton imagePlayButton;

    private String filename;

    private boolean is_playing = false;

    /**
     * there has to be a constructor
     */
    public Player() {

    }


    /**
     * plays an audio file
     * @param id id of the contact which audio file should be played
     * @param playButton
     */
    public void startPlaying(int id, final ImageButton playButton) {
        changeStatus(true);
        filename = path + id + ".3gp";

        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                changeStatus(false);
                playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
            }
        });

        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
            Log.d("player", "playing " + filename);
            playButton.setImageResource(R.drawable.ic_stop_black_48dp);

        } catch (IOException e) {
            Log.d("PlayerIOException", e.getMessage());
        }
    }

    /**
     * plays an audio file
     * @param id id of the contact which audio file should be played
     * @param playButton
     */
    public void startPlaying(String id, final ImageButton playButton) {
        changeStatus(true);
        filename = path + id + ".3gp";

        Log.d("player", "trying to play this file: " + filename);

        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                changeStatus(false);
                playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
            }
        });

        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
            Log.d("player", "playing " + filename);
            playButton.setImageResource(R.drawable.ic_stop_black_48dp);

        } catch (IOException e) {
            Log.d("PlayerIOException", e.getMessage());
        }
    }


    /**
     * plays an audio file
     * @param id id of the contact which audio file should be played
     * @param playButton
     */
    public void startPlaying(int id, final FloatingActionButton playButton) {
        changeStatus(true);
        String filename = path + id + ".3gp";
        Log.d("player", "trying to play this file: " + filename);
        player = new MediaPlayer();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                changeStatus(false);
                playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            }
        });

        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
            Log.d("player", "playing " + filename);
            playButton.setImageResource(R.drawable.ic_stop_black_24dp);

        } catch (IOException e) {
            Log.d("PlayerIOException", e.getMessage());
        }
    }


    /**
     * stop playing an audio file
     * @param playButton
     */
    public void stopPlaying(FloatingActionButton playButton) {
        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        changeStatus(false);
        player.release();
        player = null;
    }

    /**
     * stop playing an audio file
     * @param playButton
     */
    public void stopPlaying(ImageButton playButton) {
        playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
        changeStatus(false);
        player.release();
        player = null;
    }

    /**
     * returns, whether an audio file is played
     * @return true if an audio file is played, false otherwise
     */
    public boolean isPlaying() {
        return is_playing;
    }

    /**
     * Change the status of the Recorder
     * @param status true if an audio file is played, false otherwise
     */
    public void changeStatus(boolean status) {
        is_playing = status;
    }
}
