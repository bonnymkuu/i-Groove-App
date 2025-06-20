package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Artist {
    private String id;
    private String image;
    private String name;

    public Artist() {
    }

    public Artist(String name, String image, String id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
