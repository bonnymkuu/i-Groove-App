package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityRegisterBinding implements ViewBinding {
    public final FrameLayout fragmentContainer;
    private final LinearLayout rootView;
    public final TabLayout tabLayout;
    public final ViewPager viewPager;

    private ActivityRegisterBinding(LinearLayout rootView, FrameLayout fragmentContainer, TabLayout tabLayout, ViewPager viewPager) {
        this.rootView = rootView;
        this.fragmentContainer = fragmentContainer;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityRegisterBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityRegisterBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_register, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityRegisterBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.fragment_container;
        FrameLayout fragmentContainer = (FrameLayout) ViewBindings.findChildViewById(rootView, id);
        if (fragmentContainer != null) {
            id = R.id.tab_layout;
            TabLayout tabLayout = (TabLayout) ViewBindings.findChildViewById(rootView, id);
            if (tabLayout != null) {
                id = R.id.view_pager;
                ViewPager viewPager = (ViewPager) ViewBindings.findChildViewById(rootView, id);
                if (viewPager != null) {
                    return new ActivityRegisterBinding((LinearLayout) rootView, fragmentContainer, tabLayout, viewPager);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
