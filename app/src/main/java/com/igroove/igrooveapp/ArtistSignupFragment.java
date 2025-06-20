package com.igroove.igrooveapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import com.google.firebase.storage.*;
import com.hbb20.CountryCodePicker;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class ArtistSignupFragment extends Fragment {

    private EditText artistNameEditText, emailEditText, passwordEditText, phoneEditText;
    private CountryCodePicker countryCode;
    private ImageView profileImageView;
    private ProgressBar progressBar;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_signup, container, false);

        // Initialize views
        artistNameEditText = view.findViewById(R.id.artistNameEditText);
        profileImageView = view.findViewById(R.id.profileImageView);
        progressBar = view.findViewById(R.id.progressBar);
        emailEditText = view.findViewById(R.id.emailEditText);
        countryCode = view.findViewById(R.id.countryCode);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        submitButton = view.findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();

        // Image picker
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            if (uri != null) {
                profileImageView.setImageURI(uri);
                submitButton.setEnabled(true);
            } else {
                Toast.makeText(getContext(), "Image selection failed", Toast.LENGTH_SHORT).show();
                submitButton.setEnabled(true);
            }
        });

        // Click listeners
        profileImageView.setOnClickListener(v -> launchGalleryForImageSelection());
        submitButton.setOnClickListener(v -> submitDetails());

        return view;
    }

    private void submitDetails() {
        String name = artistNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String fullPhone = countryCode.getSelectedCountryCodeWithPlus() + phone;

        Drawable drawable = profileImageView.getDrawable();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || drawable == null) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            Toast.makeText(getContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream bays = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bays);
        byte[] imageData = bays.toByteArray();

        showUserTypeDialog(name, fullPhone, email, password, imageData);
    }

    private void showUserTypeDialog(String name, String phone, String email, String password, byte[] imageData) {
        String[] options = {"To Upload Song", "To Upload Post", "To Advertise your Sound", "To Advertise your Venue"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Create a Group")
                .setItems(options, (dialog, which) -> {
                    String userType;
                    switch (which) {
                        case 0:
                            userType = "Artist";
                            break;
                        case 1:
                            userType = "UploadPost";
                            break;
                        case 2:
                            userType = "AdvertiseSong";
                            break;
                        case 3:
                            userType = "AdvertiseVenue";
                            break;
                        default:
                            userType = "Unknown";
                            break;
                    }
                    uploadImageAndRegister(name, phone, email, password, imageData, userType);
                })
                .create()
                .show();
    }

    private void uploadImageAndRegister(String name, String phone, String email, String password, byte[] imageData, String userType) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("profile_images")
                .child(UUID.randomUUID().toString() + ".jpg");

        imageRef.putBytes(imageData)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri ->
                        registerUser(name, phone, email, password, uri.toString(), userType)))
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    submitButton.setEnabled(true);
                });
    }

    private void registerUser(String name, String phone, String email, String password, String imageUrl, String userType) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserToDatabase(user.getUid(), name, phone, email, imageUrl, userType);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveUserToDatabase(String uid, String name, String phone, String email, String imageUrl, String userType) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("id", uid);
        userMap.put("name", name);
        userMap.put("phone", phone);
        userMap.put("email", email);
        userMap.put("image", imageUrl);
        userMap.put("position", userType);

        ref.child(uid).setValue(userMap).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Registered: " + email, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), MainActivity.class));
            } else {
                Toast.makeText(getContext(), "Database update failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchGalleryForImageSelection() {
        galleryLauncher.launch("image/*");
    }
}
