package com.dmyaniuk.lb_6;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dmyaniuk.models.Constants;
import com.dmyaniuk.models.Contact;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class ContactHandler extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button actionButton;
    private Contact contact;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_handler);

        this.initializeElements();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initializeElements() {
        this.nameEditText = findViewById(R.id.nameEditText);
        this.phoneEditText = findViewById(R.id.phoneEditText);
        this.actionButton = findViewById(R.id.actionButton);

        String userEmail = getIntent().getStringExtra("user");
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDatabase.getReference("users").child(userEmail);

        String action = getIntent().getStringExtra("Action");
        if (action.equals(Constants.UpdateContactAction)) {
            this.contact = new Gson().fromJson(getIntent().getStringExtra("Contact"), Contact.class);
            this.nameEditText.setText(this.contact.getName());
            this.phoneEditText.setText(this.contact.getPhone());
            this.actionButton.setText(Constants.UpdateContactAction);
            this.actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Contact updatedContact = new Contact();
                    updatedContact.setName(ContactHandler.this.nameEditText.getText().toString());
                    updatedContact.setPhone(ContactHandler.this.phoneEditText.getText().toString());
                    ContactHandler.this.databaseReference.child(ContactHandler.this.contact.getId()).setValue(updatedContact);
                    Toast.makeText(
                            ContactHandler.this,
                            "Contact has been updated!",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

            return;
        }

        this.actionButton.setText(Constants.AddContactAction);
        this.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact newContact = new Contact();
                newContact.setName(ContactHandler.this.nameEditText.getText().toString());
                newContact.setPhone(ContactHandler.this.phoneEditText.getText().toString());
                ContactHandler.this.databaseReference.push().setValue(newContact);
                Toast.makeText(
                        ContactHandler.this,
                        "Contact has been added!",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}