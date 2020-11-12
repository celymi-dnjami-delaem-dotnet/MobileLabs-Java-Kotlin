package com.dmyaniuk.db.repository;

import com.dmyaniuk.db.DatabaseSingleton;
import com.dmyaniuk.db.dao.ContactsDao;
import com.dmyaniuk.db.entities.Contact;

import java.util.List;

public class ContactsRepository implements IContactsRepository {
    private ContactsDao contactsDao;

    public ContactsRepository() {
        contactsDao = DatabaseSingleton.getContactDatabase().contactsDao();
    }

    @Override
    public List<Contact> getContacts() {
        return this.contactsDao.getAllContacts();
    }

    @Override
    public Contact getContactById(long id) {
        return this.contactsDao.getContactById(id);
    }

    @Override
    public void addContact(Contact contact) {
        this.contactsDao.addContact(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        this.contactsDao.updateContact(contact);
    }

    @Override
    public void removeContact(Contact contact) {
        this.contactsDao.deleteContact(contact);
    }
}
