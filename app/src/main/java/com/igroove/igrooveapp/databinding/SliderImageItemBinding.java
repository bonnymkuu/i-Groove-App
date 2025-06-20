package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class SliderImageItemBinding implements ViewBinding {
    private final FrameLayout rootView;
    public final ImageView sliderImageView;

    private SliderImageItemBinding(FrameLayout rootView, ImageView sliderImageView) {
        this.rootView = rootView;
        this.sliderImageView = sliderImageView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static SliderImageItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static SliderImageItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.slider_image_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static SliderImageItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.sliderImageView;
        ImageView sliderImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (sliderImageView != null) {
            return new SliderImageItemBinding((FrameLayout) rootView, sliderImageView);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
