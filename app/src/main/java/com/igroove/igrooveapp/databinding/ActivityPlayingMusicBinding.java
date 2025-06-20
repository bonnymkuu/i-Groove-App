package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityPlayingMusicBinding implements ViewBinding {
    public final ImageView favorite;
    public final ImageView forward;
    public final LinearLayout ll1;
    public final LinearLayout ll2;
    public final ImageView loop;
    public final ImageView loopOne;
    public final ImageView more;
    public final TextView musicName;
    public final ImageView next;
    public final ImageView play;
    public final TextView playing;
    public final ImageView previous;
    public final ProgressBar progress;
    public final ImageView rewind;
    private final RelativeLayout rootView;
    public final ImageView rotatingIcon;
    public final TextView time;

    private ActivityPlayingMusicBinding(RelativeLayout rootView, ImageView favorite, ImageView forward, LinearLayout ll1, LinearLayout ll2, ImageView loop, ImageView loopOne, ImageView more, TextView musicName, ImageView next, ImageView play, TextView playing, ImageView previous, ProgressBar progress, ImageView rewind, ImageView rotatingIcon, TextView time) {
        this.rootView = rootView;
        this.favorite = favorite;
        this.forward = forward;
        this.ll1 = ll1;
        this.ll2 = ll2;
        this.loop = loop;
        this.loopOne = loopOne;
        this.more = more;
        this.musicName = musicName;
        this.next = next;
        this.play = play;
        this.playing = playing;
        this.previous = previous;
        this.progress = progress;
        this.rewind = rewind;
        this.rotatingIcon = rotatingIcon;
        this.time = time;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static ActivityPlayingMusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityPlayingMusicBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_playing_music, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityPlayingMusicBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.favorite;
        ImageView favorite = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (favorite != null) {
            id = R.id.forward;
            ImageView forward = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (forward != null) {
                id = R.id.ll1;
                LinearLayout ll1 = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                if (ll1 != null) {
                    id = R.id.ll2;
                    LinearLayout ll2 = (LinearLayout) ViewBindings.findChildViewById(rootView, id);
                    if (ll2 != null) {
                        id = R.id.loop;
                        ImageView loop = (ImageView) ViewBindings.findChildViewById(rootView, id);
                        if (loop != null) {
                            id = R.id.loopOne;
                            ImageView loopOne = (ImageView) ViewBindings.findChildViewById(rootView, id);
                            if (loopOne != null) {
                                id = R.id.more;
                                ImageView more = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                if (more != null) {
                                    id = R.id.musicName;
                                    TextView musicName = (TextView) ViewBindings.findChildViewById(rootView, id);
                                    if (musicName != null) {
                                        id = R.id.next;
                                        ImageView next = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                        if (next != null) {
                                            id = R.id.play;
                                            ImageView play = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                            if (play != null) {
                                                id = R.id.playing;
                                                TextView playing = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                if (playing != null) {
                                                    id = R.id.previous;
                                                    ImageView previous = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                    if (previous != null) {
                                                        id = R.id.progress;
                                                        ProgressBar progress = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                                        if (progress != null) {
                                                            id = R.id.rewind;
                                                            ImageView rewind = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                            if (rewind != null) {
                                                                id = R.id.rotatingIcon;
                                                                ImageView rotatingIcon = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                                                if (rotatingIcon != null) {
                                                                    id = R.id.time;
                                                                    TextView time = (TextView) ViewBindings.findChildViewById(rootView, id);
                                                                    if (time != null) {
                                                                        return new ActivityPlayingMusicBinding((RelativeLayout) rootView, favorite, forward, ll1, ll2, loop, loopOne, more, musicName, next, play, playing, previous, progress, rewind, rotatingIcon, time);
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
