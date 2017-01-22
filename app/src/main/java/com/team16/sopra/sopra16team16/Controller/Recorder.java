package com.team16.sopra.sopra16team16.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.NewContactActivity;

import java.io.File;
import java.io.IOException;

import static android.os.Looper.getMainLooper;

/**
 * Recorder contains method for recording audio
 */
public class Recorder {

    public static Recorder currentInstance;
    public String path;
    private MediaRecorder recorder = null;
    private boolean is_recording = false;
    private ColorStateList actionButtonColor;
    private Handler handler;
    private int id;
    private Player player = new Player();
    private Runnable runnable;



    /**
     * Singleton getter of the currentInstance
     */
    public static Recorder getInstance() {
        if (currentInstance == null) {
            return currentInstance = new Recorder();
        } else {
            return currentInstance;
        }
    }

    //constructor - need context for path
    private Recorder() {
        path = FileUtils.PATH;
    }


    /**
     * Starts recording
     * * @param id id from the contact is the same as the id from the file
     */
    public void startRecording(int id, final FloatingActionButton actionButton, final Context context) {
        this.id = id;

        Log.e("Recorder", "ID: " + id);
        String filename = path + id + "_temp.3gp";
        Log.e("Recorder", "Filename: " + filename);

        // toggle recording state
        changeStatus(true);

        // setup
        recorder = new MediaRecorder();
        // TODO improve audio quality
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //AudioSource.MIC //AudioSource.VOICE_RECOGNITION
        recorder.setOutputFile(filename);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e("RECORDER_LOG", e.getMessage());
            Log.d("RecorderException", e.getMessage());
            FileUtils.deleteFile(path + id + "_temp.3gp");
        }
        recorder.start();

        // start runnable, will stop recording after 8 seconds
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isPressed()) {
                    stopRecording(actionButton, context);
                }
            }
        };
        handler.postDelayed(runnable, 8000);

        // change recording icon and background to give user visual feedback
        actionButton.setImageResource(R.drawable.ic_mic_none_black_24dp);
        actionButtonColor = actionButton.getBackgroundTintList();
        actionButton.setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
    }

    /**
     * Stops die record of the name
     */
    public void stopRecording(FloatingActionButton actionButton, Context context) {
        try {
            Log.e("Recorder", "Aufnahme zu Ende");
            // toggle status to not recording
            changeStatus(false);
            // interrupt limiting thread
            handler.removeCallbacks(runnable);
            // stop recording
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;

            // update image and background on the button

            actionButton.setImageResource(R.drawable.ic_mic_black_24dp);
            actionButton.setBackgroundTintList(actionButtonColor);
            confirmRecording(context, actionButton);
        } catch (RuntimeException e) {
            // throw error
            // delete file
            Log.d("RecorderException", e.getMessage());
            FileUtils.deleteFile(path + id + "_temp.3gp");
        }
    }

    /**
     * Shows an alert asking the user to confirm the recording.
     */
    private void confirmRecording(final Context context, final FloatingActionButton actionButton) {
        // get the dialog
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        // set the custom layout
        Window win = alertDialog.getWindow();
        if (win != null) {
            win.setContentView(R.layout.confirm_record_dialog);

            // text
            TextView textDialog = (TextView) win.findViewById(R.id.text_dialog);
            textDialog.setText("Do you want to keep this recording?");

            // cancel
            ImageButton cancelDialog = (ImageButton) win.findViewById(R.id.cancel_dialog);

            cancelDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    FileUtils.deleteFile(FileUtils.PATH + id + "_temp.3gp");
                }
            });


            // play
            final ImageButton playDialog = (ImageButton) win.findViewById(R.id.play_dialog);

            playDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!player.isPlaying()) {
                            player.startPlaying(id + "_temp", playDialog);
                    } else {
                        player.stopPlaying(playDialog);
                    }
                }
            });


            // confirm
            ImageButton acceptDialog = (ImageButton) win.findViewById(R.id.accept_dialog);

            acceptDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                    ((NewContactActivity)context).customRecordColor();
                }
            });
        }

    }

    /**
     * true  --> is recording
     * false --> is not recording
     */
    public boolean isPressed() {
        return is_recording;
    }

    /**
     * Changes the status of the recorder.
     *
     * @param status - the new status of the recorder
     */
    private void changeStatus(boolean status) {
        is_recording = status;
    }




}