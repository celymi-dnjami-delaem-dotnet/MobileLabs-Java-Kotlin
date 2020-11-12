package com.dmyaniuk.lb_10;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.dmyaniuk.constants.Constants;
import com.dmyaniuk.db.DatabaseSingleton;
import com.dmyaniuk.db.entities.Contact;
import com.dmyaniuk.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView contactsListView;

    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseSingleton db = new DatabaseSingleton(this);

        contactsListView = findViewById(R.id.contactsListView);

        this.mainActivityViewModel = ViewModelProviders
                .of(this)
                .get(MainActivityViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initializeElements();
    }

    public void addContact(View v) {
        this.callContactHandlerPage(Constants.AddAction);
    }

    private void initializeElements() {
        LiveData<List<Contact>> liveDataContacts = this.mainActivityViewModel.getMutableContacts();
        liveDataContacts.observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(final List<Contact> contacts) {
                ArrayAdapter<Contact> contactArrayAdapter = new ArrayAdapter<>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        contacts);
                MainActivity.this.contactsListView.setAdapter(contactArrayAdapter);
                MainActivity.this.contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        MutableLiveData<Contact> mutableLiveDataContact = new MutableLiveData<>();
                        mutableLiveDataContact.setValue(contacts.get(i));
                        MainActivity.this.mainActivityViewModel.setSelectedContact(mutableLiveDataContact);
                        MainActivity.this.showPopUp(view);
                    }
                });
            }
        });
    }

    private void showPopUp(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.main_popup);
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int selectedMenuId = menuItem.getItemId();

                switch (selectedMenuId) {
                    case R.id.viewContact:
                        MainActivity.this.callContactHandlerPage(Constants.ViewAction);
                        break;
                    case R.id.removeContact:
                        MainActivity.this.mainActivityViewModel.removeContact();
                        MainActivity.this.initializeElements();
                        break;
                    case R.id.updateContact:
                        MainActivity.this.callContactHandlerPage(Constants.UpdateAction);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    private void callContactHandlerPage(String action) {
        Intent contactHandlerUpdateIntent = new Intent(MainActivity.this, ContactHandler.class);
        contactHandlerUpdateIntent.putExtra("action", action);
        startActivity(contactHandlerUpdateIntent);
    }
}