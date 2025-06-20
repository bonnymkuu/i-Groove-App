package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class ActivityPaymentsBinding implements ViewBinding {
    public final EditText amountEditText;
    public final Button btnPay;
    public final FloatingActionButton downloadButton;
    public final EditText editTextLastName;
    public final ImageView googlePayButton;
    public final Button mtnBtn;
    public final Button paypalBtn;
    public final ProgressBar progressBar;
    public final RelativeLayout relativeLayout;
    private final NestedScrollView rootView;
    public final Button stripeBtn;
    public final Button telkomBtn;
    public final Button vodacomBtn;

    private ActivityPaymentsBinding(NestedScrollView rootView, EditText amountEditText, Button btnPay, FloatingActionButton downloadButton, EditText editTextLastName, ImageView googlePayButton, Button mtnBtn, Button paypalBtn, ProgressBar progressBar, RelativeLayout relativeLayout, Button stripeBtn, Button telkomBtn, Button vodacomBtn) {
        this.rootView = rootView;
        this.amountEditText = amountEditText;
        this.btnPay = btnPay;
        this.downloadButton = downloadButton;
        this.editTextLastName = editTextLastName;
        this.googlePayButton = googlePayButton;
        this.mtnBtn = mtnBtn;
        this.paypalBtn = paypalBtn;
        this.progressBar = progressBar;
        this.relativeLayout = relativeLayout;
        this.stripeBtn = stripeBtn;
        this.telkomBtn = telkomBtn;
        this.vodacomBtn = vodacomBtn;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static ActivityPaymentsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityPaymentsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_payments, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static ActivityPaymentsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.amountEditText;
        EditText amountEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (amountEditText != null) {
            id = R.id.btnPay;
            Button btnPay = (Button) ViewBindings.findChildViewById(rootView, id);
            if (btnPay != null) {
                id = R.id.download_button;
                FloatingActionButton downloadButton = (FloatingActionButton) ViewBindings.findChildViewById(rootView, id);
                if (downloadButton != null) {
                    id = R.id.editTextLastName;
                    EditText editTextLastName = (EditText) ViewBindings.findChildViewById(rootView, id);
                    if (editTextLastName != null) {
                        id = R.id.googlePayButton;
                        ImageView googlePayButton = (ImageView) ViewBindings.findChildViewById(rootView, id);
                        if (googlePayButton != null) {
                            id = R.id.mtnBtn;
                            Button mtnBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                            if (mtnBtn != null) {
                                id = R.id.paypalBtn;
                                Button paypalBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                                if (paypalBtn != null) {
                                    id = R.id.progressBar;
                                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                    if (progressBar != null) {
                                        id = R.id.relativeLayout;
                                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(rootView, id);
                                        if (relativeLayout != null) {
                                            id = R.id.stripeBtn;
                                            Button stripeBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                                            if (stripeBtn != null) {
                                                id = R.id.telkomBtn;
                                                Button telkomBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                                                if (telkomBtn != null) {
                                                    id = R.id.vodacomBtn;
                                                    Button vodacomBtn = (Button) ViewBindings.findChildViewById(rootView, id);
                                                    if (vodacomBtn != null) {
                                                        return new ActivityPaymentsBinding((NestedScrollView) rootView, amountEditText, btnPay, downloadButton, editTextLastName, googlePayButton, mtnBtn, paypalBtn, progressBar, relativeLayout, stripeBtn, telkomBtn, vodacomBtn);
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
