package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class DialogVenueDetailsBinding implements ViewBinding {
    public final EditText inputDate;
    public final EditText inputLocation;
    public final EditText inputTime;
    private final LinearLayout rootView;

    private DialogVenueDetailsBinding(LinearLayout rootView, EditText inputDate, EditText inputLocation, EditText inputTime) {
        this.rootView = rootView;
        this.inputDate = inputDate;
        this.inputLocation = inputLocation;
        this.inputTime = inputTime;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogVenueDetailsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogVenueDetailsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.dialog_venue_details, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static DialogVenueDetailsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.inputDate;
        EditText inputDate = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (inputDate != null) {
            id = R.id.inputLocation;
            EditText inputLocation = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (inputLocation != null) {
                id = R.id.inputTime;
                EditText inputTime = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (inputTime != null) {
                    return new DialogVenueDetailsBinding((LinearLayout) rootView, inputDate, inputLocation, inputTime);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
