package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ItemVenueBinding implements ViewBinding {
    public final TextView distance;
    private final RelativeLayout rootView;
    public final TextView venue;
    public final ImageView venueIv;

    private ItemVenueBinding(RelativeLayout rootView, TextView distance, TextView venue, ImageView venueIv) {
        this.rootView = rootView;
        this.distance = distance;
        this.venue = venue;
        this.venueIv = venueIv;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ItemVenueBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemVenueBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_venue, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemVenueBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.distance;
        TextView distance = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (distance != null) {
            id = R.id.venue;
            TextView venue = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (venue != null) {
                id = R.id.venueIv;
                ImageView venueIv = (ImageView) ViewBindings.findChildViewById(rootView, id);
                if (venueIv != null) {
                    return new ItemVenueBinding((RelativeLayout) rootView, distance, venue, venueIv);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
