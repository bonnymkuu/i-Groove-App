package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Sound {
    private String price;
    private String profileImageUrl;
    private String soundId;
    private String soundName;
    private String userId;

    public Sound() {
    }

    public Sound(String soundId, String userId, String soundName, String price, String profileImageUrl) {
        this.soundId = soundId;
        this.userId = userId;
        this.soundName = soundName;
        this.price = price;
        this.profileImageUrl = profileImageUrl;
    }

    public String getSoundId() {
        return this.soundId;
    }

    public void setSoundId(String soundId) {
        this.soundId = soundId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSoundName() {
        return this.soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
