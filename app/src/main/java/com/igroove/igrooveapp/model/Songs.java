package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Songs {
    private String artistName;
    private String audioUrl;
    private String price;
    private String profileUrl;
    private String title;
    private String userId;

    public Songs() {
    }

    public Songs(String title, String artistName, String price, String profileUrl, String audioUrl, String userId) {
        this.title = title;
        this.price = price;
        this.artistName = artistName;
        this.profileUrl = profileUrl;
        this.audioUrl = audioUrl;
        this.userId = userId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artist) {
        this.artistName = artist;
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }

    public void setProfileUrl(String profile) {
        this.profileUrl = profile;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
