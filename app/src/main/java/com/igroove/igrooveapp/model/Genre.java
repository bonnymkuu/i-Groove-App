package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Genre {
    private int imageUrl;
    private String title;

    public Genre(String title, int imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public int getImageUrl() {
        return this.imageUrl;
    }
}
