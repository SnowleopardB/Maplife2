package com.example.maplife2;

import java.io.Serializable;

public class Location implements Serializable {

    private String latitude;
    private String longitude;
    private String name;
    private String description;

    @Override
    public String toString() {

        return "{" +
                "\"latitude\":" +
                "\"" + latitude + "\"," +
                "\"longitude\":" +
                "\"" + longitude + "\"," +
                "\"name\":" +
                "\"" + name + "\"," +
                "\"description\":" +
                "\"" + description + "\"" + "}";

    }

    public Location(String latitude, String longitude, String name, String description) {

        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
