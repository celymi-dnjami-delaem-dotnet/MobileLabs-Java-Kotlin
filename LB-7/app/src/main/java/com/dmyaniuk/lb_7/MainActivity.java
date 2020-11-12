package com.dmyaniuk.lb_7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import db.DatabaseHelper;
import models.Constants;
import models.Contact;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private class DatabaseHandler extends AsyncTask<String, Void, SimpleCursorAdapter> {
        private DatabaseHelper databaseHelper;
        private SQLiteDatabase sqLiteDatabase;
        private Cursor cursor;

        @Override
        protected void onPreExecute() {
            this.databaseHelper = new DatabaseHelper(MainActivity.this);
            this.sqLiteDatabase = this.databaseHelper.getWritableDatabase();
        }

        @Override
        protected SimpleCursorAdapter doInBackground(String... commands) {
            switch (commands[0]) {
                case Constants.SetSelectedAction:
                    this.setContactSelected();
                    break;
                case Constants.DeleteAction:
                    this.removeContact();
                    break;
            }

            this.cursor = this.sqLiteDatabase.query(
                    DatabaseHelper.TableName,
                    null,
                    commands[0].equals(Constants.RenderSelectedContactsAction)
                            ? DatabaseHelper.KEY_SELECTED + "=" + 1
                            : null,
                    null,
                    null,
                    null,
                    commands.length > 1 ? this.sortByType(commands[1]) : DatabaseHelper.KEY_NAME
            );

            return new SimpleCursorAdapter(
                    MainActivity.this,
                    android.R.layout.simple_list_item_multiple_choice,
                    this.cursor,
                    new String[] { DatabaseHelper.KEY_NAME },
                    new int[] { android.R.id.text1 },
                    0
            );
        }

        @Override
        protected void onPostExecute(SimpleCursorAdapter simpleCursorAdapter) {
            MainActivity.this.contactsListView.setAdapter(simpleCursorAdapter);

            int i = 0;
            while (this.cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(DatabaseHelper.KEY_SELECTED)) == 1) {
                    MainActivity.this.contactsListView.setItemChecked(i, true);
                }

                i++;
            }
        }

        private void removeContact() {
            this.sqLiteDatabase.delete(
                    DatabaseHelper.TableName,
                    DatabaseHelper.KEY_ID + "=" + MainActivity.this.contact.getId(),
                    null
            );
        }

        private void setContactSelected() {
            this.cursor = this.sqLiteDatabase.query(
                    DatabaseHelper.TableName,
                    new String[] { DatabaseHelper.KEY_SELECTED },
                    DatabaseHelper.KEY_ID + "=" + MainActivity.this.contact.getId(),
                    null,
                    null,
                    null,
                    null
                    );

            int selectedType = 0;
            if (this.cursor.getCount() > 0) {
                this.cursor.moveToNext();
                selectedType = this.cursor.getInt(this.cursor.getColumnIndex(DatabaseHelper.KEY_SELECTED));
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.KEY_SELECTED, selectedType == 0 ? 1 : 0);

            this.sqLiteDatabase.update(
                    DatabaseHelper.TableName,
                    contentValues,
                    DatabaseHelper.KEY_ID + "=" + MainActivity.this.contact.getId(),
                    null
            );
        }

        private String sortByType(String type) {
            switch (type) {
                case Constants.SortNameType:
                    return DatabaseHelper.KEY_NAME;
                case Constants.SortPhoneType:
                    return DatabaseHelper.KEY_PHONE;
                default:
                    return null;
            }
        }
    }

    private ListView contactsListView;
    private Switch selectedSwitch;
    private Spinner contactsSpinner;
    private Contact contact;

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
    public boolean onMenuItemClick(MenuItem menuItem) {
       int id = menuItem.getItemId();

       switch (id) {
           case R.id.updateItem:
               this.callContactsHandler(Constants.UpdateAction);
               break;
           case R.id.deleteItem:
               DatabaseHandler databaseHandler = new DatabaseHandler();
               databaseHandler.execute(Constants.DeleteAction);
               break;
           default:
               break;
       }

       return true;
    }

    public void addContact(View v) {
        this.callContactsHandler(Constants.AddAction);
    }

    private void initializeElements() {
        this.contactsListView = findViewById(R.id.contactsListView);
        this.selectedSwitch = findViewById(R.id.checkedSwitch);
        this.contactsSpinner = findViewById(R.id.contactsSpinner);

        this.contact = new Contact();
    }

    private void renderContacts() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        databaseHandler.execute(Constants.RenderContactsAction);

        this.contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor selectedCursor = (Cursor)MainActivity.this.contactsListView.getItemAtPosition(i);
                MainActivity.this.setSelectedContact(selectedCursor);

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(MainActivity.this);
                popupMenu.show();
            }
        });

        this.contactsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor selectedCursor = (Cursor)MainActivity.this.contactsListView.getItemAtPosition(i);
                MainActivity.this.setSelectedContact(selectedCursor);

                DatabaseHandler databaseHandler = new DatabaseHandler();
                databaseHandler.execute(Constants.SetSelectedAction);

                return true;
            }
        });

        this.selectedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                if (b) {
                    databaseHandler.execute(Constants.RenderSelectedContactsAction);
                } else {
                    databaseHandler.execute(Constants.RenderContactsAction);
                }
            }
        });

        this.contactsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                String sortType = (String)MainActivity.this.contactsSpinner.getItemAtPosition(i);

                switch (sortType) {
                    case Constants.SortNameType:
                        databaseHandler.execute(Constants.SortAction, Constants.SortNameType);
                        break;
                    case Constants.SortPhoneType:
                        databaseHandler.execute(Constants.SortAction, Constants.SortPhoneType);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSelectedContact(Cursor selectedCursor) {
        this.contact.setId(
                selectedCursor.getLong(selectedCursor.getColumnIndex(DatabaseHelper.KEY_ID))
        );
        this.contact.setName(
                selectedCursor.getString(selectedCursor.getColumnIndex(DatabaseHelper.KEY_NAME))
        );
        this.contact.setPhone(
                selectedCursor.getString(selectedCursor.getColumnIndex(DatabaseHelper.KEY_PHONE))
        );
        this.contact.setSelected(
                selectedCursor.getInt(selectedCursor.getColumnIndex(DatabaseHelper.KEY_SELECTED))
        );
    }

    private void callContactsHandler(String action) {
        Intent contactsIntent = new Intent(this, ContactsHandler.class);
        contactsIntent.putExtra("action", action);

        if (action.equals(Constants.UpdateAction)) {
            contactsIntent.putExtra("contact", this.contact);
        }

        startActivity(contactsIntent);
    }
}