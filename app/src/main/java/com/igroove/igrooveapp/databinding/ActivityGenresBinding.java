package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityGenresBinding implements ViewBinding {
    public final ImageView addActivity;
    public final ImageView genresImageView;
    public final RecyclerView genresRecyclerView;
    public final ImageView othersActivity;
    private final LinearLayout rootView;
    public final ImageView searchIcon;
    public final ImageView soundActivity;
    public final ImageView userPic;
    public final ImageView venueActivity;

    private ActivityGenresBinding(LinearLayout rootView, ImageView addActivity, ImageView genresImageView, RecyclerView genresRecyclerView, ImageView othersActivity, ImageView searchIcon, ImageView soundActivity, ImageView userPic, ImageView venueActivity) {
        this.rootView = rootView;
        this.addActivity = addActivity;
        this.genresImageView = genresImageView;
        this.genresRecyclerView = genresRecyclerView;
        this.othersActivity = othersActivity;
        this.searchIcon = searchIcon;
        this.soundActivity = soundActivity;
        this.userPic = userPic;
        this.venueActivity = venueActivity;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityGenresBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityGenresBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_genres, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityGenresBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.addActivity;
        ImageView addActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (addActivity != null) {
            id = R.id.genresImageView;
            ImageView genresImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (genresImageView != null) {
                id = R.id.genresRecyclerView;
                RecyclerView genresRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                if (genresRecyclerView != null) {
                    id = R.id.othersActivity;
                    ImageView othersActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                    if (othersActivity != null) {
                        id = R.id.searchIcon;
                        ImageView searchIcon = (ImageView) ViewBindings.findChildViewById(rootView, id);
                        if (searchIcon != null) {
                            id = R.id.soundActivity;
                            ImageView soundActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                            if (soundActivity != null) {
                                id = R.id.userPic;
                                ImageView userPic = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                if (userPic != null) {
                                    id = R.id.venueActivity;
                                    ImageView venueActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                    if (venueActivity != null) {
                                        return new ActivityGenresBinding((LinearLayout) rootView, addActivity, genresImageView, genresRecyclerView, othersActivity, searchIcon, soundActivity, userPic, venueActivity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
