package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityAllArtistsBinding implements ViewBinding {
    public final RecyclerView artistsRecyclerView;
    public final CardView cv;
    private final LinearLayout rootView;
    public final SearchView searchView;

    private ActivityAllArtistsBinding(LinearLayout rootView, RecyclerView artistsRecyclerView, CardView cv, SearchView searchView) {
        this.rootView = rootView;
        this.artistsRecyclerView = artistsRecyclerView;
        this.cv = cv;
        this.searchView = searchView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityAllArtistsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityAllArtistsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_all_artists, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityAllArtistsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.artistsRecyclerView;
        RecyclerView artistsRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (artistsRecyclerView != null) {
            id = R.id.cv;
            CardView cv = (CardView) ViewBindings.findChildViewById(rootView, id);
            if (cv != null) {
                id = R.id.searchView;
                SearchView searchView = (SearchView) ViewBindings.findChildViewById(rootView, id);
                if (searchView != null) {
                    return new ActivityAllArtistsBinding((LinearLayout) rootView, artistsRecyclerView, cv, searchView);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
