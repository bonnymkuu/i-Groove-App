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
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityRentSoundBinding implements ViewBinding {
    public final TextView audioNameTextView;
    public final LinearLayout audioPlayerLayout;
    public final SeekBar audioSeekBar;
    public final ImageView playPauseButton;
    public final EditText priceEditText;
    public final ImageView profileImage;
    private final LinearLayout rootView;
    public final Button selectAudioButton;
    public final Button submitAudioButton;
    public final ProgressBar uploadProgressBar;

    private ActivityRentSoundBinding(LinearLayout rootView, TextView audioNameTextView, LinearLayout audioPlayerLayout, SeekBar audioSeekBar, ImageView playPauseButton, EditText priceEditText, ImageView profileImage, Button selectAudioButton, Button submitAudioButton, ProgressBar uploadProgressBar) {
        this.rootView = rootView;
        this.audioNameTextView = audioNameTextView;
        this.audioPlayerLayout = audioPlayerLayout;
        this.audioSeekBar = audioSeekBar;
        this.playPauseButton = playPauseButton;
        this.priceEditText = priceEditText;
        this.profileImage = profileImage;
        this.selectAudioButton = selectAudioButton;
        this.submitAudioButton = submitAudioButton;
        this.uploadProgressBar = uploadProgressBar;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityRentSoundBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityRentSoundBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_rent_sound, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityRentSoundBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.audioNameTextView;
        TextView audioNameTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (audioNameTextView != null) {
            id = R.id.audioPlayerLayout;
            LinearLayout audioPlayerLayout = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
            if (audioPlayerLayout != null) {
                id = R.id.audioSeekBar;
                SeekBar audioSeekBar = (SeekBar) ViewBindings.findChildViewById(rootView, id);
                if (audioSeekBar != null) {
                    id = R.id.playPauseButton;
                    ImageView playPauseButton = (ImageView) ViewBindings.findChildViewById(rootView, id);
                    if (playPauseButton != null) {
                        id = R.id.priceEditText;
                        EditText priceEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                        if (priceEditText != null) {
                            id = R.id.profileImage;
                            ImageView profileImage = (ImageView) ViewBindings.findChildViewById(rootView, id);
                            if (profileImage != null) {
                                id = R.id.selectAudioButton;
                                Button selectAudioButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                if (selectAudioButton != null) {
                                    id = R.id.submitAudioButton;
                                    Button submitAudioButton = (Button) ViewBindings.findChildViewById(rootView, id);
                                    if (submitAudioButton != null) {
                                        id = R.id.uploadProgressBar;
                                        ProgressBar uploadProgressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                        if (uploadProgressBar != null) {
                                            return new ActivityRentSoundBinding((LinearLayout) rootView, audioNameTextView, audioPlayerLayout, audioSeekBar, playPauseButton, priceEditText, profileImage, selectAudioButton, submitAudioButton, uploadProgressBar);
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
