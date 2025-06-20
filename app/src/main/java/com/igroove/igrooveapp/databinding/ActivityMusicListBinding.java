package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityMusicListBinding implements ViewBinding {
    public final TextView localLabel;
    public final RecyclerView localMusicRecyclerView;
    public final TextView onlineLabel;
    public final RecyclerView onlineMusicRecyclerView;
    private final NestedScrollView rootView;

    private ActivityMusicListBinding(NestedScrollView rootView, TextView localLabel, RecyclerView localMusicRecyclerView, TextView onlineLabel, RecyclerView onlineMusicRecyclerView) {
        this.rootView = rootView;
        this.localLabel = localLabel;
        this.localMusicRecyclerView = localMusicRecyclerView;
        this.onlineLabel = onlineLabel;
        this.onlineMusicRecyclerView = onlineMusicRecyclerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityMusicListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityMusicListBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_music_list, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityMusicListBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.localLabel;
        TextView localLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (localLabel != null) {
            id = R.id.localMusicRecyclerView;
            RecyclerView localMusicRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
            if (localMusicRecyclerView != null) {
                id = R.id.onlineLabel;
                TextView onlineLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (onlineLabel != null) {
                    id = R.id.onlineMusicRecyclerView;
                    RecyclerView onlineMusicRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                    if (onlineMusicRecyclerView != null) {
                        return new ActivityMusicListBinding((NestedScrollView) rootView, localLabel, localMusicRecyclerView, onlineLabel, onlineMusicRecyclerView);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
