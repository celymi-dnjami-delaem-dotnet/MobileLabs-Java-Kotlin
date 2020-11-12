package com.dmyaniuk.db;

import androidx.room.Room;
import com.dmyaniuk.lb_10.MainActivity;

public class DatabaseSingleton {
    private static final String DatabaseName = "ContactsDatabase";
    private static ContactDatabase contactDatabase;

    public DatabaseSingleton(MainActivity mainActivity) {
        contactDatabase = Room.databaseBuilder(mainActivity, ContactDatabase.class, DatabaseName)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static ContactDatabase getContactDatabase() {
        return contactDatabase;
    }
}
