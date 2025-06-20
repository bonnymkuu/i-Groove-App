package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityArtistUploadBinding implements ViewBinding {
    public final Button addEventButton;
    public final TextView audioNameTextView;
    public final LinearLayout audioPlayerLayout;
    public final SeekBar audioSeekBar;
    public final Button audioUploadButton;
    public final ImageView eventImageView;
    public final Spinner genreSpinner;
    public final ImageView playPauseButton;
    public final EditText priceEditText;
    private final NestedScrollView rootView;
    public final TextView songUploadTextView;
    public final ProgressBar uploadProgressBar;

    private ActivityArtistUploadBinding(NestedScrollView rootView, Button addEventButton, TextView audioNameTextView, LinearLayout audioPlayerLayout, SeekBar audioSeekBar, Button audioUploadButton, ImageView eventImageView, Spinner genreSpinner, ImageView playPauseButton, EditText priceEditText, TextView songUploadTextView, ProgressBar uploadProgressBar) {
        this.rootView = rootView;
        this.addEventButton = addEventButton;
        this.audioNameTextView = audioNameTextView;
        this.audioPlayerLayout = audioPlayerLayout;
        this.audioSeekBar = audioSeekBar;
        this.audioUploadButton = audioUploadButton;
        this.eventImageView = eventImageView;
        this.genreSpinner = genreSpinner;
        this.playPauseButton = playPauseButton;
        this.priceEditText = priceEditText;
        this.songUploadTextView = songUploadTextView;
        this.uploadProgressBar = uploadProgressBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityArtistUploadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityArtistUploadBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_artist_upload, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityArtistUploadBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.addEventButton;
        Button addEventButton = (Button) ViewBindings.findChildViewById(rootView, id);
        if (addEventButton != null) {
            id = R.id.audioNameTextView;
            TextView audioNameTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (audioNameTextView != null) {
                id = R.id.audioPlayerLayout;
                LinearLayout audioPlayerLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                if (audioPlayerLayout != null) {
                    id = R.id.audioSeekBar;
                    SeekBar audioSeekBar = (SeekBar) ViewBindings.findChildViewById(rootView, id);
                    if (audioSeekBar != null) {
                        id = R.id.audioUploadButton;
                        Button audioUploadButton = (Button) ViewBindings.findChildViewById(rootView, id);
                        if (audioUploadButton != null) {
                            id = R.id.eventImageView;
                            ImageView eventImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
                            if (eventImageView != null) {
                                id = R.id.genreSpinner;
                                Spinner genreSpinner = (Spinner) ViewBindings.findChildViewById(rootView, id);
                                if (genreSpinner != null) {
                                    id = R.id.playPauseButton;
                                    ImageView playPauseButton = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                    if (playPauseButton != null) {
                                        id = R.id.priceEditText;
                                        EditText priceEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                                        if (priceEditText != null) {
                                            id = R.id.songUploadTextView;
                                            TextView songUploadTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                                            if (songUploadTextView != null) {
                                                id = R.id.uploadProgressBar;
                                                ProgressBar uploadProgressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                                if (uploadProgressBar != null) {
                                                    return new ActivityArtistUploadBinding((NestedScrollView) rootView, addEventButton, audioNameTextView, audioPlayerLayout, audioSeekBar, audioUploadButton, eventImageView, genreSpinner, playPauseButton, priceEditText, songUploadTextView, uploadProgressBar);
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
