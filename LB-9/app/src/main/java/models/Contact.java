package models;

import androidx.annotation.NonNull;

public class Contact {
    private int id;
    private String name;
    private String phone;
    private String location;
    private String vkLink;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setVkLink(String vkLink) {
        this.vkLink = vkLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public String getVkLink() {
        return vkLink;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name + " " + this.phone + " " + this.location + " " + this.vkLink;
    }
}
