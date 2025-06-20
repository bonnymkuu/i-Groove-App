package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Event {
    public String contact;
    public String date;
    public String imageUrl;
    public String location;
    public String title;

    public Event() {
    }

    public Event(String title, String location, String contact, String date, String imageUrl) {
        this.title = title;
        this.location = location;
        this.contact = contact;
        this.date = date;
        this.imageUrl = imageUrl;
    }
}
