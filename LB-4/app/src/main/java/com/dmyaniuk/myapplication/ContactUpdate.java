package com.dmyaniuk.myapplication;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ContactUpdate extends AppCompatActivity {
    private Contact selectedContact;
    private ArrayList<Contact> contacts;
    private FileHandler fileHandler;
    private DialogHandler dialogHandler;

    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText locationEditText;
    private EditText vkEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_update);

        this.initializeElements();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void updateContact(View v) {
        String newName = this.nameEditText.getText().toString();
        String newPhone = this.phoneEditText.getText().toString();
        String newEmail = this.emailEditText.getText().toString();
        String newLocation = this.locationEditText.getText().toString();
        String newVkLink = this.vkEditText.getText().toString();

        if (newName.equals("")
                || newPhone.equals("")
                || newEmail.equals("")
                || newLocation.equals("")
                || newVkLink.equals("")
        ) {
            Toast.makeText(this, "Data cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        this.updateExistingContact();
        this.dialogHandler.updateContacts(
                "Update",
                "Are you sure you want to update?",
                this.contacts
        ).show();
    }

    private void initializeElements() {
        this.nameEditText = findViewById(R.id.nameEditText);
        this.phoneEditText = findViewById(R.id.phoneEditText);
        this.emailEditText = findViewById(R.id.emailEditText);
        this.locationEditText = findViewById(R.id.locationEditText);
        this.vkEditText = findViewById(R.id.vkEditText);

        String serializableContact = (String)getIntent().getSerializableExtra("contact");
        this.selectedContact = new Gson().fromJson(serializableContact, Contact.class);
        this.fileHandler = new FileHandler(this);
        this.contacts = this.fileHandler.readFromStorage();
        this.dialogHandler = new DialogHandler(this, this.fileHandler);

        this.nameEditText.setText(this.selectedContact.getName());
        this.phoneEditText.setText(this.selectedContact.getPhone());
        this.emailEditText.setText(this.selectedContact.getEmail());
        this.locationEditText.setText(this.selectedContact.getLocation());
        this.vkEditText.setText(this.selectedContact.getSocialNetwork());
    }

    private void updateExistingContact() {
        for (Contact contact:this.contacts) {
            if (contact.getId() == this.selectedContact.getId()) {
                contact.setName(this.nameEditText.getText().toString());
                contact.setPhone(this.phoneEditText.getText().toString());
                contact.setEmail(this.emailEditText.getText().toString());
                contact.setLocation(this.locationEditText.getText().toString());
                contact.setSocialNetwork(this.vkEditText.getText().toString());

                break;
            }
        }
    }
}