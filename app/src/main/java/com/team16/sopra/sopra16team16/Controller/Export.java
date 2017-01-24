package com.team16.sopra.sopra16team16.Controller;

import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.Model.DBHelper;
import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Contains methods for exporting the app data.
 */
public class Export {

    private Resources resources = HomeActivity.contextOfApplication.getResources();
    /**
     * Exports the database and all audio files into a zip file located on the sd card
     */
    public void exportDB() {
        try {
            // file path of audio files
            // everything is located in /files/
            File filesPath = new File("//data//data//" + HomeActivity.contextOfApplication.getPackageName()
                    + "//files//");
            File[] fileDir = filesPath.listFiles();

            for (int i = 0; i < fileDir.length; i++) {
                Log.d("fileDir", "fileDir[" + Integer.toString(i) + "]:" + fileDir[i]);
            }

            ArrayList<String> audios = new ArrayList<String>();

            for (File e : fileDir) {
                if (e.getName().endsWith(".3gp")) {
                    audios.add(e.toString());
                }
            }

            // String[] of files that will be zipped
            // size is audio files + 1 (database file)
            String[] files = new String[audios.size() + 1];

            // get the audio files
            // 1 to skip the instant-run directory
            for (int i = 0; i < audios.size(); i++) {
                    files[i] = audios.get(i);
            }

            // get the database file
            String dbPath = "//data//data//" + HomeActivity.contextOfApplication.getPackageName()
                    + "//databases//" + DBHelper.DATABASE_NAME;
            files[files.length - 1] = dbPath;

            // name of the backup file is
            // pronoBackupYYYYMMDD_HHMMSS.zip
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDateandTime = sdf.format(new Date());

            String sd = Environment.getExternalStorageDirectory().toString();
            String fileName = sd + "/pronoBackup" + currentDateandTime + ".zip";

            for (int i = 0; i < files.length; i++) {
                Log.d("file", "files[" + Integer.toString(i) + "]:" + files[i]);
            }

            // create the zip file
            zip(files, fileName);

            // tell the user
            Toast.makeText(HomeActivity.contextOfApplication,  resources.getString(R.string.successful_export) + fileName, Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            Toast.makeText(HomeActivity.contextOfApplication, resources.getString(R.string.failed_export), Toast.LENGTH_SHORT)
                    .show();
            Log.d("EXPORT", e.getMessage());
        }
    }


    /**
     * zips all files in files[] to the .zip file zipFile
     *
     * @param files path - file array containing all files that are to be zipped
     * @param zipFile path - zip file location
     * @throws IOException
     */
    private static void zip(String[] files, String zipFile) throws IOException {
        BufferedInputStream origin = null;
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        int BUFFER_SIZE = 8192;

        try {
            byte data[] = new byte[BUFFER_SIZE];

            for (int i = 0; i < files.length; i++) {
                FileInputStream fi = new FileInputStream(files[i]);
                origin = new BufferedInputStream(fi, BUFFER_SIZE);
                try {
                    ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
                    out.putNextEntry(entry);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                        out.write(data, 0, count);
                    }
                } finally {
                    origin.close();
                }
            }
        } finally {
            out.close();
        }
    }
}
