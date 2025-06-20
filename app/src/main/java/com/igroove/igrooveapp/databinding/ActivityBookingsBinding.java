package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityBookingsBinding implements ViewBinding {
    public final EditText addressInput;
    public final ImageView artistImage;
    public final TextView artistName;
    public final TextView bookingsDeposit;
    public final TextView bookingsDistance;
    public final TextView bookingsTime;
    public final TextView bookingsTransport;
    public final Button dateButton;
    public final ImageView dateImageView1;
    public final ImageView dateImageView2;
    public final TextView depositAmount;
    public final EditText durationInput;
    public final ProgressBar progressBar;
    private final NestedScrollView rootView;
    public final RecyclerView soundRecyclerview;
    public final Button submitButton;
    public final Button timeButton;
    public final ImageView timeImageView1;
    public final ImageView timeImageView2;
    public final TextView titleText;
    public final TextView titleText0;
    public final TextView titleText2;
    public final TextView titleText3;
    public final TextView totalAmount;
    public final TextView transportAmount;
    public final RecyclerView venueRecyclerview;

    private ActivityBookingsBinding(NestedScrollView rootView, EditText addressInput, ImageView artistImage, TextView artistName, TextView bookingsDeposit, TextView bookingsDistance, TextView bookingsTime, TextView bookingsTransport, Button dateButton, ImageView dateImageView1, ImageView dateImageView2, TextView depositAmount, EditText durationInput, ProgressBar progressBar, RecyclerView soundRecyclerview, Button submitButton, Button timeButton, ImageView timeImageView1, ImageView timeImageView2, TextView titleText, TextView titleText0, TextView titleText2, TextView titleText3, TextView totalAmount, TextView transportAmount, RecyclerView venueRecyclerview) {
        this.rootView = rootView;
        this.addressInput = addressInput;
        this.artistImage = artistImage;
        this.artistName = artistName;
        this.bookingsDeposit = bookingsDeposit;
        this.bookingsDistance = bookingsDistance;
        this.bookingsTime = bookingsTime;
        this.bookingsTransport = bookingsTransport;
        this.dateButton = dateButton;
        this.dateImageView1 = dateImageView1;
        this.dateImageView2 = dateImageView2;
        this.depositAmount = depositAmount;
        this.durationInput = durationInput;
        this.progressBar = progressBar;
        this.soundRecyclerview = soundRecyclerview;
        this.submitButton = submitButton;
        this.timeButton = timeButton;
        this.timeImageView1 = timeImageView1;
        this.timeImageView2 = timeImageView2;
        this.titleText = titleText;
        this.titleText0 = titleText0;
        this.titleText2 = titleText2;
        this.titleText3 = titleText3;
        this.totalAmount = totalAmount;
        this.transportAmount = transportAmount;
        this.venueRecyclerview = venueRecyclerview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityBookingsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityBookingsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_bookings, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityBookingsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.addressInput;
        EditText addressInput = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (addressInput != null) {
            id = R.id.artistImage;
            ImageView artistImage = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (artistImage != null) {
                id = R.id.artistName;
                TextView artistName = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (artistName != null) {
                    id = R.id.bookingsDeposit;
                    TextView bookingsDeposit = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (bookingsDeposit != null) {
                        id = R.id.bookingsDistance;
                        TextView bookingsDistance = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (bookingsDistance != null) {
                            id = R.id.bookingsTime;
                            TextView bookingsTime = (TextView) ViewBindings.findChildViewById(rootView, id);
                            if (bookingsTime != null) {
                                id = R.id.bookingsTransport;
                                TextView bookingsTransport = (TextView) ViewBindings.findChildViewById(rootView, id);
                                if (bookingsTransport != null) {
                                    id = R.id.dateButton;
                                    Button dateButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                    if (dateButton != null) {
                                        id = R.id.dateImageView1;
                                        ImageView dateImageView1 = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                        if (dateImageView1 != null) {
                                            id = R.id.dateImageView2;
                                            ImageView dateImageView2 = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                            if (dateImageView2 != null) {
                                                id = R.id.depositAmount;
                                                TextView depositAmount = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                if (depositAmount != null) {
                                                    id = R.id.durationInput;
                                                    EditText durationInput = (EditText) ViewBindings.findChildViewById(rootView, id);
                                                    if (durationInput != null) {
                                                        id = R.id.progressBar;
                                                        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                                        if (progressBar != null) {
                                                            id = R.id.soundRecyclerview;
                                                            RecyclerView soundRecyclerview = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                            if (soundRecyclerview != null) {
                                                                id = R.id.submitButton;
                                                                Button submitButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                                                if (submitButton != null) {
                                                                    id = R.id.timeButton;
                                                                    Button timeButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                                                    if (timeButton != null) {
                                                                        id = R.id.timeImageView1;
                                                                        ImageView timeImageView1 = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                                        if (timeImageView1 != null) {
                                                                            id = R.id.timeImageView2;
                                                                            ImageView timeImageView2 = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                                            if (timeImageView2 != null) {
                                                                                id = R.id.titleText;
                                                                                TextView titleText = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                if (titleText != null) {
                                                                                    id = R.id.titleText0;
                                                                                    TextView titleText0 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                    if (titleText0 != null) {
                                                                                        id = R.id.titleText2;
                                                                                        TextView titleText2 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                        if (titleText2 != null) {
                                                                                            id = R.id.titleText3;
                                                                                            TextView titleText3 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                            if (titleText3 != null) {
                                                                                                id = R.id.totalAmount;
                                                                                                TextView totalAmount = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                                if (totalAmount != null) {
                                                                                                    id = R.id.transportAmount;
                                                                                                    TextView transportAmount = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                                    if (transportAmount != null) {
                                                                                                        id = R.id.venueRecyclerview;
                                                                                                        RecyclerView venueRecyclerview = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                                                                        if (venueRecyclerview != null) {
                                                                                                            return new ActivityBookingsBinding((NestedScrollView) rootView, addressInput, artistImage, artistName, bookingsDeposit, bookingsDistance, bookingsTime, bookingsTransport, dateButton, dateImageView1, dateImageView2, depositAmount, durationInput, progressBar, soundRecyclerview, submitButton, timeButton, timeImageView1, timeImageView2, titleText, titleText0, titleText2, titleText3, totalAmount, transportAmount, venueRecyclerview);
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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
