package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class Venue {
    private String distance;
    private String imageUrl;
    private String userId;
    private String venueName;

    public Venue() {
    }

    public Venue(String venueName, String distance, String userId, String imageUrl) {
        this.venueName = venueName;
        this.distance = distance;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public String getVenueName() {
        return this.venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getDistance() {
        return this.distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
