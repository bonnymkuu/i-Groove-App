package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class RecommendedItems {
    private String imageUrl;
    private String name;

    public RecommendedItems() {
    }

    public RecommendedItems(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
