package com.dmyaniuk.db.repository;

import com.dmyaniuk.db.entities.Contact;

import java.util.List;

public interface IContactsRepository {
    List<Contact> getContacts();
    Contact getContactById(long id);
    void addContact(Contact contact);
    void updateContact(Contact contact);
    void removeContact(Contact contact);
}
