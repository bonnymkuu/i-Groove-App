package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class OnlineAudioModel {
    private String artist;
    private String audioUrl;
    private long downloads;
    private String profile;
    private String title;

    public OnlineAudioModel() {
    }

    public OnlineAudioModel(String title, String artist, String profile, String audioUrl, long downloads) {
        this.title = title;
        this.artist = artist;
        this.profile = profile;
        this.audioUrl = audioUrl;
        this.downloads = downloads;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAudioUrl() {
        return this.audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public long getDownloads() {
        return this.downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
