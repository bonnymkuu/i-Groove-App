package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class DialogEventBinding implements ViewBinding {
    public final EditText eventContactEditText;
    public final Button eventDateButton;
    public final TextView eventDateTextView;
    public final ImageView eventImageView;
    public final EditText eventLocationEditText;
    public final EditText eventTitleEditText;
    private final LinearLayout rootView;
    public final Button uploadEventButton;

    private DialogEventBinding(LinearLayout rootView, EditText eventContactEditText, Button eventDateButton, TextView eventDateTextView, ImageView eventImageView, EditText eventLocationEditText, EditText eventTitleEditText, Button uploadEventButton) {
        this.rootView = rootView;
        this.eventContactEditText = eventContactEditText;
        this.eventDateButton = eventDateButton;
        this.eventDateTextView = eventDateTextView;
        this.eventImageView = eventImageView;
        this.eventLocationEditText = eventLocationEditText;
        this.eventTitleEditText = eventTitleEditText;
        this.uploadEventButton = uploadEventButton;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogEventBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogEventBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.dialog_event, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static DialogEventBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.eventContactEditText;
        EditText eventContactEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (eventContactEditText != null) {
            id = R.id.eventDateButton;
            Button eventDateButton = (Button) ViewBindings.findChildViewById(rootView, id);
            if (eventDateButton != null) {
                id = R.id.eventDateTextView;
                TextView eventDateTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (eventDateTextView != null) {
                    id = R.id.eventImageView;
                    ImageView eventImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
                    if (eventImageView != null) {
                        id = R.id.eventLocationEditText;
                        EditText eventLocationEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                        if (eventLocationEditText != null) {
                            id = R.id.eventTitleEditText;
                            EditText eventTitleEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                            if (eventTitleEditText != null) {
                                id = R.id.uploadEventButton;
                                Button uploadEventButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                if (uploadEventButton != null) {
                                    return new DialogEventBinding((LinearLayout) rootView, eventContactEditText, eventDateButton, eventDateTextView, eventImageView, eventLocationEditText, eventTitleEditText, uploadEventButton);
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
