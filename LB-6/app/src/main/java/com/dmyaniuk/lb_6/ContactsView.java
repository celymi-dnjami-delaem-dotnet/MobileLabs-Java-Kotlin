package com.dmyaniuk.lb_6;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.dmyaniuk.models.Constants;
import com.dmyaniuk.models.Contact;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ContactsView extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ListView contactsListView;

    private String userEmail;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Contact selectedContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_view);

        this.initializeElements();
        this.renderContacts();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.updateMenuItem:
                this.callContactHandler(Constants.UpdateContactAction);
                break;
            case R.id.deleteMenuItem:
                this.databaseReference.child(this.selectedContact.getId()).removeValue();
                Toast.makeText(this, "Contact has been removed", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        return true;
    }

    public void addUser(View v) {
        this.callContactHandler(Constants.AddContactAction);
    }

    private void initializeElements() {
        this.contactsListView = findViewById(R.id.contactsListView);

        this.userEmail = getIntent().getStringExtra("user").replace('.', '_');
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = this.firebaseDatabase.getReference("users").child(userEmail);
    }

    private void renderContacts() {
        Query databaseQuery = this.databaseReference;
        final ArrayAdapter contactArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        this.contactsListView.setAdapter(contactArrayAdapter);

        this.contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                PopupMenu popupMenu = new PopupMenu(ContactsView.this, v);
                popupMenu.inflate(R.menu.popup);
                popupMenu.setOnMenuItemClickListener(ContactsView.this);
                popupMenu.show();
                selectedContact = (Contact)contactArrayAdapter.getItem(position);
            }
        });

        databaseQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Contact contact = snapshot.getValue(Contact.class);
                contact.setId(snapshot.getKey());
                contactArrayAdapter.add(contact);
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                Contact updatedContact = snapshot.getValue(Contact.class);
                updatedContact.setId(snapshot.getKey());
                
                for (int i = 0; i < contactArrayAdapter.getCount(); i++) {
                    Contact contact = (Contact) contactArrayAdapter.getItem(i);
                    if (contact.getId().equals(updatedContact.getId())) {
                        contact.setName(updatedContact.getName());
                        contact.setPhone(updatedContact.getPhone());
                        break;
                    }
                }
                contactArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                Contact removedContact = snapshot.getValue(Contact.class);
                removedContact.setId(snapshot.getKey());

                Contact contact = null;
                for (int i = 0; i < contactArrayAdapter.getCount(); i++) {
                    if (((Contact)contactArrayAdapter.getItem(i)).getId().equals(removedContact.getId())) {
                        contact = (Contact)contactArrayAdapter.getItem(i);
                        break;
                    }
                }
                contactArrayAdapter.remove(contact);
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void callContactHandler(String action) {
        Intent contactHandler = new Intent(this, ContactHandler.class);
        contactHandler.putExtra("user", this.userEmail);
        contactHandler.putExtra("Action", action);
        if (action.equals(Constants.UpdateContactAction)) {
            contactHandler.putExtra("Contact", new Gson().toJson(this.selectedContact));
        }
        startActivity(contactHandler);
    }
}