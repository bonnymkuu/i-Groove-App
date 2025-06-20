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
public final class ItemUserBinding implements ViewBinding {
    public final TextView phoneTextView;
    public final ImageView profileIv;
    private final LinearLayout rootView;
    public final TextView usernameTextView;

    private ItemUserBinding(LinearLayout rootView, TextView phoneTextView, ImageView profileIv, TextView usernameTextView) {
        this.rootView = rootView;
        this.phoneTextView = phoneTextView;
        this.profileIv = profileIv;
        this.usernameTextView = usernameTextView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ItemUserBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ItemUserBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.item_user, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ItemUserBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.phoneTextView;
        TextView phoneTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
        if (phoneTextView != null) {
            id = R.id.profileIv;
            ImageView profileIv = (ImageView) ViewBindings.findChildViewById(rootView, id);
            if (profileIv != null) {
                id = R.id.usernameTextView;
                TextView usernameTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                if (usernameTextView != null) {
                    return new ItemUserBinding((LinearLayout) rootView, phoneTextView, profileIv, usernameTextView);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
