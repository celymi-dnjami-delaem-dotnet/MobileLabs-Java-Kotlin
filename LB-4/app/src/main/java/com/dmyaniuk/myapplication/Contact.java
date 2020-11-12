package com.dmyaniuk.myapplication;

public class Contact {
    private int id;
    private String name;
    private String email;
    private String location;
    private String phone;
    private String socialNetwork;

    public Contact(int id, String name, String email, String location, String phone, String socialNetwork) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.location = location;
        this.phone = phone;
        this.socialNetwork = socialNetwork;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
