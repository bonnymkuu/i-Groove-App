package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityRentVenueBinding implements ViewBinding {
    public final EditText locationEditText;
    public final EditText nameEditText;
    public final EditText priceEditText;
    private final LinearLayout rootView;
    public final Button submitButton;

    private ActivityRentVenueBinding(LinearLayout rootView, EditText locationEditText, EditText nameEditText, EditText priceEditText, Button submitButton) {
        this.rootView = rootView;
        this.locationEditText = locationEditText;
        this.nameEditText = nameEditText;
        this.priceEditText = priceEditText;
        this.submitButton = submitButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityRentVenueBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityRentVenueBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_rent_venue, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityRentVenueBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.locationEditText;
        EditText locationEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (locationEditText != null) {
            id = R.id.nameEditText;
            EditText nameEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (nameEditText != null) {
                id = R.id.priceEditText;
                EditText priceEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (priceEditText != null) {
                    id = R.id.submitButton;
                    Button submitButton = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (submitButton != null) {
                        return new ActivityRentVenueBinding((LinearLayout) rootView, locationEditText, nameEditText, priceEditText, submitButton);
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
