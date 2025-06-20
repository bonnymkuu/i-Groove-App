package com.igroove.igrooveapp.model;

/* loaded from: classes4.dex */
public class OfflineAudioModel {
    private String path;
    private String title;

    public OfflineAudioModel(String title, String path) {
        this.title = title;
        this.path = path;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
