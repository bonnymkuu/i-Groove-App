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
public final class ArtistItemBinding implements ViewBinding {
    public final ImageView artistImage;
    public final TextView artistName;
    private final LinearLayout rootView;

    private ArtistItemBinding(LinearLayout rootView, ImageView artistImage, TextView artistName) {
        this.rootView = rootView;
        this.artistImage = artistImage;
        this.artistName = artistName;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ArtistItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ArtistItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.artist_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ArtistItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.artistImage;
        ImageView artistImage = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (artistImage != null) {
            id = R.id.artistName;
            TextView artistName = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (artistName != null) {
                return new ArtistItemBinding((LinearLayout) rootView, artistImage, artistName);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
