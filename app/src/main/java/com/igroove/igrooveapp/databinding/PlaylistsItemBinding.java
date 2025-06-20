package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class PlaylistsItemBinding implements ViewBinding {
    public final ImageView artistImage;
    public final TextView artistName;
    public final TextView bookings;
    public final TextView buyOnline;
    public final TextView buyWithAirtime;
    public final TextView buyWithBankCard;
    public final LinearLayout ll1;
    private final LinearLayout rootView;
    public final TextView songPrice;
    public final TextView songTitle;

    private PlaylistsItemBinding(LinearLayout rootView, ImageView artistImage, TextView artistName, TextView bookings, TextView buyOnline, TextView buyWithAirtime, TextView buyWithBankCard, LinearLayout ll1, TextView songPrice, TextView songTitle) {
        this.rootView = rootView;
        this.artistImage = artistImage;
        this.artistName = artistName;
        this.bookings = bookings;
        this.buyOnline = buyOnline;
        this.buyWithAirtime = buyWithAirtime;
        this.buyWithBankCard = buyWithBankCard;
        this.ll1 = ll1;
        this.songPrice = songPrice;
        this.songTitle = songTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static PlaylistsItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static PlaylistsItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.playlists_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static PlaylistsItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.artistImage;
        ImageView artistImage = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (artistImage != null) {
            id = R.id.artistName;
            TextView artistName = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (artistName != null) {
                id = R.id.bookings;
                TextView bookings = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (bookings != null) {
                    id = R.id.buyOnline;
                    TextView buyOnline = (TextView) ViewBindings.findChildViewById(rootView, id);
                    if (buyOnline != null) {
                        id = R.id.buyWithAirtime;
                        TextView buyWithAirtime = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (buyWithAirtime != null) {
                            id = R.id.buyWithBankCard;
                            TextView buyWithBankCard = (TextView) ViewBindings.findChildViewById(rootView, id);
                            if (buyWithBankCard != null) {
                                id = R.id.ll1;
                                LinearLayout ll1 = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                                if (ll1 != null) {
                                    id = R.id.songPrice;
                                    TextView songPrice = (TextView) ViewBindings.findChildViewById(rootView, id);
                                    if (songPrice != null) {
                                        id = R.id.songTitle;
                                        TextView songTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
                                        if (songTitle != null) {
                                            return new PlaylistsItemBinding((LinearLayout) rootView, artistImage, artistName, bookings, buyOnline, buyWithAirtime, buyWithBankCard, ll1, songPrice, songTitle);
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
