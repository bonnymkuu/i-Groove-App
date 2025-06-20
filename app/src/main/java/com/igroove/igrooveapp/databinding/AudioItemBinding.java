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
public final class AudioItemBinding implements ViewBinding {
    public final TextView audioTitle;
    public final ImageView profileImageView;
    private final LinearLayout rootView;

    private AudioItemBinding(LinearLayout rootView, TextView audioTitle, ImageView profileImageView) {
        this.rootView = rootView;
        this.audioTitle = audioTitle;
        this.profileImageView = profileImageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static AudioItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static AudioItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.audio_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static AudioItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.audioTitle;
        TextView audioTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (audioTitle != null) {
            id = R.id.profileImageView;
            ImageView profileImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (profileImageView != null) {
                return new AudioItemBinding((LinearLayout) rootView, audioTitle, profileImageView);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
