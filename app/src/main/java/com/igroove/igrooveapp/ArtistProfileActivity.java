package com.igroove.igrooveapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.adapters.BookingsAdapter;
import com.igroove.igrooveapp.adapters.ImageSliderAdapter;
import com.igroove.igrooveapp.adapters.ProfileSongsAdapter;
import com.igroove.igrooveapp.adapters.RecommendedAdapter;
import com.igroove.igrooveapp.adapters.SoundAdapter;
import com.igroove.igrooveapp.adapters.VenueAdapter;
import com.igroove.igrooveapp.model.Bookings;
import com.igroove.igrooveapp.model.OnlineAudioModel;
import com.igroove.igrooveapp.model.RecommendedItems;
import com.igroove.igrooveapp.model.Sound;
import com.igroove.igrooveapp.model.Venue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ArtistProfileActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 101;

    // UI Elements
    private TextView bioTextView;
    private TextView depositEditText;
    private TextView priceEditText;
    private TextView transportEditText;
    private TextView usernameTextView;
    private ImageView profileImageView;
    private ProgressBar progressBar;
    private Button editButton;
    private RecyclerView noticeRecyclerView;
    private BookingsAdapter bookingsAdapter;
    private RecyclerView songsRecyclerView;
    private ProfileSongsAdapter songsAdapter;
    private RecyclerView venueRecyclerview;
    private VenueAdapter venueAdapter;
    private RecyclerView soundRecyclerview;
    private SoundAdapter soundAdapter;
    private RecyclerView recommendedRecyclerView;
    private RecommendedAdapter recommendedAdapter;

    // ViewPager2
    private ViewPager2 upcomingEventsSlider;

    // TextViews for titles based on user position
    private TextView titleText1;
    private TextView titleText2;
    private TextView titleText3;

    // Data lists
    private final List<OnlineAudioModel> songList = new ArrayList<>();
    private final List<Bookings> bookingsList = new ArrayList<>();
    private final List<RecommendedItems> recommendedList = new ArrayList<>();
    private List<Venue> venueList;
    private List<Sound> soundList;

    // Firebase related variables
    private String currentUserId;
    private String userName;
    private String profile; // Profile image URL
    private String eventId; // Currently loaded event ID

    @Override
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_profile);

        // Initialize UI elements
        progressBar = findViewById(R.id.progressBar);
        bioTextView = findViewById(R.id.bioTextView);
        priceEditText = findViewById(R.id.priceEditText);
        depositEditText = findViewById(R.id.depositEditText);
        usernameTextView = findViewById(R.id.usernameTextView);
        profileImageView = findViewById(R.id.profileImageView);
        songsRecyclerView = findViewById(R.id.songsRecyclerView);
        transportEditText = findViewById(R.id.transportEditText);
        noticeRecyclerView = findViewById(R.id.noticeRecyclerView);
        upcomingEventsSlider = findViewById(R.id.UpcomingEventsSlider);
        recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);
        venueRecyclerview = findViewById(R.id.venueRecyclerview);
        soundRecyclerview = findViewById(R.id.soundRecyclerview);
        titleText1 = findViewById(R.id.titleText1);
        titleText2 = findViewById(R.id.titleText2);
        titleText3 = findViewById(R.id.titleText3);
        editButton = findViewById(R.id.editButton);

        // Set up Listeners
        bioTextView.setOnClickListener(v -> showBioEditDialog());

        // Set up RecyclerViews and Adapters
        noticeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookingsAdapter = new BookingsAdapter(this, bookingsList);
        noticeRecyclerView.setAdapter(bookingsAdapter);

        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        songsAdapter = new ProfileSongsAdapter(songList);
        songsRecyclerView.setAdapter(songsAdapter);

        venueList = new ArrayList<>();
        venueAdapter = new VenueAdapter(this, venueList);
        venueRecyclerview.setAdapter(venueAdapter);
        venueRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        soundList = new ArrayList<>();
        soundAdapter = new SoundAdapter(this, soundList);
        soundRecyclerview.setAdapter(soundAdapter);
        soundRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        recommendedRecyclerView.setAdapter(recommendedAdapter);

        // Firebase User check and data loading
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
            loadProfileData();
            loadBookingDetails();
            loadNotification();
            loadUserSongs();
            fetchEventImages();
            fetchRecommendedItemsFromFirebase();
            checkUserPositionAndUpdateUi(currentUserId);
        }

        // Start Notification Service
        startService(new Intent(this, NotificationService.class));
        checkAndRequestNotificationPermission();
    }

    private void loadProfileData() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    userName = dataSnapshot.child("name").getValue(String.class);
                    profile = dataSnapshot.child("image").getValue(String.class);
                    String bio = dataSnapshot.child("bio").getValue(String.class);

                    usernameTextView.setText(userName);
                    Glide.with(ArtistProfileActivity.this)
                            .load(profile)
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(profileImageView);

                    if (bioTextView != null) {
                        bioTextView.setText(bio != null ? bio : "Hey there music is my passion");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ArtistProfileActivity.this, "Error loading profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBookingDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId).child("Bookings");
        Log.d("FirebaseDebug", "Querying Path: " + ref);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("FirebaseDebug", "Snapshot Exists: " + dataSnapshot.exists());
                Log.d("FirebaseDebug", "Full Snapshot: " + dataSnapshot);

                if (dataSnapshot.exists()) {
                    // Assuming there's only one booking entry or we take the first one
                    DataSnapshot bookingSnapshot = dataSnapshot.getChildren().iterator().next();
                    Log.d("FirebaseDebug", "Booking Snapshot: " + bookingSnapshot.toString());

                    final String price = bookingSnapshot.child("price").getValue(String.class);
                    final String transport = bookingSnapshot.child(NotificationCompat.CATEGORY_TRANSPORT).getValue(String.class);
                    final String deposit = bookingSnapshot.child("deposit").getValue(String.class);

                    if (price != null) {
                        priceEditText.setText(String.format("Per Hour: R%s", price));
                    } else {
                        priceEditText.setText("Per Hour : R0");
                    }

                    if (transport != null) {
                        transportEditText.setText(String.format("Transport: R%s per km", transport));
                    } else {
                        transportEditText.setText("Transport: R0 per km");
                    }

                    if (deposit != null) {
                        depositEditText.setText(String.format(" - %s%% Deposit", deposit));
                    } else {
                        depositEditText.setText(" - 0% Deposit");
                    }

                    editButton.setOnClickListener(v -> showEditDialog(price, transport, deposit));
                } else {
                    Toast.makeText(ArtistProfileActivity.this, "No booking details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to load booking details", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseDebug", "Error: " + databaseError.getMessage());
            }
        });
    }

    private void showEditDialog(String price, String transport, String deposit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_details, null);
        builder.setView(dialogView);

        final EditText editPrice = dialogView.findViewById(R.id.editPrice);
        final EditText editTransport = dialogView.findViewById(R.id.editTransport);
        final EditText editDeposit = dialogView.findViewById(R.id.editDeposit);

        editPrice.setText(price != null ? price : "0");
        editTransport.setText(transport != null ? transport : "0");
        editDeposit.setText(deposit != null ? deposit : "0");

        builder.setTitle("Edit Booking Details")
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedPrice = editPrice.getText().toString().trim();
                    String updatedTransport = editTransport.getText().toString().trim();
                    String updatedDeposit = editDeposit.getText().toString().trim();

                    if (updatedPrice.isEmpty()) {
                        updatedPrice = "0";
                    }
                    if (updatedTransport.isEmpty()) {
                        updatedTransport = "0";
                    }
                    if (updatedDeposit.isEmpty()) {
                        updatedDeposit = "0";
                    }
                    updateBooking(updatedPrice, updatedTransport, updatedDeposit);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateBooking(String price, String transport, String deposit) {
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Price field cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(transport)) {
            Toast.makeText(this, "Transport field cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(deposit)) {
            Toast.makeText(this, "Deposit field cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId).child("Bookings");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bookingId = null;
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                if (it.hasNext()) {
                    DataSnapshot bookingSnapshot = it.next();
                    bookingId = bookingSnapshot.getKey();
                }

                if (bookingId == null) {
                    // If no existing booking, generate a new key
                    bookingId = ref.push().getKey();
                }

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("price", price);
                hashMap.put(NotificationCompat.CATEGORY_TRANSPORT, transport);
                hashMap.put("deposit", deposit);

                if (bookingId != null) {
                    ref.child(bookingId).updateChildren(hashMap)
                            .addOnSuccessListener(unused -> {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ArtistProfileActivity.this, "Booking details updated successfully.", Toast.LENGTH_SHORT).show();
                                loadBookingDetails();
                            })
                            .addOnFailureListener(e -> {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ArtistProfileActivity.this, "Failed to update booking details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ArtistProfileActivity.this, "Failed to get booking ID.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ArtistProfileActivity.this, "Failed to check existing bookings: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkUserPositionAndUpdateUi(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) throws DatabaseException {
                if (snapshot.exists()) {
                    String position = snapshot.child("position").getValue(String.class);
                    String username = snapshot.child("name").getValue(String.class);
                    usernameTextView.setText(username);

                    if (ExifInterface.TAG_ARTIST.equals(position) || "admin".equals(position)) {
                        titleText1.setVisibility(View.VISIBLE);
                        titleText2.setVisibility(View.GONE);
                        titleText3.setVisibility(View.GONE);
                    } else if ("AdvertiseSong".equals(position)) {
                        titleText1.setVisibility(View.GONE);
                        titleText2.setVisibility(View.GONE);
                        titleText3.setVisibility(View.VISIBLE);
                        loadSoundsFromDatabase();
                    } else if ("AdvertiseVenue".equals(position)) {
                        titleText1.setVisibility(View.GONE);
                        titleText2.setVisibility(View.VISIBLE);
                        titleText3.setVisibility(View.GONE);
                        loadVenuesFromDatabase();
                    } else {
                        // Default case or other positions
                        titleText1.setVisibility(View.GONE);
                        titleText2.setVisibility(View.GONE);
                        titleText3.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistProfileActivity.this, "Error loading your details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showBioEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Bio");

        final EditText bioEditText = new EditText(this);
        bioEditText.setHint("Enter your bio here");
        bioEditText.setText(bioTextView.getText());
        builder.setView(bioEditText);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newBio = bioEditText.getText().toString().trim();
            if (!TextUtils.isEmpty(newBio)) {
                updateBioInFirebase(newBio);
            } else {
                Toast.makeText(this, "Bio cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateBioInFirebase(final String newBio) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        userRef.child("bio").setValue(newBio)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        bioTextView.setText(newBio);
                        Toast.makeText(ArtistProfileActivity.this, "Bio updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ArtistProfileActivity.this, "Failed to update bio", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadNotification() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId).child("Notifications");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bookings bookings = snapshot.getValue(Bookings.class);
                    if (bookings != null) {
                        bookingsList.add(bookings);
                        sendNotification("New Event: " + bookings.getLocation(), "Date: " + bookings.getDate() + " , Time: " + bookings.getTime());
                    }
                }
                if (bookingsList.isEmpty()) {
                    noticeRecyclerView.setVisibility(View.GONE);
                } else {
                    noticeRecyclerView.setVisibility(View.VISIBLE);
                }
                bookingsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to retrieve notifications " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserSongs() throws DatabaseException {
        DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference("audios");
        songsRef.orderByChild("userId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                songList.clear();
                for (DataSnapshot songSnapshot : dataSnapshot.getChildren()) {
                    String songTitle = songSnapshot.child("title").getValue(String.class);
                    String songUrl = songSnapshot.child("audioUrl").getValue(String.class);
                    long downloads = 0;
                    if (songSnapshot.child("downloads").exists()) {
                        downloads = songSnapshot.child("downloads").getChildrenCount();
                    }
                    songList.add(new OnlineAudioModel(songTitle, userName, profile, songUrl, downloads));
                }
                if (songList.isEmpty()) {
                    songsRecyclerView.setVisibility(View.GONE);
                } else {
                    songsRecyclerView.setVisibility(View.VISIBLE);
                }
                songsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ArtistProfileActivity.this, "Error loading songs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
        }
    }

    private void sendNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "event_channel")
                .setSmallIcon(R.drawable.app_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == 0) {
            notificationManager.notify(1, builder.build());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchEventImages() throws DatabaseException {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.orderByChild("uId").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> imageUrls = new ArrayList<>();
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    String imageUrl = eventSnapshot.child("imageUrl").getValue(String.class);
                    eventId = eventSnapshot.child("eventId").getValue(String.class);

                    if (imageUrl != null) {
                        imageUrls.add(imageUrl);
                    }
                }
                ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(ArtistProfileActivity.this, imageUrls);
                upcomingEventsSlider.setAdapter(sliderAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to load events:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Placeholder for loadVenuesFromDatabase and loadSoundsFromDatabase
    private void loadVenuesFromDatabase() {
        DatabaseReference venuesRef = FirebaseDatabase.getInstance().getReference("venues");
        venuesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                venueList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Venue venue = dataSnapshot.getValue(Venue.class);
                    if (venue != null) {
                        venueList.add(venue);
                    }
                }
                if (venueList.isEmpty()) {
                    venueRecyclerview.setVisibility(View.GONE);
                } else {
                    venueRecyclerview.setVisibility(View.VISIBLE);
                }
                venueAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to load venues: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSoundsFromDatabase() {
        DatabaseReference soundsRef = FirebaseDatabase.getInstance().getReference("sounds");
        soundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soundList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Sound sound = dataSnapshot.getValue(Sound.class);
                    if (sound != null) {
                        soundList.add(sound);
                    }
                }
                if (soundList.isEmpty()) {
                    soundRecyclerview.setVisibility(View.GONE);
                } else {
                    soundRecyclerview.setVisibility(View.VISIBLE);
                }
                soundAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to load sounds: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchRecommendedItemsFromFirebase() {
        DatabaseReference recommendedRef = FirebaseDatabase.getInstance().getReference("recommended_items");
        recommendedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recommendedList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecommendedItems item = dataSnapshot.getValue(RecommendedItems.class);
                    if (item != null) {
                        recommendedList.add(item);
                    }
                }
                if (recommendedList.isEmpty()) {
                    recommendedRecyclerView.setVisibility(View.GONE);
                } else {
                    recommendedRecyclerView.setVisibility(View.VISIBLE);
                }
                recommendedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to load recommended items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showEventDetailsDialog() {
        String eventIdToShow = this.eventId;

        if (eventIdToShow == null || eventIdToShow.isEmpty()) {
            Toast.makeText(this, "No event selected or invalid event ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("EventDetails", "Loading event details for ID: " + eventIdToShow);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_event); // This must be the XML you provided
        dialog.setCancelable(true);

        // Initialize views from your given XML
        EditText titleEditText = dialog.findViewById(R.id.eventTitleEditText);
        EditText locationEditText = dialog.findViewById(R.id.eventLocationEditText);
        EditText contactEditText = dialog.findViewById(R.id.eventContactEditText);
        Button dateButton = dialog.findViewById(R.id.eventDateButton);
        TextView dateTextView = dialog.findViewById(R.id.eventDateTextView);
        ImageView imageView = dialog.findViewById(R.id.eventImageView);
        Button uploadButton = dialog.findViewById(R.id.uploadEventButton);

        // Disable editing and hide unnecessary buttons
        titleEditText.setEnabled(false);
        locationEditText.setEnabled(false);
        contactEditText.setEnabled(false);
        dateButton.setVisibility(View.GONE);
        uploadButton.setText("Close"); // Repurpose as close button

        // Close dialog on button click
        uploadButton.setOnClickListener(v -> dialog.dismiss());

        // Load data from Firebase
        DatabaseReference eventRef = FirebaseDatabase.getInstance()
                .getReference("events")
                .child(eventIdToShow);

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String title = snapshot.child("title").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class); // Ensure this exists
                    String date = snapshot.child("date").getValue(String.class);
                    String imageUrl = snapshot.child("imageUrl").getValue(String.class);

                    titleEditText.setText(title != null ? title : "");
                    locationEditText.setText(location != null ? location : "");
                    contactEditText.setText(contact != null ? contact : "");
                    dateTextView.setText(date != null ? date : "No date selected");

                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Glide.with(ArtistProfileActivity.this)
                                .load(imageUrl)
                                .placeholder(R.drawable.baseline_camera_24)
                                .error(R.drawable.baseline_camera_24)
                                .into(imageView);
                    } else {
                        imageView.setImageResource(R.drawable.baseline_camera_24);
                    }

                    dialog.show();
                } else {
                    Toast.makeText(ArtistProfileActivity.this, "Event not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistProfileActivity.this, "Failed to load event: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}