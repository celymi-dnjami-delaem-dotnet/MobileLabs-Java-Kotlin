package com.dmyaniuk.db.dao;

import androidx.room.*;
import com.dmyaniuk.db.entities.Contact;

import java.util.List;

@Dao
public interface ContactsDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAllContacts();

    @Query("SELECT * FROM Contact WHERE id = :id")
    Contact getContactById(long id);

    @Insert
    void addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);
}
