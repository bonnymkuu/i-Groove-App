package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Bookings {
    private String date;
    private String location;
    private String time;

    public Bookings() {
    }

    public Bookings(String location, String date, String time) {
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
