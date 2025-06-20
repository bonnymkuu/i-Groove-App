package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityArtistProfileBinding implements ViewBinding {
    public final ViewPager2 UpcomingEventsSlider;
    public final TextView bioTextView;
    public final TextView depositEditText;
    public final Button editButton;
    public final RecyclerView noticeRecyclerView;
    public final TextView priceEditText;
    public final ImageView profileImageView;
    public final ProgressBar progressBar;
    public final TextView recommendedLabel;
    public final RecyclerView recommendedRecyclerView;
    private final NestedScrollView rootView;
    public final RecyclerView songsRecyclerView;
    public final RecyclerView soundRecyclerview;
    public final TextView titleText1;
    public final TextView titleText2;
    public final TextView titleText3;
    public final TextView transportEditText;
    public final TextView usernameTextView;
    public final RecyclerView venueRecyclerview;

    private ActivityArtistProfileBinding(NestedScrollView rootView, ViewPager2 UpcomingEventsSlider, TextView bioTextView, TextView depositEditText, Button editButton, RecyclerView noticeRecyclerView, TextView priceEditText, ImageView profileImageView, ProgressBar progressBar, TextView recommendedLabel, RecyclerView recommendedRecyclerView, RecyclerView songsRecyclerView, RecyclerView soundRecyclerview, TextView titleText1, TextView titleText2, TextView titleText3, TextView transportEditText, TextView usernameTextView, RecyclerView venueRecyclerview) {
        this.rootView = rootView;
        this.UpcomingEventsSlider = UpcomingEventsSlider;
        this.bioTextView = bioTextView;
        this.depositEditText = depositEditText;
        this.editButton = editButton;
        this.noticeRecyclerView = noticeRecyclerView;
        this.priceEditText = priceEditText;
        this.profileImageView = profileImageView;
        this.progressBar = progressBar;
        this.recommendedLabel = recommendedLabel;
        this.recommendedRecyclerView = recommendedRecyclerView;
        this.songsRecyclerView = songsRecyclerView;
        this.soundRecyclerview = soundRecyclerview;
        this.titleText1 = titleText1;
        this.titleText2 = titleText2;
        this.titleText3 = titleText3;
        this.transportEditText = transportEditText;
        this.usernameTextView = usernameTextView;
        this.venueRecyclerview = venueRecyclerview;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityArtistProfileBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityArtistProfileBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_artist_profile, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityArtistProfileBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.UpcomingEventsSlider;
        ViewPager2 UpcomingEventsSlider = (ViewPager2) ViewBindings.findChildViewById(rootView, id);
        if (UpcomingEventsSlider != null) {
            id = R.id.bioTextView;
            TextView bioTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (bioTextView != null) {
                id = R.id.depositEditText;
                TextView depositEditText = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (depositEditText != null) {
                    id = R.id.editButton;
                    Button editButton = (Button) ViewBindings.findChildViewById(rootView, id);
                    if (editButton != null) {
                        id = R.id.noticeRecyclerView;
                        RecyclerView noticeRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                        if (noticeRecyclerView != null) {
                            id = R.id.priceEditText;
                            TextView priceEditText = (TextView) ViewBindings.findChildViewById(rootView, id);
                            if (priceEditText != null) {
                                id = R.id.profileImageView;
                                ImageView profileImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                if (profileImageView != null) {
                                    id = R.id.progressBar;
                                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                    if (progressBar != null) {
                                        id = R.id.recommendedLabel;
                                        TextView recommendedLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
                                        if (recommendedLabel != null) {
                                            id = R.id.recommendedRecyclerView;
                                            RecyclerView recommendedRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                            if (recommendedRecyclerView != null) {
                                                id = R.id.songsRecyclerView;
                                                RecyclerView songsRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                if (songsRecyclerView != null) {
                                                    id = R.id.soundRecyclerview;
                                                    RecyclerView soundRecyclerview = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                    if (soundRecyclerview != null) {
                                                        id = R.id.titleText1;
                                                        TextView titleText1 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                        if (titleText1 != null) {
                                                            id = R.id.titleText2;
                                                            TextView titleText2 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                            if (titleText2 != null) {
                                                                id = R.id.titleText3;
                                                                TextView titleText3 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                if (titleText3 != null) {
                                                                    id = R.id.transportEditText;
                                                                    TextView transportEditText = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                    if (transportEditText != null) {
                                                                        id = R.id.usernameTextView;
                                                                        TextView usernameTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                        if (usernameTextView != null) {
                                                                            id = R.id.venueRecyclerview;
                                                                            RecyclerView venueRecyclerview = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                                            if (venueRecyclerview != null) {
                                                                                return new ActivityArtistProfileBinding((NestedScrollView) rootView, UpcomingEventsSlider, bioTextView, depositEditText, editButton, noticeRecyclerView, priceEditText, profileImageView, progressBar, recommendedLabel, recommendedRecyclerView, songsRecyclerView, soundRecyclerview, titleText1, titleText2, titleText3, transportEditText, usernameTextView, venueRecyclerview);
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
