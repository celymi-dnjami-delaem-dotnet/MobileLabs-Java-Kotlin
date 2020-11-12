package com.dmyaniuk.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> selectedContacts;
    private FileHandler fileHandler;
    private ListView listView;
    private DialogHandler dialogHandler;
    private Contact selectedContactInPopUp;

    private final int ITEM_VIEW = 1;
    private final int ITEM_DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeElements();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.renderContacts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.newItem:
                this.addContact();
                break;
            case R.id.updateItem:
                this.editContact();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, ITEM_VIEW, Menu.NONE, "View");
        menu.add(Menu.NONE, ITEM_DELETE, Menu.NONE, "Delete");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case ITEM_VIEW:
                this.viewContact();
                break;
            case ITEM_DELETE:
                this.removeContacts();
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.copyBufPop:
                this.copyToClipboard();
                break;
            case R.id.deletePop:
                this.selectedContacts.add(this.selectedContactInPopUp);
                this.removeContacts();
                break;
            default:
                break;
        }

        return false;
    }

    private void initializeElements() {
        this.fileHandler = new FileHandler(this);
        this.listView = findViewById(R.id.contactsList);
        this.selectedContacts = new ArrayList<>();
        this.dialogHandler = new DialogHandler(this, this.fileHandler);
        registerForContextMenu(this.listView);
    }

    public void renderContacts() {
        this.contacts = this.fileHandler.readFromStorage();

        final ArrayList<String> contactNames = new ArrayList<>();
        for (Contact contact:this.contacts) {
            contactNames.add(contact.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, contactNames);
        this.listView.setAdapter(arrayAdapter);

        if (this.selectedContacts.size() != 0) {
            for (int i = 0; i < this.contacts.size(); i++) {
                Contact selectedContact = this.getSelectedContact(this.contacts.get(i));
                if (selectedContact != null) {
                    this.listView.setItemChecked(i, true);
                }
            }
        }

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu pop = new PopupMenu(MainActivity.this, view);
                pop.setOnMenuItemClickListener(MainActivity.this);
                pop.inflate(R.menu.pop_menu);
                pop.show();
                selectedContactInPopUp = contacts.get(i);
                listView.setItemChecked(i, false);
            }
        });

        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact selectedContact = contacts.get(i);
                Contact alreadySelectedContact = getSelectedContact(selectedContact);

                if (alreadySelectedContact != null) {
                    selectedContacts.remove(alreadySelectedContact);
                    listView.setItemChecked(i, false);
                } else {
                    selectedContacts.add(selectedContact);
                    listView.setItemChecked(i, true);
                }

                return false;
            }
        });
    }

    private void addContact() {
        Intent contactCreation = new Intent(this, ContactCreation.class);
        startActivity(contactCreation);
    }

    private void removeContacts() {
        this.contacts.removeAll(this.selectedContacts);
        this.dialogHandler.removeContacts(
                "Delete",
                "Are you sure you want to remove?",
                this.contacts
        ).show();
        this.selectedContacts.clear();
    }

    private void editContact() {
        if (this.selectedContacts.size() != 1) {
            Toast.makeText(this, "Only one item can be edited!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent editContactIntent = new Intent(this, ContactUpdate.class);
        editContactIntent.putExtra("contact", new Gson().toJson(this.selectedContacts.get(0)));
        startActivity(editContactIntent);
    }

    private void copyToClipboard() {
        ClipboardManager manager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("contact", new Gson().toJson(this.selectedContactInPopUp));
        manager.setPrimaryClip(data);

        Toast.makeText(this, "Contact copied", Toast.LENGTH_LONG).show();
    }

    private void viewContact() {
        if (this.selectedContacts.size() != 1) {
            Toast.makeText(this, "Only one contact can be viewed", Toast.LENGTH_LONG).show();
            return;
        }

        Intent viewContactIntent = new Intent(MainActivity.this, ContactInfo.class);
        viewContactIntent.putExtra("contact", new Gson().toJson(this.selectedContacts.get(0)));
        startActivity(viewContactIntent);
    }

    private Contact getSelectedContact(Contact selectedContact) {
        for (Contact contact:this.selectedContacts) {
            if (contact.getId() == selectedContact.getId()) {
                return contact;
            }
        }

        return null;
    }
}