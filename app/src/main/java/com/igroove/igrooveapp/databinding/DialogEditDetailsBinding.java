package com.igroove.igrooveapp.databinding;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.igroove.igrooveapp.R;

/* loaded from: classes5.dex */
public final class DialogEditDetailsBinding implements ViewBinding {
    public final EditText editDeposit;
    public final EditText editPrice;
    public final EditText editTransport;
    private final LinearLayout rootView;

    private DialogEditDetailsBinding(LinearLayout rootView, EditText editDeposit, EditText editPrice, EditText editTransport) {
        this.rootView = rootView;
        this.editDeposit = editDeposit;
        this.editPrice = editPrice;
        this.editTransport = editTransport;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static DialogEditDetailsBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static DialogEditDetailsBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.dialog_edit_details, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    public static DialogEditDetailsBinding bind(View rootView) throws Resources.NotFoundException {
        int id = R.id.editDeposit;
        EditText editDeposit = (EditText) ViewBindings.findChildViewById(rootView, id);
        if (editDeposit != null) {
            id = R.id.editPrice;
            EditText editPrice = (EditText) ViewBindings.findChildViewById(rootView, id);
            if (editPrice != null) {
                id = R.id.editTransport;
                EditText editTransport = (EditText) ViewBindings.findChildViewById(rootView, id);
                if (editTransport != null) {
                    return new DialogEditDetailsBinding((LinearLayout) rootView, editDeposit, editPrice, editTransport);
                }
            }
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}
