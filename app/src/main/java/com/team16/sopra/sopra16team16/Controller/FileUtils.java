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
     */
    public static void deleteFile(String from) {
        File file = new File(from);

        if(file.exists()) {
            file.delete();
            Log.d("FileUtils", "deleted file " + from);
        }
    }



    /**
     * Checks if any recording associated to the id exists.
     * @param id id of the contact - int
     */
    public static boolean exists(int id) {
        // TODO change parameter to be the path
        String path = HomeActivity.contextOfApplication.getFilesDir().getPath() + "/";
        File temp = new File(path + id + "temp.3gp");
        File perm = new File(path + id + ".3gp");

        if(!perm.exists() && !temp.exists()) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Renames a temp audio file to the permanent version.
     * @param id id of the contact - int
     */
    public static void confirmAudio(int id) {
        String path = HomeActivity.contextOfApplication.getFilesDir().getPath() + "/";
        File temp = new File(path + id + "temp.3gp");
        File perm = new File(path + id + ".3gp");

        if (perm.exists() && temp.exists()) {
            FileUtils.renameFile(
                    path + id + ".3gp",
                    path + id + "_undo.3gp"
            );
            if (perm.exists()) {
                perm.delete();
                Log.d("recorder", "deleted " + perm + " while copying temp");
            }

        }
        if (temp.exists()) {
            temp.renameTo(perm);
            Log.d("recorder", "renamed " + temp + " to " + perm);
        }

        deleteTempFiles();
    }

    /**
     * Renames a temp audio file to the permanent version.
     * @param id id of the contact - int
     */
    public static void confirmAudio(String id) {
        String path = HomeActivity.contextOfApplication.getFilesDir().getPath() + "/";
        File temp = new File(path + id + "temp.3gp");
        File perm = new File(path + id + ".3gp");

        if (perm.exists() && temp.exists()) {
            FileUtils.renameFile(
                    path + id + ".3gp",
                    path + id + "_undo.3gp"
            );
            if (perm.exists()) {
                perm.delete();
                Log.d("recorder", "deleted " + perm + " while copying temp");
            }

        }
        if (temp.exists()) {
            temp.renameTo(perm);
            Log.d("recorder", "renamed " + temp + " to " + perm);
        }

        deleteTempFiles();
    }


    /**
     * Copies a file
     */
    public static void copy(File src, File dst) throws IOException {
        if (src.exists()) {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dst);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        }
    }

    /**
     * Deletes temp audio files.
     */
    public static void deleteTempFiles() {
        File[] files = new File(PATH).listFiles();

        for (File f : files) {
            String fp = f.getPath();
            if (fp.length() > 8) {
                String fpsub = fp.substring(fp.length() - 8, fp.length());
                Log.d("deletingFile", fp);
                if (fpsub.equals("temp.3gp")) {
                    new File(fp).delete();
                }
            }
        }
    }
}
