package com.team16.sopra.sopra16team16.Controller;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.team16.sopra.sopra16team16.Model.DBHelper;
import com.team16.sopra.sopra16team16.Model.DBManager;
import com.team16.sopra.sopra16team16.R;
import com.team16.sopra.sopra16team16.View.FileChooser;
import com.team16.sopra.sopra16team16.View.HomeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Contains methods for importing data into the app.
 */

public class Import {

    private String importPath;
    private Resources resources = HomeActivity.contextOfApplication.getResources();

    /**
     * Triggers the importing dialog.
     * @param context
     */
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

                        if (importPath.substring(importPath.length() - 4, importPath.length()).equals(".zip")
                                && importPath.contains("pronoBackup")) {
                            importDB(importPath);
                        } else {
                            Toast.makeText(HomeActivity.contextOfApplication, resources.getString(R.string.file_not_accepted),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        fileChooser.setExtension(".zip");
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
            // unzip the files to the /files/import/ directory
            unzip(importPath, "/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                    + "/files/import/");

            // move and replace the database file
            String backupDBPath = "/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                    + "/files/import/" + DBHelper.DATABASE_NAME;

            boolean result = DBManager.getCurrentInstance(HomeActivity.contextOfApplication).replaceDatabase(backupDBPath);

            // tell the user
            if (result) {
                try {
                    // copy the audio files from ../files/import/ to /files/

                    // wipe /files/ directory
                    String filesPath = "/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                            + "/files/";
                    String importFilesPath = "/data/data/" + HomeActivity.contextOfApplication.getPackageName()
                            + "/files/import/";

                    for (File f : new File(filesPath).listFiles()) {
                        if (f.getPath().endsWith(".3gp")) {
                            f.delete();
                        }
                    }

                    // copy audio files from ../files/import/ to ../files/
                    for (File f : new File(importFilesPath).listFiles()) {
                        if (f.getPath().endsWith(".3gp")) {
                            FileUtils.renameFile(importFilesPath + f.getName(), filesPath + f.getName());
                        }
                    }
                    Toast.makeText(HomeActivity.contextOfApplication, resources.getString(R.string.successful_import),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(HomeActivity.contextOfApplication, resources.getString(R.string.failed_import),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HomeActivity.contextOfApplication, resources.getString(R.string.failed_import),
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
