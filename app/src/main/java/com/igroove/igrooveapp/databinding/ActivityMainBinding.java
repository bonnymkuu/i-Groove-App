package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityMainBinding implements ViewBinding {
    public final ViewPager2 UpcomingEventsSlider;
    public final ImageView accountBalance;
    public final ImageView addActivity;
    public final RecyclerView artistsRecyclerView;
    public final ImageView genresImageView;
    public final RelativeLayout headerLayout;
    public final ImageView logo;
    public final ImageView logoutIcon;
    public final ImageView othersActivity;
    public final TextView paypalLinkTextView;
    public final TextView recommendedLabel;
    public final RecyclerView recommendedRecyclerView;
    private final NestedScrollView rootView;
    public final ImageView searchIcon;
    public final RecyclerView songsRecyclerView;
    public final ImageView soundActivity;
    public final TextView title;
    public final TextView title11;
    public final TextView topArtistsLabel;
    public final TextView topSongsLabel;
    public final TextView upcomingEventsLabel;
    public final RelativeLayout userLayout;
    public final LinearLayout userLayout2;
    public final ImageView userPic;
    public final TextView usernameTextView;
    public final ImageView venueActivity;

    private ActivityMainBinding(NestedScrollView rootView, ViewPager2 UpcomingEventsSlider, ImageView accountBalance, ImageView addActivity, RecyclerView artistsRecyclerView, ImageView genresImageView, RelativeLayout headerLayout, ImageView logo, ImageView logoutIcon, ImageView othersActivity, TextView paypalLinkTextView, TextView recommendedLabel, RecyclerView recommendedRecyclerView, ImageView searchIcon, RecyclerView songsRecyclerView, ImageView soundActivity, TextView title, TextView title11, TextView topArtistsLabel, TextView topSongsLabel, TextView upcomingEventsLabel, RelativeLayout userLayout, LinearLayout userLayout2, ImageView userPic, TextView usernameTextView, ImageView venueActivity) {
        this.rootView = rootView;
        this.UpcomingEventsSlider = UpcomingEventsSlider;
        this.accountBalance = accountBalance;
        this.addActivity = addActivity;
        this.artistsRecyclerView = artistsRecyclerView;
        this.genresImageView = genresImageView;
        this.headerLayout = headerLayout;
        this.logo = logo;
        this.logoutIcon = logoutIcon;
        this.othersActivity = othersActivity;
        this.paypalLinkTextView = paypalLinkTextView;
        this.recommendedLabel = recommendedLabel;
        this.recommendedRecyclerView = recommendedRecyclerView;
        this.searchIcon = searchIcon;
        this.songsRecyclerView = songsRecyclerView;
        this.soundActivity = soundActivity;
        this.title = title;
        this.title11 = title11;
        this.topArtistsLabel = topArtistsLabel;
        this.topSongsLabel = topSongsLabel;
        this.upcomingEventsLabel = upcomingEventsLabel;
        this.userLayout = userLayout;
        this.userLayout2 = userLayout2;
        this.userPic = userPic;
        this.usernameTextView = usernameTextView;
        this.venueActivity = venueActivity;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityMainBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_main, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityMainBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.UpcomingEventsSlider;
        ViewPager2 UpcomingEventsSlider = (ViewPager2) ViewBindings.findChildViewById(rootView, id);
        if (UpcomingEventsSlider != null) {
            id = R.id.account_balance;
            ImageView accountBalance = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (accountBalance != null) {
                id = R.id.addActivity;
                ImageView addActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                if (addActivity != null) {
                    id = R.id.artistsRecyclerView;
                    RecyclerView artistsRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                    if (artistsRecyclerView != null) {
                        id = R.id.genresImageView;
                        ImageView genresImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
                        if (genresImageView != null) {
                            id = R.id.headerLayout;
                            RelativeLayout headerLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, id);
                            if (headerLayout != null) {
                                id = R.id.logo;
                                ImageView logo = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                if (logo != null) {
                                    id = R.id.logoutIcon;
                                    ImageView logoutIcon = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                    if (logoutIcon != null) {
                                        id = R.id.othersActivity;
                                        ImageView othersActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                        if (othersActivity != null) {
                                            id = R.id.paypalLinkTextView;
                                            TextView paypalLinkTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                                            if (paypalLinkTextView != null) {
                                                id = R.id.recommendedLabel;
                                                TextView recommendedLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                if (recommendedLabel != null) {
                                                    id = R.id.recommendedRecyclerView;
                                                    RecyclerView recommendedRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                    if (recommendedRecyclerView != null) {
                                                        id = R.id.searchIcon;
                                                        ImageView searchIcon = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                        if (searchIcon != null) {
                                                            id = R.id.songsRecyclerView;
                                                            RecyclerView songsRecyclerView = (RecyclerView) ViewBindings.findChildViewById(rootView, id);
                                                            if (songsRecyclerView != null) {
                                                                id = R.id.soundActivity;
                                                                ImageView soundActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                                if (soundActivity != null) {
                                                                    id = R.id.title;
                                                                    TextView title = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                    if (title != null) {
                                                                        id = R.id.title11;
                                                                        TextView title11 = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                        if (title11 != null) {
                                                                            id = R.id.topArtistsLabel;
                                                                            TextView topArtistsLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                            if (topArtistsLabel != null) {
                                                                                id = R.id.topSongsLabel;
                                                                                TextView topSongsLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                if (topSongsLabel != null) {
                                                                                    id = R.id.upcomingEventsLabel;
                                                                                    TextView upcomingEventsLabel = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                    if (upcomingEventsLabel != null) {
                                                                                        id = R.id.userLayout;
                                                                                        RelativeLayout userLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, id);
                                                                                        if (userLayout != null) {
                                                                                            id = R.id.userLayout2;
                                                                                            LinearLayout userLayout2 = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                                                                                            if (userLayout2 != null) {
                                                                                                id = R.id.userPic;
                                                                                                ImageView userPic = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                                                                if (userPic != null) {
                                                                                                    id = R.id.usernameTextView;
                                                                                                    TextView usernameTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                                                    if (usernameTextView != null) {
                                                                                                        id = R.id.venueActivity;
                                                                                                        ImageView venueActivity = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                                                                        if (venueActivity != null) {
                                                                                                            return new ActivityMainBinding((NestedScrollView) rootView, UpcomingEventsSlider, accountBalance, addActivity, artistsRecyclerView, genresImageView, headerLayout, logo, logoutIcon, othersActivity, paypalLinkTextView, recommendedLabel, recommendedRecyclerView, searchIcon, songsRecyclerView, soundActivity, title, title11, topArtistsLabel, topSongsLabel, upcomingEventsLabel, userLayout, userLayout2, userPic, usernameTextView, venueActivity);
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
