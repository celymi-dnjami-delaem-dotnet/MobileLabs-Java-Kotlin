package com.dmyaniuk.lb_9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import models.Contact;

import java.util.ArrayList;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private final ArrayList<Contact> contacts;

    public CustomRecyclerAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);

        return new ViewHolder(((TextView)view));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(contacts.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }
}
