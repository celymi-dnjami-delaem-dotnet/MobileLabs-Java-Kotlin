package com.dmyaniuk.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.dmyaniuk.db.entities.Contact;
import com.dmyaniuk.db.repository.ContactsRepository;
import com.dmyaniuk.db.repository.IContactsRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private static MutableLiveData<Contact> selectedContact;
    private static MutableLiveData<List<Contact>> mutableContacts;
    private static IContactsRepository contactsRepository;

    public MutableLiveData<List<Contact>> getMutableContacts() {
            mutableContacts = new MutableLiveData<>();
                contactsRepository = new ContactsRepository();
                mutableContacts.setValue(contactsRepository.getContacts());

        return mutableContacts;
    }

    public void setSelectedContact(MutableLiveData<Contact> selectedContact) {
        MainActivityViewModel.selectedContact = selectedContact;
    }

    public void addContact(Contact contact) {
        contactsRepository.addContact(contact);
    }

    public void updateContact(Contact contact) {
        contactsRepository.updateContact(contact);
    }

    public void removeContact() {
        contactsRepository.removeContact(selectedContact.getValue());
    }

    public MutableLiveData<Contact> getSelectedContact() {
        return selectedContact;
    }
}
