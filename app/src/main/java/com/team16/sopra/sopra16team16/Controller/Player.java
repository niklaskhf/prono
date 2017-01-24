package com.team16.sopra.sopra16team16.Controller;

import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageButton;

import com.team16.sopra.sopra16team16.R;

import java.io.IOException;

/**
 * Player contains methods for playing stored audio
 */

public class Player {
    private static Player currentInstance;
    private MediaPlayer player = new MediaPlayer();
    private int id;
    private boolean isReleased = false;

    private String filename;

    private boolean is_playing = false;

    /**
     * Singleton getter
     * @return the only instance of the class - Singleton
     */
    public static Player getCurrentInstance() {
        if (currentInstance == null) {
            return currentInstance = new Player();
        } else {
            return currentInstance;
        }
    }

    /**
     * Constructor for Singleton
     */
    private Player() {

    }

    /**
     * plays an audio file
     * @param id id of the contact which audio file should be played
     * @param playButton the button which was clicked
     */
    public void startPlaying(int id, final ImageButton playButton) {
        filename = FileUtils.PATH + id + ".3gp";

        if (isPlaying()) {
            // do nothing
        } else {
            if (isReleased) {
                player = new MediaPlayer();
                isReleased =false;
            }
            changeStatus(true);
            player.reset();
            this.id = id;
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
                changeStatus(false);
                Log.d("PlayerIOException", e.getMessage());
            }
        }
    }

    /**
     * plays an audio file
     * @param idString filename of the audio file, located in the /files/ directory (3temp.3gp -> 3temp)
     * @param playButton the button which was clicked
     * @param id id of the contact currently being played
     */
    public void startPlaying(String idString, final ImageButton playButton, int id) {
        filename = FileUtils.PATH + idString + ".3gp";

        Log.d("player", "trying to play this file: " + filename);

        if (!isPlaying()) {
            if (isReleased) {
                player = new MediaPlayer();
                isReleased =false;
            }
            changeStatus(true);
            this.id = id;
            player.reset();
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
                changeStatus(false);
                Log.d("PlayerIOException", e.getMessage());
            }
        }
    }


    /**
     * plays an audio file
     * @param id id of the contact which audio file should be played
     * @param playButton the button which was clicked
     */
    public void startPlaying(int id, final FloatingActionButton playButton) {
        String filename = FileUtils.PATH + id + ".3gp";
        Log.d("player", "trying to play this file: " + filename);

        if (!isPlaying()) {
            if (isReleased) {
                player = new MediaPlayer();
                isReleased =false;
            }
            changeStatus(true);
            player.reset();
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
                changeStatus(false);
                Log.d("PlayerIOException", e.getMessage());
            }
        }
    }


    /**
     * stop playing an audio file
     * @param playButton the button which was clicked
     * @param id id of the contact calling the method
     */
    public void stopPlaying(FloatingActionButton playButton, int id) {
        if (this.id == id) {
            playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            changeStatus(false);
            player.reset();
            //player.release();
        }
    }

    /**
     * stop playing an audio file
     * @param playButton the button which was clicked
     * @param id id of the contact calling the method
     */
    public void stopPlaying(ImageButton playButton, int id) {
        if (this.id == id) {
            playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
            changeStatus(false);
            player.reset();
        }
    }

    /**
     * returns, whether an audio file is played
     * @return true if an audio file is played, false otherwise
     */
    public boolean isPlaying() {
        return is_playing;
    }

    /**
     * Releases the MediaPlayer object
     */
    public void release() {
        if (!isReleased) {
            player.release();
            isReleased = true;
            changeStatus(false);
        }
    }

    /**
     * Change the status of the Recorder
     * @param status true if an audio file is played, false otherwise
     */
    private void changeStatus(boolean status) {
        is_playing = status;
    }
}
