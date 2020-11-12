package com.dmyaniuk.myapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ContactCreation extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText locationEditText;
    private EditText socialNetworkEditText;
    private TextView vkLink;
    private FileHandler fileHandler;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_creation);

        this.initializeElements();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void createContact(View v) {
        int id = this.getNewIdForContact();
        String name = this.nameEditText.getText().toString();
        String phone = this.phoneEditText.getText().toString();
        String email = this.emailEditText.getText().toString();
        String location = this.locationEditText.getText().toString();
        String socialNetwork = this.vkLink.getText().toString() + this.socialNetworkEditText.getText().toString();

        Contact newContact = new Contact(id, name, email, location, phone, socialNetwork);
        this.contacts.add(newContact);
        this.fileHandler.saveToStorage(this.contacts);

        Toast.makeText(this, "Contact has been added!", Toast.LENGTH_LONG).show();
    }

    private int getNewIdForContact() {
        int newId = 0;
        for (Contact contact : this.contacts) {
            if (newId < contact.getId()) {
                newId = contact.getId();
            }
        }

        return newId + 1;
    }

    private void initializeElements() {
        this.nameEditText = findViewById(R.id.nameEditText);
        this.phoneEditText = findViewById(R.id.phoneEditText);
        this.emailEditText = findViewById(R.id.emailEditText);
        this.locationEditText = findViewById(R.id.locationEditText);
        this.socialNetworkEditText = findViewById(R.id.socialNetworkEditText);
        this.vkLink = findViewById(R.id.vkLink);
        this.fileHandler = new FileHandler(this);
        this.contacts = this.fileHandler.readFromStorage();
    }
}