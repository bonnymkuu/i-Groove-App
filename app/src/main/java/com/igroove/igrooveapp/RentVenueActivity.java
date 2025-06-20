package com.igroove.igrooveapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class RentVenueActivity extends AppCompatActivity {
    private String currentUserId;
    private String imageUrl;
    private EditText locationEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText nameEditText;
    private EditText priceEditText;
    Button submitButton;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_venue);
        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            this.currentUserId = currentUser.getUid();
        }
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.nameEditText = findViewById(R.id.nameEditText);
        this.submitButton = findViewById(R.id.submitButton);
        this.priceEditText = findViewById(R.id.priceEditText);
        this.locationEditText = findViewById(R.id.locationEditText);
        fetchProfileUrl();
        this.submitButton.setOnClickListener(this::onSubmitButtonClicked);
    }

    private void onSubmitButtonClicked(View v) {
        this.v = v;
        submitVenueDetails();
    }

    private void fetchProfileUrl() throws DatabaseException {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(this.currentUserId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    RentVenueActivity.this.imageUrl = snapshot.child("image").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error if needed
            }
        });
    }

    private void submitVenueDetails() {
        String price = this.priceEditText.getText().toString().trim();
        String location = this.locationEditText.getText().toString().trim();
        String venueName = this.nameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please enter a price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(venueName)) {
            Toast.makeText(this, "Please enter the venue Name", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Map<String, Object> venueDetails = new HashMap<>();
            venueDetails.put("price", price);
            venueDetails.put("location", location);
            venueDetails.put("venueName", venueName);
            venueDetails.put("userId", userId);
            venueDetails.put("imageUrl", this.imageUrl);
            venueDetails.put("type", "venue");

            String venueId = this.mDatabase.child("Venues").push().getKey();
            if (venueId != null) {
                venueDetails.put("venueId", venueId);
                this.mDatabase.child("Venues").child(venueId).setValue(venueDetails)
                        .addOnCompleteListener(this::onVenueUploadComplete);
            } else {
                Toast.makeText(this, "Failed to generate venue ID", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void onVenueUploadComplete(Task<Void> task) {
        if (task.isSuccessful()) {
            Toast.makeText(this, "Venue details uploaded successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Failed to upload venue details", Toast.LENGTH_SHORT).show();
        }
    }
}