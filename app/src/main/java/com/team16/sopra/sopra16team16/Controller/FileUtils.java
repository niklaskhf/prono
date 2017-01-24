package com.team16.sopra.sopra16team16.Controller;

import android.util.Log;

import com.team16.sopra.sopra16team16.View.HomeActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Contains methods for manipulating files, like copying/replacing.
 */

public class FileUtils {

    public static final String PATH = HomeActivity.contextOfApplication.getFilesDir().getPath() + "/";

    /**
     * Replaces the original file with the replacement file
     * This assumes that both files are located in the same folder
     * @param original
     * @param replacement
     */
    public static void replaceFile(String original, String replacement) {
        File temp = new File(replacement);
        File perm = new File(original);

        if (perm.exists() && temp.exists()) {
            perm.delete();

            Log.d("FileUtils", "deleted " + perm + " while copying temp");
        }
        if (temp.exists()) {
            temp.renameTo(perm);
            Log.d("FileUtils", "renamed " + temp + " to " + perm);
        }
    }


    /**
     * Renames a file
     * @param from original file
     * @param to new path
     */
    public static void renameFile(String from, String to) {
        File orig = new File(from);
        File repl = new File(to);

        if (orig.exists()) {
            orig.renameTo(repl);
            Log.d("FileUtils", "renamed " + from + " to " + to);
        }

    }

    /**
     * Deletes a file
     * @param from the file that is to be deleted
     */
    public static void deleteFile(String from) {
        File file = new File(from);

        if(file.exists()) {
            file.delete();
            Log.d("FileUtils", "deleted file " + from);
        }
    }


    /**
     * Renames a temp audio file to the permanent version.
     * @param id id of the contact - int
     */
    public static void confirmAudio(int id) {
        String path = HomeActivity.contextOfApplication.getFilesDir().getPath() + "/";
        File temp = new File(path + id + "_temp.3gp");
        File perm = new File(path + id + ".3gp");

        if (perm.exists() && temp.exists()) {
            FileUtils.renameFile(
                    path + id + ".3gp",
                    path + id + "_undo.3gp"
            );
        }
        if (temp.exists()) {
            temp.renameTo(perm);
            Log.d("recorder", "renamed " + temp + " to " + perm);
        }
    }

    /**
     * Deletes *temp.3gp files in the files directory.
     */
    public static void deleteTempFiles() {
        File[] files = new File(PATH).listFiles();

        for (File f : files) {
            String fp = f.getPath();
            if (fp.length() > 8) {
                String fpsub = fp.substring(fp.length() - 9, fp.length());
                if (fpsub.equals("_temp.3gp")) {
                    Log.d("deletingTempFile", fp);
                    new File(fp).delete();
                }
            }
        }
    }


    /**
     * Copies a file
     *
     * fromFile - FileInputStream - source
     * toFile - FileOutputStream - target
     */
    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
            Log.d("COPY", "COPY");
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }
}
