package com.team16.sopra.sopra16team16.Controller;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Martin on 17.11.2016.
 */

public class Recorder {
    private static Recorder currentInstance = null;

    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private MediaRecorder recorder = null;
    private boolean is_recording = false;


    public static Recorder getCurrentInstance() {
        if (currentInstance == null) {
            currentInstance = new Recorder();
            return currentInstance;
        } else {
            return currentInstance;
        }
    }


    /*
     * Startet die Aufnahme des Namens
     */
    public void startRecording(int id) {

        String filename = path + id + ".3gp";
        changeStatus(true);

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC); //AudioSource.MIC //AudioSource.VOICE_RECOGNITION
        recorder.setOutputFile(filename);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("RECORDER_LOG", "prepare failed");
        }
    }

    /*
     * Stoppt die Aufnahme des Namens
     */
    public void stopRecording() {
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