package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class FragmentArtistSignupBinding implements ViewBinding {
    public final EditText artistNameEditText;
    public final CountryCodePicker countryCode;
    public final EditText countryEditText;
    public final EditText emailEditText;
    public final EditText passwordEditText;
    public final TextInputLayout passwordTIL;
    public final EditText phoneEditText;
    public final ImageView profileImageView;
    public final ProgressBar progressBar;
    public final ProgressBar progressbar;
    private final NestedScrollView rootView;
    public final Button submit;

    private FragmentArtistSignupBinding(NestedScrollView rootView, EditText artistNameEditText, CountryCodePicker countryCode, EditText countryEditText, EditText emailEditText, EditText passwordEditText, TextInputLayout passwordTIL, EditText phoneEditText, ImageView profileImageView, ProgressBar progressBar, ProgressBar progressbar, Button submit) {
        this.rootView = rootView;
        this.artistNameEditText = artistNameEditText;
        this.countryCode = countryCode;
        this.countryEditText = countryEditText;
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
        this.passwordTIL = passwordTIL;
        this.phoneEditText = phoneEditText;
        this.profileImageView = profileImageView;
        this.progressBar = progressBar;
        this.progressbar = progressbar;
        this.submit = submit;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentArtistSignupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentArtistSignupBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_artist_signup, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentArtistSignupBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.artistNameEditText;
        EditText artistNameEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (artistNameEditText != null) {
            id = R.id.countryCode;
            CountryCodePicker countryCode = (CountryCodePicker) ViewBindings.findChildViewById(rootView, id);
            if (countryCode != null) {
                id = R.id.countryEditText;
                EditText countryEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (countryEditText != null) {
                    id = R.id.emailEditText;
                    EditText emailEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                    if (emailEditText != null) {
                        id = R.id.passwordEditText;
                        EditText passwordEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                        if (passwordEditText != null) {
                            id = R.id.passwordTIL;
                            TextInputLayout passwordTIL = (TextInputLayout) ViewBindings.findChildViewById(rootView, id);
                            if (passwordTIL != null) {
                                id = R.id.phoneEditText;
                                EditText phoneEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                                if (phoneEditText != null) {
                                    id = R.id.profileImageView;
                                    ImageView profileImageView = (ImageView) ViewBindings.findChildViewById(rootView, id);
                                    if (profileImageView != null) {
                                        id = R.id.progressBar;
                                        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                        if (progressBar != null) {
                                            id = R.id.progressbar;
                                            ProgressBar progressbar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                                            if (progressbar != null) {
                                                id = R.id.submit;
                                                Button submit = (Button) ViewBindings.findChildViewById(rootView, id);
                                                if (submit != null) {
                                                    return new FragmentArtistSignupBinding((NestedScrollView) rootView, artistNameEditText, countryCode, countryEditText, emailEditText, passwordEditText, passwordTIL, phoneEditText, profileImageView, progressBar, progressbar, submit);
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
