package com.igroove.igrooveapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.viewbinding.ViewBinding;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class GooglepayButtonBinding implements ViewBinding {
    private final RelativeLayout rootView;

    private GooglepayButtonBinding(RelativeLayout rootView) {
        this.rootView = rootView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static GooglepayButtonBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static GooglepayButtonBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.googlepay_button, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static GooglepayButtonBinding bind(View rootView) {
        if (rootView == null) {
            throw new NullPointerException("rootView");
        }
        return new GooglepayButtonBinding((RelativeLayout) rootView);
    }
}
