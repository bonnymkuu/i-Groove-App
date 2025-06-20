package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivitySearchBinding implements ViewBinding {
    public final RecyclerView artistRecyclerView;
    public final CardView cv;
    private final RelativeLayout rootView;
    public final SearchView searchView;
    public final RecyclerView songRecyclerView;
    public final TextView text;

    private ActivitySearchBinding(RelativeLayout rootView, RecyclerView artistRecyclerView, CardView cv, SearchView searchView, RecyclerView songRecyclerView, TextView text) {
        this.rootView = rootView;
        this.artistRecyclerView = artistRecyclerView;
        this.cv = cv;
        this.searchView = searchView;
        this.songRecyclerView = songRecyclerView;
        this.text = text;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivitySearchBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivitySearchBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_search, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivitySearchBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.artistRecyclerView;
        RecyclerView artistRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
        if (artistRecyclerView != null) {
            id = R.id.cv;
            CardView cv = (CardView) ViewBindings.findChildViewById(rootView, id);
            if (cv != null) {
                id = R.id.searchView;
                SearchView searchView = (SearchView) ViewBindings.findChildViewById(rootView, id);
                if (searchView != null) {
                    id = R.id.songRecyclerView;
                    RecyclerView songRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                    if (songRecyclerView != null) {
                        id = R.id.text;
                        TextView text = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (text != null) {
                            return new ActivitySearchBinding((RelativeLayout) rootView, artistRecyclerView, cv, searchView, songRecyclerView, text);
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
