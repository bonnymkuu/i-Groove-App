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
public final class RecommendedItemBinding implements ViewBinding {
    public final ImageView recommendedImage;
    public final TextView recommendedTitle;
    private final LinearLayout rootView;

    private RecommendedItemBinding(LinearLayout rootView, ImageView recommendedImage, TextView recommendedTitle) {
        this.rootView = rootView;
        this.recommendedImage = recommendedImage;
        this.recommendedTitle = recommendedTitle;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static RecommendedItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static RecommendedItemBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.recommended_item, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static RecommendedItemBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.recommendedImage;
        ImageView recommendedImage = (ImageView) ViewBindings.findChildViewById(rootView, id);
        if (recommendedImage != null) {
            id = R.id.recommendedTitle;
            TextView recommendedTitle = (TextView) ViewBindings.findChildViewById(rootView, id);
            if (recommendedTitle != null) {
                return new RecommendedItemBinding((LinearLayout) rootView, recommendedImage, recommendedTitle);
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
