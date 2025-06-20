package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class OnlineAudioItemBinding implements ViewBinding {
    public final TextView audioArtist;
    public final TextView audioTitle;
    public final ImageButton playButton;
    private final CardView rootView;

    private OnlineAudioItemBinding(CardView rootView, TextView audioArtist, TextView audioTitle, ImageButton playButton) {
        this.rootView = rootView;
        this.audioArtist = audioArtist;
        this.audioTitle = audioTitle;
        this.playButton = playButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static OnlineAudioItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static OnlineAudioItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.online_audio_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static OnlineAudioItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.audioArtist;
        TextView audioArtist = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (audioArtist != null) {
            id = R.id.audioTitle;
            TextView audioTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (audioTitle != null) {
                id = R.id.playButton;
                ImageButton playButton = (ImageButton) ViewBindings.findChildViewById(rootView, id);
                if (playButton != null) {
                    return new OnlineAudioItemBinding((CardView) rootView, audioArtist, audioTitle, playButton);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
