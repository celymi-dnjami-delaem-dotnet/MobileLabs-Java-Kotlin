package models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Contact implements Serializable {
    private long id;
    private String name;
    private String phone;
    private int selected;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getSelected() {
        return selected;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public String toString() {
        return this.getName() + " " + this.getPhone();
    }
}
