package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class GenreItemBinding implements ViewBinding {
    public final ImageView genreImage;
    public final TextView genreTitle;
    private final CardView rootView;

    private GenreItemBinding(CardView rootView, ImageView genreImage, TextView genreTitle) {
        this.rootView = rootView;
        this.genreImage = genreImage;
        this.genreTitle = genreTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static GenreItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static GenreItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.genre_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static GenreItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.genreImage;
        ImageView genreImage = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (genreImage != null) {
            id = R.id.genreTitle;
            TextView genreTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (genreTitle != null) {
                return new GenreItemBinding((CardView) rootView, genreImage, genreTitle);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
