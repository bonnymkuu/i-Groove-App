package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityGenreListBinding implements ViewBinding {
    public final ImageButton backButton;
    public final RecyclerView genreAudioRecyclerView;
    public final TextView genreNotFound;
    public final TextView genreTitle;
    private final RelativeLayout rootView;

    private ActivityGenreListBinding(RelativeLayout rootView, ImageButton backButton, RecyclerView genreAudioRecyclerView, TextView genreNotFound, TextView genreTitle) {
        this.rootView = rootView;
        this.backButton = backButton;
        this.genreAudioRecyclerView = genreAudioRecyclerView;
        this.genreNotFound = genreNotFound;
        this.genreTitle = genreTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityGenreListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityGenreListBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_genre_list, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityGenreListBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.backButton;
        ImageButton backButton = (ImageButton) ViewBindings.findChildViewById(rootView, id);
        if (backButton != null) {
            id = R.id.genreAudioRecyclerView;
            RecyclerView genreAudioRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
            if (genreAudioRecyclerView != null) {
                id = R.id.genreNotFound;
                TextView genreNotFound = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (genreNotFound != null) {
                    id = R.id.genreTitle;
                    TextView genreTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (genreTitle != null) {
                        return new ActivityGenreListBinding((RelativeLayout) rootView, backButton, genreAudioRecyclerView, genreNotFound, genreTitle);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
