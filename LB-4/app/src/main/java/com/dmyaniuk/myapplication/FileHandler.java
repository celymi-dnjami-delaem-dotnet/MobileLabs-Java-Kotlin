package com.dmyaniuk.myapplication;

import android.app.Activity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FileHandler {
    private final String fileName = "Contacts.txt";
    private final Activity mainActivity;

    public FileHandler(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void saveToStorage(ArrayList<Contact> contacts) {
        Gson gson = new Gson();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = this.mainActivity.openFileOutput(this.fileName, MODE_PRIVATE);
            fileOutputStream.write(gson.toJson(contacts).getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<Contact> readFromStorage() {
        Gson gson = new Gson();
        FileInputStream fileInputStream;
        InputStreamReader streamReader;
        try {
            fileInputStream = this.mainActivity.openFileInput(this.fileName);
            streamReader = new InputStreamReader(fileInputStream);

            return gson.fromJson(streamReader, new TypeToken<List<Contact>>() {}.getType());
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
