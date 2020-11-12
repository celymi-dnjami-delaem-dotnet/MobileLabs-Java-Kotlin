package com.dmyaniuk.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;

public class ContactInfo extends AppCompatActivity implements View.OnClickListener {
    private Contact selectedContact;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView emailTextView;
    private TextView locationTextView;
    private TextView socialNetworkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        this.initializeElements();
        this.showContactInfo();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.phone:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + this.phoneTextView.getText().toString()));
                break;
            case R.id.email:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { this.emailTextView.getText().toString() });
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                break;
            case R.id.location:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + this.locationTextView.getText().toString()));
                break;
            case R.id.socialNetwork:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.socialNetworkTextView.getText().toString()));
                break;
            default:
                break;
        }

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initializeElements() {
        this.nameTextView = findViewById(R.id.name);
        this.phoneTextView = findViewById(R.id.phone);
        this.emailTextView = findViewById(R.id.email);
        this.locationTextView = findViewById(R.id.location);
        this.socialNetworkTextView = findViewById(R.id.socialNetwork);

        String serializableContact = (String)getIntent().getSerializableExtra("contact");
        this.selectedContact = new Gson().fromJson(serializableContact, Contact.class);
    }

    private void showContactInfo() {
        this.nameTextView.setText(this.selectedContact.getName());
        this.phoneTextView.setText(this.selectedContact.getPhone());
        this.emailTextView.setText(this.selectedContact.getEmail());
        this.locationTextView.setText(this.selectedContact.getLocation());
        this.socialNetworkTextView.setText(this.selectedContact.getSocialNetwork());
    }
}