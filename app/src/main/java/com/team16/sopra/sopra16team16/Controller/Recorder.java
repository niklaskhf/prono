package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Martin on 17.11.2016.
 */

/**
 *
 * Recorder
 *
 */
public class Recorder{
    private static Recorder currentInstance = null;

    //private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    //ToDo Pfad über Context finden
    String path;
    private MediaRecorder recorder = null;
    private boolean is_recording = false;
    private Context context;

    //constructor - need context for path
    private Recorder(Context context) {
        path = context.getApplicationContext().getFilesDir().getPath();
        context = context;
    }

    //Singelton
    public static Recorder getCurrentInstance(Context context) {
        if (currentInstance == null) {
            currentInstance = new Recorder(context);
            return currentInstance;
        } else {
            return currentInstance;
        }
    }


    /**
     * Startet Recorder
     *  * @param id id from the contact is the same as the id from the file
     */
    public void startRecording(int id) {
        Log.e("Record", "ID: " + id);
        String filename = path + id + ".3gp";
        Log.e("Recorder", "Filename: " + filename);
        changeStatus(true);



        recorder = new MediaRecorder();
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
    }

    /*
     * Stoppt die Aufnahme des Namens
     */
    public void stopRecording() {
        Log.e("Recorder", "Aufnahme zu Ende");
        changeStatus(false);
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    /*
     * true  --> is recording
     * false --> is not recording
     */
    public boolean isPressed() {
        return is_recording;
    }

    private void changeStatus(boolean status) {
        is_recording = status;
    }


}