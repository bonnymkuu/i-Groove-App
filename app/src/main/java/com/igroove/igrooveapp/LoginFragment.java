package com.igroove.igrooveapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

public class LoginFragment extends Fragment {

    private EditText emailEditText, passwordEditText;
    ProgressBar progressBar;
    private TextView resetTextView;
    private Button submitButton;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.textEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        resetTextView = view.findViewById(R.id.resetTextView);
        submitButton = view.findViewById(R.id.submit);
        progressBar = view.findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        resetTextView.setOnClickListener(v -> resetPassword());
        submitButton.setOnClickListener(v -> attemptLogin());

        return view;
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty() || !isEmail(email)) {
            emailEditText.setError("Please enter a valid email");
            emailEditText.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Reset email sent. Check your inbox.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin() {
        String input = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (input.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Email/Username and Password required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEmail(input)) {
            signIn(input, password);
        } else {
            getEmailFromUsername(input, email -> {
                if (email != null) {
                    signIn(email, password);
                } else {
                    Toast.makeText(getContext(), "Username not found", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else {
                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isEmail(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    private void getEmailFromUsername(String username, OnEmailRetrievedListener listener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("name").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String email = userSnapshot.child("email").getValue(String.class);
                        listener.onEmailRetrieved(email);
                        return;
                    }
                } else {
                    listener.onEmailRetrieved(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onEmailRetrieved(null);
            }
        });
    }

    interface OnEmailRetrievedListener {
        void onEmailRetrieved(String email);
    }
}
