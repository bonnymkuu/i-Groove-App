package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class EventDetailsEditableBinding implements ViewBinding {
    public final EditText eventContactEdit;
    public final Button eventDateButton;
    public final TextView eventDateTextView;
    public final EditText eventLocationEdit;
    public final EditText eventTitleEdit;
    public final ProgressBar progressBar;
    private final LinearLayout rootView;
    public final Button updateButton;

    private EventDetailsEditableBinding(LinearLayout rootView, EditText eventContactEdit, Button eventDateButton, TextView eventDateTextView, EditText eventLocationEdit, EditText eventTitleEdit, ProgressBar progressBar, Button updateButton) {
        this.rootView = rootView;
        this.eventContactEdit = eventContactEdit;
        this.eventDateButton = eventDateButton;
        this.eventDateTextView = eventDateTextView;
        this.eventLocationEdit = eventLocationEdit;
        this.eventTitleEdit = eventTitleEdit;
        this.progressBar = progressBar;
        this.updateButton = updateButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static EventDetailsEditableBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static EventDetailsEditableBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.event_details_editable, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static EventDetailsEditableBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.eventContactEdit;
        EditText eventContactEdit = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (eventContactEdit != null) {
            id = R.id.eventDateButton;
            Button eventDateButton = (Button) ViewBindings.findChildViewById(rootView, id);
            if (eventDateButton != null) {
                id = R.id.eventDateTextView;
                TextView eventDateTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (eventDateTextView != null) {
                    id = R.id.eventLocationEdit;
                    EditText eventLocationEdit = (EditText) ViewBindings.findChildViewById(rootView, id);
                    if (eventLocationEdit != null) {
                        id = R.id.eventTitleEdit;
                        EditText eventTitleEdit = (EditText) ViewBindings.findChildViewById(rootView, id);
                        if (eventTitleEdit != null) {
                            id = R.id.progressBar;
                            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                            if (progressBar != null) {
                                id = R.id.updateButton;
                                Button updateButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                if (updateButton != null) {
                                    return new EventDetailsEditableBinding((LinearLayout) rootView, eventContactEdit, eventDateButton, eventDateTextView, eventLocationEdit, eventTitleEdit, progressBar, updateButton);
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
