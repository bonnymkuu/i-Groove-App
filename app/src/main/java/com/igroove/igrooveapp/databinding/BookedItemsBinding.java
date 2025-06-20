package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class BookedItemsBinding implements ViewBinding {
    public final TextView date;
    public final TextView location;
    private final CardView rootView;
    public final TextView time;

    private BookedItemsBinding(CardView rootView, TextView date, TextView location, TextView time) {
        this.rootView = rootView;
        this.date = date;
        this.location = location;
        this.time = time;
    }

    @Override // androidx.viewbinding.ViewBinding
    public CardView getRoot() {
        return this.rootView;
    }

    public static BookedItemsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static BookedItemsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.booked_items, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static BookedItemsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.date;
        TextView date = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (date != null) {
            id = R.id.location;
            TextView location = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (location != null) {
                id = R.id.time;
                TextView time = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (time != null) {
                    return new BookedItemsBinding((CardView) rootView, date, location, time);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
