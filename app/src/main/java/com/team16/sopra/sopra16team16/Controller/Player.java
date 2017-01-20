package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageButton;

import com.team16.sopra.sopra16team16.R;

import java.io.File;
import java.io.IOException;


public class Player {

    private static Player currentInstance = null;
    //private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String path = FileUtils.PATH;
    private MediaPlayer player = null;
    private ImageButton imagePlayButton;

    private String filename;

    private boolean is_playing = false;

    public Player() {
    }


    /**
     * Gibt die Aufnahme des Namens aus
     */
    public void startPlaying(int id, final ImageButton playButton) {
        if (isPlaying()) {
            stopPlaying(this.imagePlayButton);
        }
        this.imagePlayButton = playButton;
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
     * Gibt die Aufnahme des Namens aus
     */
    public void startPlaying(String id, final ImageButton playButton) {
        if (isPlaying()) {
            stopPlaying(this.imagePlayButton);
        }
        this.imagePlayButton = playButton;
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
     * Gibt die Aufnahme des Namens aus
     */
    public void startPlaying(int id, String first, String last, String country, final FloatingActionButton playButton) {
        changeStatus(true);
        String idFilename = path + id + ".3gp";
        String firstFilename = FileUtils.PATH + first.toLowerCase() + country.toLowerCase() + ".3gp";
        final String lastFilename = FileUtils.PATH + last.toLowerCase() + country.toLowerCase() + ".3gp";

        if (new File(idFilename).exists()) {
            filename = idFilename;

            MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    changeStatus(false);
                    playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            };

            this.play(filename, listener);
            playButton.setImageResource(R.drawable.ic_stop_black_24dp);
        } else {
            filename = firstFilename;
            MediaPlayer.OnCompletionListener listener1 = new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    filename = lastFilename;
                    play(filename, listener2);
                }

                MediaPlayer.OnCompletionListener listener2 = new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        changeStatus(false);
                        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                };
            };

            this.play(filename, listener1);
            playButton.setImageResource(R.drawable.ic_stop_black_24dp);
        }

    }

    /**
     * Gibt die Aufnahme des Namens aus
     */
    public void startPlaying(int id, String first, String last, String country, final ImageButton playButton) {
        if (isPlaying()) {
            stopPlaying(this.imagePlayButton);
            this.imagePlayButton.setImageResource(R.drawable.ic_stop_black_48dp);
        }
        this.imagePlayButton = playButton;

        changeStatus(true);
        String idFilename = path + id + ".3gp";
        String firstFilename = FileUtils.PATH + first.toLowerCase() + country.toLowerCase() + ".3gp";
        final String lastFilename = FileUtils.PATH + last.toLowerCase() + country.toLowerCase() + ".3gp";

        if (new File(idFilename).exists()) {
            filename = idFilename;

            MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    changeStatus(false);
                    playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
                }
            };

            this.play(filename, listener);
            playButton.setImageResource(R.drawable.ic_stop_black_48dp);
        } else {
            filename = firstFilename;
            MediaPlayer.OnCompletionListener listener1 = new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    filename = lastFilename;
                    play(filename, listener2);
                }

                MediaPlayer.OnCompletionListener listener2 = new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        changeStatus(false);
                        playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
                    }
                };
            };

            this.play(filename, listener1);
            playButton.setImageResource(R.drawable.ic_stop_black_48dp);
        }

    }

    public void play(String filename, MediaPlayer.OnCompletionListener listener) {
        player = new MediaPlayer();
        player.setOnCompletionListener(listener);

        try {
            player.setDataSource(filename);
            player.prepare();
            player.start();
            Log.d("player", "playing " + filename);

        } catch (IOException e) {
            Log.d("PlayerIOException", e.getMessage());
        }
    }

    /**
     * Gibt die Aufnahme des Namens aus
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


    /*
     * Stopt die Wiedergabe des Namens
     */
    public void stopPlaying(FloatingActionButton playButton) {
        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        changeStatus(false);
        player.release();
        player = null;
    }

    /*
     * Stopt die Wiedergabe des Namens
     */
    public void stopPlaying(ImageButton playButton) {
        playButton.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
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
