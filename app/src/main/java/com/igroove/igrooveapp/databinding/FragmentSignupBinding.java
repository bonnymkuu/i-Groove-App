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
public final class FragmentSignupBinding implements ViewBinding {
    public final CountryCodePicker countryCode;
    public final EditText countryEditText;
    public final EditText emailEditText;
    public final EditText passwordEditText;
    public final TextInputLayout passwordTIL;
    public final EditText phoneEditText;
    public final ImageView profileImageView;
    public final ProgressBar progressBar;
    private final NestedScrollView rootView;
    public final Button submit;
    public final EditText usernameEditText;

    private FragmentSignupBinding(NestedScrollView rootView, CountryCodePicker countryCode, EditText countryEditText, EditText emailEditText, EditText passwordEditText, TextInputLayout passwordTIL, EditText phoneEditText, ImageView profileImageView, ProgressBar progressBar, Button submit, EditText usernameEditText) {
        this.rootView = rootView;
        this.countryCode = countryCode;
        this.countryEditText = countryEditText;
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
        this.passwordTIL = passwordTIL;
        this.phoneEditText = phoneEditText;
        this.profileImageView = profileImageView;
        this.progressBar = progressBar;
        this.submit = submit;
        this.usernameEditText = usernameEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public NestedScrollView getRoot() {
        return this.rootView;
    }

    public static FragmentSignupBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentSignupBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_signup, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentSignupBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.countryCode;
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
                                        id = R.id.submit;
                                        Button submit = (Button) ViewBindings.findChildViewById(rootView, id);
                                        if (submit != null) {
                                            id = R.id.usernameEditText;
                                            EditText usernameEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                                            if (usernameEditText != null) {
                                                return new FragmentSignupBinding((NestedScrollView) rootView, countryCode, countryEditText, emailEditText, passwordEditText, passwordTIL, phoneEditText, profileImageView, progressBar, submit, usernameEditText);
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
