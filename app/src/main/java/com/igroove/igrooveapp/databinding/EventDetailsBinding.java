package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class EventDetailsBinding implements ViewBinding {
    public final Button buyTicketButton;
    public final TextView eventContact;
    public final TextView eventDate;
    public final TextView eventLocation;
    public final TextView eventTitle;
    private final LinearLayout rootView;

    private EventDetailsBinding(LinearLayout rootView, Button buyTicketButton, TextView eventContact, TextView eventDate, TextView eventLocation, TextView eventTitle) {
        this.rootView = rootView;
        this.buyTicketButton = buyTicketButton;
        this.eventContact = eventContact;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventTitle = eventTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static EventDetailsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static EventDetailsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.event_details, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static EventDetailsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.buyTicketButton;
        Button buyTicketButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (buyTicketButton != null) {
            id = R.id.eventContact;
            TextView eventContact = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (eventContact != null) {
                id = R.id.eventDate;
                TextView eventDate = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (eventDate != null) {
                    id = R.id.eventLocation;
                    TextView eventLocation = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (eventLocation != null) {
                        id = R.id.eventTitle;
                        TextView eventTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (eventTitle != null) {
                            return new EventDetailsBinding((LinearLayout) rootView, buyTicketButton, eventContact, eventDate, eventLocation, eventTitle);
                        }
                    }
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
