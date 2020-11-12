package com.dmyaniuk.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;

public class DialogHandler {
    private final Activity activity;
    private final FileHandler fileHandler;

    public DialogHandler(Activity activity, FileHandler fileHandler) {
        this.activity = activity;
        this.fileHandler = fileHandler;
    }

    private AlertDialog.Builder createDialog(String title, String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this.activity);
        dialogBuilder.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(message)
                .setNegativeButton("No", null)
                .create();

        return dialogBuilder;
    }

    public AlertDialog removeContacts(String title, String message, final ArrayList<Contact> contacts) {
        final MainActivity mainActivity = (MainActivity)activity;
        return this.createDialog(title, message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fileHandler.saveToStorage(contacts);
                mainActivity.renderContacts();
            }
        }).create();
    }

    public AlertDialog updateContacts(String title, String message, final ArrayList<Contact> contacts) {
        return this.createDialog(title, message).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fileHandler.saveToStorage(contacts);
                Toast.makeText(activity, "Contact has been updated!", Toast.LENGTH_LONG).show();
            }
        }).create();
    }
}
