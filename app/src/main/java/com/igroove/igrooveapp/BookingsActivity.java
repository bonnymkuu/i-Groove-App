package com.igroove.igrooveapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.adapters.SoundAdapter;
import com.igroove.igrooveapp.adapters.VenueAdapter;
import com.igroove.igrooveapp.model.Sound;
import com.igroove.igrooveapp.model.Venue;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class BookingsActivity extends AppCompatActivity {
    private EditText addressInput;
    private String artistId;
    private ImageView artistImage;
    private TextView artistName;
    private TextView bookingsDeposit;
    TextView bookingsDistance;
    private TextView bookingsTime;
    private TextView bookingsTransport;
    private String currentUserId;
    private Button dateButton;
    ImageView dateImageView1;
    ImageView dateImageView2;
    private String deposit;
    private TextView depositAmount;
    private EditText durationInput;
    private String price;
    private ProgressBar progressBar;
    private Calendar selectedDate;
    private Calendar selectedTime;
    private SoundAdapter soundAdapter;
    private List<Sound> soundList;
    RecyclerView soundRecyclerview;
    private Button submitButton;
    private Button timeButton;
    ImageView timeImageView1;
    ImageView timeImageView2;
    private TextView totalAmount;
    private String transport;
    private TextView transportAmount;
    private VenueAdapter venueAdapter;
    private List<Venue> venueList;
    RecyclerView venueRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        // Get current user ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }

        // Get artistId from intent and load booking details
        artistId = getIntent().getStringExtra("artistId");
        if (artistId != null) {
            loadBookingDetails(artistId);
            checkUserPositionAndUpdateUi(artistId);
        }

        // Initialize UI elements
        artistName = findViewById(R.id.artistName);
        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        artistImage = findViewById(R.id.artistImage);
        progressBar = findViewById(R.id.progressBar);
        totalAmount = findViewById(R.id.totalAmount);
        submitButton = findViewById(R.id.submitButton);
        bookingsTime = findViewById(R.id.bookingsTime);
        addressInput = findViewById(R.id.addressInput);
        durationInput = findViewById(R.id.durationInput);
        depositAmount = findViewById(R.id.depositAmount);
        dateImageView1 = findViewById(R.id.dateImageView1);
        dateImageView2 = findViewById(R.id.dateImageView2);
        timeImageView1 = findViewById(R.id.timeImageView1);
        timeImageView2 = findViewById(R.id.timeImageView2);
        transportAmount = findViewById(R.id.transportAmount);
        bookingsDeposit = findViewById(R.id.bookingsDeposit);
        bookingsDistance = findViewById(R.id.bookingsDistance);
        soundRecyclerview = findViewById(R.id.soundRecyclerview);
        venueRecyclerview = findViewById(R.id.venueRecyclerview);
        bookingsTransport = findViewById(R.id.bookingsTransport);

        selectedDate = Calendar.getInstance();
        selectedTime = Calendar.getInstance();

        // Set click listeners
        dateButton.setOnClickListener(v -> showDatePicker());
        dateImageView1.setOnClickListener(v -> showDatePicker());
        dateImageView2.setOnClickListener(v -> showDatePicker());
        timeButton.setOnClickListener(v -> showTimePicker());
        timeImageView1.setOnClickListener(v -> showTimePicker());
        timeImageView2.setOnClickListener(v -> showTimePicker());
        submitButton.setOnClickListener(v -> handleSubmit());

        // Venue list and adapter
        venueList = new ArrayList<>();
        venueAdapter = new VenueAdapter(this, venueList);
        venueRecyclerview.setAdapter(venueAdapter);
        venueRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadVenuesFromDatabase();

        // Sound list and adapter
        soundList = new ArrayList<>();
        soundAdapter = new SoundAdapter(this, soundList);
        soundRecyclerview.setAdapter(soundAdapter);
        soundRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadSoundsFromDatabase();
    }

    private void loadBookingDetails(String artistId) throws DatabaseException {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(artistId)
                .child("Bookings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot bookingSnapshot : dataSnapshot.getChildren()) {
                        price = bookingSnapshot.child("price").getValue(String.class);
                        transport = bookingSnapshot.child("transport").getValue(String.class);
                        deposit = bookingSnapshot.child("deposit").getValue(String.class);

                        bookingsTime.setText(price != null ? "Per Hour: R" + price : "Per Hour : R0");
                        bookingsTransport.setText(transport != null ? "Transport: R" + transport + " per km" : "Transport: R0 per km");
                        bookingsDeposit.setText(deposit != null ? " - " + deposit + "% Deposit" : " - 0% Deposit");
                    }
                } else {
                    Toast.makeText(BookingsActivity.this, "No booking details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookingsActivity.this, "Failed to load booking details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadVenuesFromDatabase() throws DatabaseException {
        DatabaseReference venueRef = FirebaseDatabase.getInstance().getReference("Venues");

        venueRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                venueList.clear();
                for (DataSnapshot venueSnapshot : snapshot.getChildren()) {
                    Venue venue = venueSnapshot.getValue(Venue.class);
                    if (venue != null) {
                        venueList.add(venue);
                    }
                }
                venueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingsActivity.this, "Failed to load venues.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSoundsFromDatabase() throws DatabaseException {
        DatabaseReference soundRef = FirebaseDatabase.getInstance().getReference("Sounds");

        soundRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soundList.clear();
                for (DataSnapshot soundSnapshot : snapshot.getChildren()) {
                    Sound sound = soundSnapshot.getValue(Sound.class);
                    if (sound != null) {
                        soundList.add(sound);
                    }
                }

                soundAdapter.notifyDataSetChanged();

                soundRecyclerview.setVisibility(soundList.isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingsActivity.this, "Failed to load sounds.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserPositionAndUpdateUi(String userId) throws DatabaseException {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String username = snapshot.child("name").getValue(String.class);
                    String profile = snapshot.child("image").getValue(String.class);

                    artistName.setText(username);
                    if (profile != null) {
                        Glide.with(BookingsActivity.this)
                                .load(profile)
                                .placeholder(R.drawable.ic_user)
                                .error(R.drawable.ic_user)
                                .into(artistImage);
                    } else {
                        artistImage.setImageResource(R.drawable.ic_user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingsActivity.this, "error loading your details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addsToHisNotification(
            String artistId,
            Calendar selectedTime,
            Calendar selectedDate,
            EditText addressInput
    ) throws DatabaseException {

        progressBar.setVisibility(View.VISIBLE);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String address = addressInput.getText().toString().trim();

        if (TextUtils.isEmpty(address)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "please enter address", Toast.LENGTH_SHORT).show();
            return;
        }

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(selectedDate.getTime());
        String formattedTime = new SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(selectedTime.getTime());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("timestamp", ServerValue.TIMESTAMP);
        hashMap.put("time", formattedTime);
        hashMap.put("date", formattedDate);
        hashMap.put("address", address);
        hashMap.put("artistId", artistId);
        hashMap.put("senderId", currentUserId);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.child(artistId)
                .child("Notifications")
                .child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(unused -> {
                    progressBar.setVisibility(View.GONE);
                    submitButton.setText(R.string.submit);
                    Toast.makeText(this, "Artist booked successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    submitButton.setText(R.string.submit);
                    Toast.makeText(this, "Failed to book the artist: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    selectedDate.set(Calendar.YEAR, year);
                    selectedDate.set(Calendar.MONTH, month);
                    selectedDate.set(Calendar.DAY_OF_MONTH, day);
                    dateButton.setText(day + "/" + (month + 1) + "/" + year);
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedTime.set(Calendar.MINUTE, minute);
                    timeButton.setText(MessageFormat.format("{0}:{1}", hourOfDay, minute));
                },
                selectedTime.get(Calendar.HOUR_OF_DAY),
                selectedTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void handleSubmit() throws NumberFormatException, DatabaseException {
        String address = addressInput.getText().toString();
        String durationStr = durationInput.getText().toString();

        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(durationStr)) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "please enter address", Toast.LENGTH_SHORT).show();
            return;
        }

        int duration = Integer.parseInt(durationStr);
        int depositValue = Integer.parseInt(price);
        int transportFee = Integer.parseInt(transport);
        int total = (depositValue * duration) + transportFee;

        depositAmount.setText("R" + depositValue);
        transportAmount.setText("R" + transportFee);
        totalAmount.setText("R" + total);

        submitButton.setText(R.string.uploading);

        addsToHisNotification(artistId, selectedTime, selectedDate, addressInput);
    }
}
