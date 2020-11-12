package com.dmyaniuk.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.dmyaniuk.db.dao.ContactsDao;
import com.dmyaniuk.db.entities.Contact;

@Database(entities = { Contact.class }, version = 2)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactsDao contactsDao();
}
