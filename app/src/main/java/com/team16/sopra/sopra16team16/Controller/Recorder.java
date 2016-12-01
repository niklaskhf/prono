package com.team16.sopra.sopra16team16.Controller;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Martin on 17.11.2016.
 */

public class Recorder {
    private static String filename = null;
    private MediaRecorder recorder = null;

    /*
     * Konstruktur: Der Pfad zur Datei wird hier definiert
     */
    public Recorder() {
        filename = Environment.getExternalStorageDirectory().getAbsolutePath();

        //ToDo: Filename ist noch falsch. name sollte die id des Kontaktes aus der Datenbank sein
        filename += "000001.3gp";
    }

    /*
     * Startet die Aufnahme des Namens
     */
    public void startRecording() {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION); //AudioSource.MIC
        recorder.setOutputFile(filename);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e("RECORDER_LOG", "prepare fehlgeschlagen");
        }
    }

    /*
     * Stoppt die Aufnahme des Namens
     */
    public void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }
}