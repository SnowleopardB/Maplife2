package com.example.maplife2;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private int id;
    private String password;
    private String name;
    private String email;
    private ArrayList<Location> locations;
    private ArrayList<Friend> friends;


    public User(int aID, String aEmail, String aName, String aPassword, ArrayList<Location> aLocations, ArrayList<Friend> aFriends) {

        this.id = aID;
        this.password = aPassword;
        this.name = aName;
        this.email = aEmail;
        this.locations = aLocations;
        this.friends = aFriends;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }
}


