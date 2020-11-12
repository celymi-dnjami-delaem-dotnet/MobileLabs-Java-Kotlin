package com.dmyaniuk.models;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

public class Contact {
    private String id;
    private String name;
    private String phone;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return this.getName() + " " + this.getPhone();
    }
}
