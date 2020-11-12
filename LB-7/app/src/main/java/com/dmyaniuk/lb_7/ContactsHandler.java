package com.dmyaniuk.lb_7;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import db.DatabaseHelper;
import models.Constants;
import models.Contact;

public class ContactsHandler extends AppCompatActivity {
    private class DatabaseHandler extends AsyncTask<String, Void, String> {
        private DatabaseHelper databaseHelper;
        private SQLiteDatabase sqLiteDatabase;

        @Override
        protected void onPreExecute() {
            this.databaseHelper = new DatabaseHelper(ContactsHandler.this);
            this.sqLiteDatabase = this.databaseHelper.getWritableDatabase();
        }

        @Override
        protected String doInBackground(String... actions) {
            String name = ContactsHandler.this.nameEditText.getText().toString();
            String phone = ContactsHandler.this.phoneEditText.getText().toString();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.KEY_NAME, name);
            contentValues.put(DatabaseHelper.KEY_PHONE, phone);

            if (actions[0].equals(Constants.AddAction)) {
                contentValues.put(DatabaseHelper.KEY_SELECTED, 0);
                this.sqLiteDatabase.insert(
                        DatabaseHelper.TableName,
                        null,
                        contentValues
                );

                return actions[0];
            }

            if (actions[0].equals(Constants.UpdateAction)) {
                contentValues.put(DatabaseHelper.KEY_SELECTED, ContactsHandler.this.contact.getSelected());
                this.sqLiteDatabase.update(
                        DatabaseHelper.TableName,
                        contentValues,
                        DatabaseHelper.KEY_ID + "=" + ContactsHandler.this.contact.getId(),
                        null
                );

                return actions[0];
            }

            return actions[0];
        }

        @Override
        protected void onPostExecute(String action) {
            Toast.makeText(
                    ContactsHandler.this,
                    action.equals(Constants.AddAction) ? "Contact has been added" : "Contact has been updated",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private Button handleButton;
    private EditText nameEditText;
    private EditText phoneEditText;

    private Contact contact;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_handler);

        this.initializeElements();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void handleContact(View v) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.execute(this.action);
    }

    private void initializeElements() {
        this.handleButton = findViewById(R.id.handleButton);
        this.nameEditText = findViewById(R.id.nameEditText);
        this.phoneEditText = findViewById(R.id.phoneEditText);

        this.action = getIntent().getStringExtra("action");

        if (this.action.equals(Constants.AddAction)) {
            this.handleButton.setText(Constants.AddAction);
        } else if (this.action.equals(Constants.UpdateAction)) {
            this.contact = (Contact) getIntent().getSerializableExtra("contact");

            this.nameEditText.setText(this.contact.getName());
            this.phoneEditText.setText(this.contact.getPhone());
            this.handleButton.setText(Constants.UpdateAction);
        }
    }
}