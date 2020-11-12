package com.dmyaniuk.lb_10;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dmyaniuk.constants.Constants;
import com.dmyaniuk.db.entities.Contact;
import com.dmyaniuk.viewmodel.MainActivityViewModel;

public class ContactHandler extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText locationEditText;
    private Button contactHandlerButton;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView locationTextView;

    private String action;
    private Contact contact;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_handler);

        this.initializeItems();
        this.showContactInfo();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void initializeItems() {
        this.nameEditText = findViewById(R.id.nameEditText);
        this.phoneEditText = findViewById(R.id.phoneEditText);
        this.locationEditText = findViewById(R.id.locationEditText);
        this.contactHandlerButton = findViewById(R.id.handleContactButton);
        this.nameTextView = findViewById(R.id.nameTextView);
        this.phoneTextView = findViewById(R.id.phoneTextView);
        this.locationTextView = findViewById(R.id.locationTextView);

        this.action = getIntent().getStringExtra("action");

        this.mainActivityViewModel = new MainActivityViewModel();
        this.contact = new Contact();

        if (!this.action.equals(Constants.AddAction)) {
            this.contact = mainActivityViewModel.getSelectedContact().getValue();
        }
    }

    private void showContactInfo() {
        switch (this.action) {
            case Constants.AddAction:
                this.contactHandlerButton.setText(this.action);
                break;
            case Constants.UpdateAction:
                this.nameEditText.setText(this.contact.getName());
                this.phoneEditText.setText(this.contact.getPhone());
                this.locationEditText.setText(this.contact.getLocation());
                this.contactHandlerButton.setText(this.action);
                break;
            case Constants.ViewAction:
                this.nameEditText.setVisibility(View.INVISIBLE);
                this.phoneEditText.setVisibility(View.INVISIBLE);
                this.locationEditText.setVisibility(View.INVISIBLE);
                this.contactHandlerButton.setVisibility(View.INVISIBLE);
                this.contactHandlerButton.setVisibility(View.INVISIBLE);

                this.nameTextView.setText(this.contact.getName());
                this.phoneTextView.setText(this.contact.getPhone());
                this.locationTextView.setText(this.contact.getLocation());
            default:
                break;
        }
    }

    public void handleContact(View v) {
        this.contact.setName(this.nameEditText.getText().toString());
        this.contact.setPhone(this.phoneEditText.getText().toString());
        this.contact.setLocation(this.locationEditText.getText().toString());

        if (action.equals(Constants.AddAction)) {
            this.mainActivityViewModel.addContact(this.contact);
        } else if (action.equals(Constants.UpdateAction)) {
            this.mainActivityViewModel.updateContact(this.contact);
        }
    }
}