package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.Model.DBHelper;
import com.team16.sopra.sopra16team16.Model.DBManager;
import com.team16.sopra.sopra16team16.View.FileChooser;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class Backup {

    private String importPath;

    public void importDBDialog(Context context) {
        String data = Environment.getExternalStorageDirectory().toString();

        // let the user select a file
        // THIS IS KIND OF BUGGED
        // OPEN THE FINAL ROOT MIGHT CRASH THE APP
        FileChooser fileChooser = new FileChooser((HomeActivity) context)
                .setFileListener(new FileChooser.FileSelectedListener() {

            @Override
            public void fileSelected(File file) {
                // import the files from the selected path
                importPath = file.getPath();
                // TODO verify file name is valid
                if (importPath.substring(importPath.length() - 4, importPath.length()).equals(".zip")
                        && importPath.contains("prono")) {
                    importDB(importPath);
                } else {
                    Toast.makeText(HomeActivity.contextOfApplication, "Import failed, file not accepted",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        fileChooser.showDialog();
    }

    /**
     * Imports data from a zip file
     * DOES NOT VERIFY ANYTHING YET
     * MIGHT CORRUPT EVERYTHING
     *
     * @param importPath new database - path
     */
    public void importDB(String importPath) {

        try {
            // unzip the files to the /files/ directory
            unzip(importPath, "/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                    + "/files/");

            // move and replace the database file
            String backupDBPath = "/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                    + "/files/" + DBHelper.DATABASE_NAME;
            boolean result = false;
            result = DBHelper.getCurrentInstance(HomeActivity.contextOfApplication).importDatabase(backupDBPath);

            // tell the user
            if (result) {
                Toast.makeText(HomeActivity.contextOfApplication, "Import Successful",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HomeActivity.contextOfApplication, "Import failed",
                        Toast.LENGTH_SHORT).show();
            }

            // open/close database
            DBManager dbManager = DBManager.getCurrentInstance(HomeActivity.contextOfApplication);
            dbManager.close();
            dbManager.reopen();
            ContactManager contactManager = ContactManager.getInstance(HomeActivity.contextOfApplication);
            contactManager.open();

            // delete the now unnecessary database file in /files/
            FileUtils.deleteFile(backupDBPath);
        } catch (Exception e) {
            // handle exceptio
            Log.d("ImportIOEXception", e.getMessage());
        }

    }

    /**
     * Exports the database and all audio files into a zip file located on the sd card
     */
    public void exportDB() {
        try {
            // file path of audio files
            // everything is located in /files/
            File filesPath = new File("//data//data//" + HomeActivity.contextOfApplication.getPackageName()
                    + "//files//");
            // String[] of files that will be zipped
            // size is /files/.length - 1 (instant-run folder) + 1 (database file)
            String[] files = new String[filesPath.listFiles().length];

            // get the audio files
            // 1 to skip the instant-run directory
            for (int i = 0; i < files.length - 1; i++) {
                files[i] = filesPath.listFiles()[i+1].toString();
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
            Toast.makeText(HomeActivity.contextOfApplication, "Backup Successful!\nSaved to " + fileName, Toast.LENGTH_SHORT)
                    .show();
        } catch (Exception e) {
            Toast.makeText(HomeActivity.contextOfApplication, "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();
            Log.d("EXPORT", e.getMessage());
        }
    }


    /**
     * zips all files in files[] to the zip file zipFile
     * @param files path - file array containing all files that are to be zipped
     * @param zipFile path - zip file location
     * @throws IOException
     */
    public static void zip(String[] files, String zipFile) throws IOException {
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


    /**
     * Unzips a zip file to the given location
     * @param zipFile path - location of the zip file
     * @param location path - where to put the file
     * @throws IOException
     */
    public static void unzip(String zipFile, String location) throws IOException {
        try {
            File f = new File(location);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        FileOutputStream fout = new FileOutputStream(path, false);
                        try {
                            for (int c = zin.read(); c != -1; c = zin.read()) {
                                fout.write(c);
                            }
                            zin.closeEntry();
                        } finally {
                            fout.close();
                        }
                    }
                }
            } finally {
                zin.close();
            }
        } catch (Exception e) {
            Log.e("UNZIP", "Unzip exception", e);
        }
    }


}
