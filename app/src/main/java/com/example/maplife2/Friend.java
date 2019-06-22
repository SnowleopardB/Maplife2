package com.example.maplife2;


import java.io.Serializable;

public class Friend implements Serializable {

    private int id;
    private String name;

    @Override
    public String toString() {

        return "{" +
                "\"id\":" +
                "\"" + id + "\"," +
                "\"name\":" +
                "\"" + name + "\"" + "}";

    }
    public Friend(int aID, String aName) {

        this.id = aID;
        this.name = aName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
