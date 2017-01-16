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
 * Created by Martin on 17.11.2016.
 */

/**
 * Recorder
 */
public class Recorder {

    public String path;
    private MediaRecorder recorder = null;
    private boolean is_recording = false;
    private ColorStateList actionButtonColor;
    public Context context;
    private FloatingActionButton actionButton;
    private ImageButton recordFirst;
    private ImageButton recordLast;
    private RecordingMode mode;
    private Handler handler;
    private int id;
    private Player player = new Player();


    private String name;
    private String country;



    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isPressed()) {
                stopRecording();
                ((NewContactActivity) context).enableButtons();;
            }
        }
    };


    //constructor - need context for path
    public Recorder(Context context, FloatingActionButton fba, ImageButton recFirst, ImageButton recLast) {
        path = context.getApplicationContext().getFilesDir().getPath() + "/";
        this.context = context;
        actionButton = fba;
        recordFirst = recFirst;
        recordLast = recLast;
    }


    /**
     * Startet Recorder
     * * @param id id from the contact is the same as the id from the file
     */
    public void startRecording(int id, RecordingMode mode) {
        this.mode = mode;
        this.id = id;

        Log.e("Recorder", "ID: " + id);
        String filename = path + id + "temp.3gp";
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
        }
        recorder.start();

        // start runnable, will stop recording after 8 seconds
        handler = new Handler();
        handler.postDelayed(runnable, 8000);

        // change recording icon and background to give user visual feedback
        actionButton.setImageResource(R.drawable.ic_mic_none_black_24dp);
        actionButtonColor = actionButton.getBackgroundTintList();
        actionButton.setBackgroundTintList(ColorStateList.valueOf(Color.CYAN));
    }

    /*
     * Stoppt die Aufnahme des Namens
     */
    public void stopRecording() {
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
            if (mode == RecordingMode.RECORDING_CUSTOM) {
                actionButton.setImageResource(R.drawable.ic_mic_black_24dp);
                actionButton.setBackgroundTintList(actionButtonColor);
            } else if (mode == RecordingMode.RECORDING_FIRST) {
                recordFirst.setImageResource(R.drawable.ic_mic_black_24dp);
            } else if (mode == RecordingMode.RECORDING_LAST) {
                Log.d("trying to chast", "this");
                recordLast.setImageResource(R.drawable.ic_mic_black_24dp);
            }
            confirmRecording();
        } catch (RuntimeException e) {
            // throw error
            // delete file
            // TODO
        }
    }

    /**
     * Shows an alert asking the user to confirm the recording.
     */
    private void confirmRecording() {
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
                    FileUtils.deleteFile(FileUtils.PATH + id + "temp.3gp");
                    ((NewContactActivity) context).enableButtons();;
                }
            });


            // play
            final ImageButton playDialog = (ImageButton) win.findViewById(R.id.play_dialog);

            playDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!player.isPlaying()) {
                        if (mode == RecordingMode.RECORDING_CUSTOM) {
                            player.startPlaying(id + "temp", playDialog);
                        } else if (mode == RecordingMode.RECORDING_FIRST) {
                            player.startPlaying(name + country + "temp", playDialog);
                        } else if (mode == RecordingMode.RECORDING_LAST) {
                            player.startPlaying(name + country + "temp", playDialog);
                        }
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
                    if (mode == RecordingMode.RECORDING_CUSTOM) {
                        ((NewContactActivity) context).customRecordColor();
                        ((NewContactActivity) context).firstRecordColor();
                        ((NewContactActivity) context).lastRecordColor();
                    } else if (mode == RecordingMode.RECORDING_FIRST) {
                        ((NewContactActivity) context).firstRecordColor();
                    } else if (mode == RecordingMode.RECORDING_LAST) {
                        ((NewContactActivity) context).lastRecordColor();
                    }
                }
            });
        }

    }

    /**
     * Trigger recording process of an audio file related to the generic name.
     * @param name - name
     * @param country - country
     * @param mode - recording mode
     */
    public void triggerRecordingGeneric(String name, String country, RecordingMode mode) {
        // save all the data
        this.mode = mode;
        // lower case
        country = country.toLowerCase();
        name = name.toLowerCase();
        this.name = name;
        this.country = country;

        String filename = path + name + country + ".3gp";
        String tempfile = path + name + country + "temp.3gp";

        // check if file exists, and might need to be overwritten
        if (new File(filename).exists() || new File(tempfile).exists()) {
            confirmGenericDialog();
        } else {
            startRecordingGeneric();
        }
    }

    /**
     * Starts recording an audio file related to the generic name.

     */
    public void startRecordingGeneric() {
        Log.e("Recorder", "Name: " + name + " Country: " + country);
        String filename = path + name + country + "temp.3gp";
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
        }
        recorder.start();

        // start runnable, will stop recording after 4 seconds
        handler = new Handler();
        handler.postDelayed(runnable, 4000);

        // change recording icon and background to give user visual feedback
        if (mode == RecordingMode.RECORDING_LAST) {
            recordLast.setImageResource(R.drawable.ic_mic_none_black_24dp);
        } else {
            recordFirst.setImageResource(R.drawable.ic_mic_none_black_24dp);
        }
    }


    /**
     * Shows a dialog asking the user if he wants to overwrite the existing file.

     */
    private void confirmGenericDialog() {
        // TODO CREATE NEW DIALOG LAYOUT
        String nameCap = "";
        String countryCap = "";
        if (name.length() > 0) {
            nameCap = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
        if (country.length() > 1) {
            countryCap = Character.toUpperCase(name.charAt(0)) + country.substring(1);
        }
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
            if (countryCap.equals("")) {
                textDialog.setText("A generic audio file for the name " + nameCap + " already exists. Overwrite?");
            } else {
                textDialog.setText("A generic audio file for the name " + nameCap + " from " + countryCap + " already exists. Overwrite?");
            }

            // cancel
            ImageButton cancelDialog = (ImageButton) win.findViewById(R.id.cancel_dialog);

            cancelDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // dont overwrite, quit
                    alertDialog.dismiss();
                    ((NewContactActivity) context).enableButtons();;
                }
            });


            // play
            final ImageButton playDialog = (ImageButton) win.findViewById(R.id.play_dialog);

            playDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!player.isPlaying()) {
                        player.startPlaying(name + country, playDialog);
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
                    // overwrite
                    startRecordingGeneric();

                }
            });
        }
    }
    /*
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