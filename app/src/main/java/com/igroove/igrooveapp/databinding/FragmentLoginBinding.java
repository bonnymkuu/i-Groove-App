package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputLayout;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class FragmentLoginBinding implements ViewBinding {
    public final TextInputLayout emailTIL;
    public final EditText passwordEditText;
    public final TextInputLayout passwordTIL;
    public final ProgressBar progressBar;
    public final TextView resetTextView;
    private final RelativeLayout rootView;
    public final Button submit;
    public final EditText textEditText;

    private FragmentLoginBinding(RelativeLayout rootView, TextInputLayout emailTIL, EditText passwordEditText, TextInputLayout passwordTIL, ProgressBar progressBar, TextView resetTextView, Button submit, EditText textEditText) {
        this.rootView = rootView;
        this.emailTIL = emailTIL;
        this.passwordEditText = passwordEditText;
        this.passwordTIL = passwordTIL;
        this.progressBar = progressBar;
        this.resetTextView = resetTextView;
        this.submit = submit;
        this.textEditText = textEditText;
    }

    @Override // androidx.viewbinding.ViewBinding
    public RelativeLayout getRoot() {
        return this.rootView;
    }

    public static FragmentLoginBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static FragmentLoginBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.fragment_login, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static FragmentLoginBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.emailTIL;
        TextInputLayout emailTIL = (TextInputLayout) ViewBindings.findChildViewById(rootView, id);
        if (emailTIL != null) {
            id = R.id.passwordEditText;
            EditText passwordEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (passwordEditText != null) {
                id = R.id.passwordTIL;
                TextInputLayout passwordTIL = (TextInputLayout) ViewBindings.findChildViewById(rootView, id);
                if (passwordTIL != null) {
                    id = R.id.progressBar;
                    ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(rootView, id);
                    if (progressBar != null) {
                        id = R.id.resetTextView;
                        TextView resetTextView = (TextView) ViewBindings.findChildViewById(rootView, id);
                        if (resetTextView != null) {
                            id = R.id.submit;
                            Button submit = (Button) ViewBindings.findChildViewById(rootView, id);
                            if (submit != null) {
                                id = R.id.textEditText;
                                EditText textEditText = (EditText) ViewBindings.findChildViewById(rootView, id);
                                if (textEditText != null) {
                                    return new FragmentLoginBinding((RelativeLayout) rootView, emailTIL, passwordEditText, passwordTIL, progressBar, resetTextView, submit, textEditText);
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
