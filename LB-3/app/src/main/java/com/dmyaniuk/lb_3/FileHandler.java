package com.dmyaniuk.lb_3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;

public class FileHandler {
    private MainActivity activity;
    private File file;

    public FileHandler(MainActivity activity, File file) {
        this.activity = activity;
        this.file = file;
    }

    public void saveToFile(List<Note> notes, String selectedStorageType) {
        Gson gson = new Gson();
        String jsonNote = gson.toJson(notes);

        FileOutputStream fileOutput = null;
        try {
            switch (selectedStorageType) {
                case Constants.InternalStorage:
                    fileOutput = activity.openFileOutput(Constants.FileName, MODE_PRIVATE);
                    break;
                case Constants.ExternalStorage:
                    fileOutput = new FileOutputStream(file);
                    break;
            }

            fileOutput.write(jsonNote.getBytes());
            fileOutput.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<Note> readFromFile(String selectedStorageType) {
        Gson gson = new Gson();

        FileInputStream fileInput = null;
        InputStreamReader streamReader;
        try {
            switch (selectedStorageType) {
                case Constants.InternalStorage:
                    fileInput = activity.openFileInput(Constants.FileName);
                    break;
                case Constants.ExternalStorage: {
                    fileInput = new FileInputStream(file);
                    break;
                }
            }
            streamReader = new InputStreamReader(fileInput);
            ArrayList<Note> notes = gson.fromJson(streamReader, new TypeToken<List<Note>>() {}.getType());

            ToastHandler.showToast(activity, "File has been loaded");

            return notes;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}