package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class SongItemBinding implements ViewBinding {
    private final LinearLayout rootView;
    public final TextView songArtist;
    public final ImageView songThumbnail;
    public final TextView songTitle;

    private SongItemBinding(LinearLayout rootView, TextView songArtist, ImageView songThumbnail, TextView songTitle) {
        this.rootView = rootView;
        this.songArtist = songArtist;
        this.songThumbnail = songThumbnail;
        this.songTitle = songTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static SongItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static SongItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.song_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static SongItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.songArtist;
        TextView songArtist = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (songArtist != null) {
            id = R.id.songThumbnail;
            ImageView songThumbnail = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (songThumbnail != null) {
                id = R.id.songTitle;
                TextView songTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (songTitle != null) {
                    return new SongItemBinding((LinearLayout) rootView, songArtist, songThumbnail, songTitle);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
