package com.team16.sopra.sopra16team16.View;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

/**
 * Displays a FileChooser, allowing the user to choose a file from his external storage
 *
 * https://rogerkeays.com/simple-android-file-chooser
 */
public class FileChooser {
    private static final String PARENT_DIR = "..";

    private final Activity activity;
    private ListView list;
    private Dialog dialog;
    private File currentPath;

    // filter on file extension
    private String extension = null;

    /**
     * Sets the extension value
     * @param extension String - extension
     */
    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }

    /**
     * Sets the fileListener
     * @param fileListener fileListener to be used
     * @return
     */
    public FileChooser setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }
    private FileSelectedListener fileListener;

    /**
     * Constructor
     * Builds the dialog
     * @param activity Activity for the FileChooser to be displayed in
     */
    public FileChooser(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        list = new ListView(activity);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                String fileChosen = (String) list.getItemAtPosition(which);
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    refresh(chosenFile);
                } else {
                    if (fileListener != null) {
                        fileListener.fileSelected(chosenFile);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(list);
        dialog.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        refresh(Environment.getExternalStorageDirectory());
    }

    /**
     * Shows the dialog window
     */
    public void showDialog() {
        dialog.show();
    }


    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {
        this.currentPath = path;
        if (path.exists()) {
            File[] dirs = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    return (file.isDirectory() && file.canRead());
                }
            });
            if (dirs != null && dirs.length != 0) {
                File[] files = path.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if (!file.isDirectory()) {
                            if (!file.canRead()) {
                                return false;
                            } else if (extension == null) {
                                return true;
                            } else {
                                return file.getName().toLowerCase().endsWith(extension);
                            }
                        } else {
                            return false;
                        }
                    }
                });

                // convert to an array
                int i = 0;
                String[] fileList;
                if (path.getParentFile() == null) {
                    fileList = new String[dirs.length + files.length];
                } else {
                    fileList = new String[dirs.length + files.length + 1];
                    fileList[i++] = PARENT_DIR;
                }
                Arrays.sort(dirs);
                Arrays.sort(files);
                for (File dir : dirs) {
                    fileList[i++] = dir.getName();
                }
                for (File file : files) {
                    fileList[i++] = file.getName();
                }

                // refresh the user interface
                dialog.setTitle(currentPath.getPath());
                list.setAdapter(new ArrayAdapter(activity,
                        android.R.layout.simple_list_item_1, fileList) {
                    @Override
                    public View getView(int pos, View view, ViewGroup parent) {
                        view = super.getView(pos, view, parent);
                        ((TextView) view).setSingleLine(true);
                        return view;
                    }
                });
            }
        }
    }


    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }
}
