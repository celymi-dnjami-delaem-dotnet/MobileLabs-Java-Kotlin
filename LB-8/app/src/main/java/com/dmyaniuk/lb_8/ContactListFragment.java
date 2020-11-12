package com.dmyaniuk.lb_8;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dmyaniuk.models.Contact;

import java.util.ArrayList;

public class ContactListFragment extends Fragment {
    public interface OnItemSelected {
        void getSelectedContact(Contact contact);
    }

    private ListView contactsListView;
    private OnItemSelected onItemSelectedCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        this.registerListEvents(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelected) {
            onItemSelectedCallback = (OnItemSelected)context;
        }
    }

    private void registerListEvents(View view) {
        this.contactsListView = view.findViewById(R.id.contactsListView);

        if (getArguments() != null) {
            ArrayList<Contact> contacts = (ArrayList<Contact>) getArguments().getSerializable("contactList");

            ArrayAdapter<Contact> contactArrayAdapter = new ArrayAdapter<>(
                    view.getContext(),
                    android.R.layout.simple_list_item_1,
                    contacts
            );
            this.contactsListView.setAdapter(contactArrayAdapter);

            this.contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Contact contact = (Contact) ContactListFragment.this.contactsListView.getItemAtPosition(i);
                    onItemSelectedCallback.getSelectedContact(contact);
                }
            });
        }
    }
}