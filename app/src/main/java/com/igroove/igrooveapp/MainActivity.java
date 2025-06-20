package com.igroove.igrooveapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.adapters.ArtistAdapter;
import com.igroove.igrooveapp.adapters.ImageSliderAdapter;
import com.igroove.igrooveapp.adapters.RecommendedAdapter;
import com.igroove.igrooveapp.adapters.SongAdapter;
import com.igroove.igrooveapp.model.Artist;
import com.igroove.igrooveapp.model.RecommendedItems;
import com.igroove.igrooveapp.model.Songs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 2;
    private ImageView accountBalance;
    private ImageView addActivity;
    private ArtistAdapter artistAdapter;
    RecyclerView artistsRecyclerView;
    String currentUserId;
    private ImageView genresImageView;
    private ImageView logoutIcon;
    private ImageView othersActivity;
    private RecommendedAdapter recommendedAdapter;
    RecyclerView recommendedRecyclerView;
    private ImageView searchIcon;
    private SongAdapter songsAdapter;
    RecyclerView songsRecyclerView;
    private ImageView soundActivity;
    ViewPager2 upcomingEventsSlider;
    private ImageView userPic;
    TextView usernameTextView;
    private ImageView venueActivity;
    private final List<Songs> songsList = new ArrayList();
    private final List<Artist> artistsList = new ArrayList();
    private final List<RecommendedItems> recommendedList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userPic = findViewById(R.id.userPic);
        logoutIcon = findViewById(R.id.logoutIcon);
        searchIcon = findViewById(R.id.searchIcon);
        addActivity = findViewById(R.id.addActivity);
        venueActivity = findViewById(R.id.venueActivity);
        soundActivity = findViewById(R.id.soundActivity);
        othersActivity = findViewById(R.id.othersActivity);
        accountBalance = findViewById(R.id.account_balance);
        genresImageView = findViewById(R.id.genresImageView);
        usernameTextView = findViewById(R.id.usernameTextView);
        songsRecyclerView = findViewById(R.id.songsRecyclerView);
        artistsRecyclerView = findViewById(R.id.artistsRecyclerView);
        upcomingEventsSlider = findViewById(R.id.UpcomingEventsSlider);
        recommendedRecyclerView = findViewById(R.id.recommendedRecyclerView);

        setupRecyclerViews();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
            checkUserPositionAndUpdateUi(currentUserId);
        } else {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }

        loadFirebaseDataWithDelay();
        setupOnClickListener();
    }

    private void checkUserPositionAndUpdateUi(String userId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String position = snapshot.child("position").getValue(String.class);
                    String username = snapshot.child("name").getValue(String.class);
                    usernameTextView.setText(username);

                    if ("ARTIST".equals(position) || "admin".equals(position)) {
                        addActivity.setVisibility(View.VISIBLE);
                        userPic.setVisibility(View.VISIBLE);
                    } else if ("AdvertiseSong".equals(position)) {
                        venueActivity.setVisibility(View.VISIBLE);
                        addActivity.setVisibility(View.GONE);
                    } else if ("AdvertiseVenue".equals(position)) {
                        soundActivity.setVisibility(View.VISIBLE);
                        addActivity.setVisibility(View.GONE);
                    } else {
                        addActivity.setVisibility(View.GONE);
                        userPic.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error loading your details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFirebaseDataWithDelay() {
        Handler handler = new Handler();

        handler.postDelayed(() -> fetchEventImages(), 300);
        handler.postDelayed(() -> fetchSongsFromFirebase(), 10000);
        handler.postDelayed(() -> fetchArtistsFromFirebase(), 15000);
        handler.postDelayed(() -> fetchRecommendedItemsFromFirebase(), 20000);
    }

    private void setupOnClickListener() {
        searchIcon.setOnClickListener(view -> onSearchClicked(view));
        logoutIcon.setOnClickListener(view -> onLogoutClicked(view));
        userPic.setOnClickListener(view -> onUserPicClicked(view));
        genresImageView.setOnClickListener(view -> onGenresClicked(view));
        accountBalance.setOnClickListener(view -> onAccountBalanceClicked(view));
        venueActivity.setOnClickListener(view -> onVenueActivityClicked(view));
        soundActivity.setOnClickListener(view -> onSoundActivityClicked(view));
        othersActivity.setOnClickListener(view -> onOthersActivityClicked(view));
        addActivity.setOnClickListener(view -> onAddActivityClicked(view));
    }

    private void onSearchClicked(View v) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    private void onLogoutClicked(View v) {
        logout();
    }

    private void onUserPicClicked(View v) {
        startActivity(new Intent(this, ArtistProfileActivity.class));
    }

    private void onGenresClicked(View v) {
        startActivity(new Intent(this, GenresActivity.class));
    }

    private void onAccountBalanceClicked(View v) {
        startActivity(new Intent(this, PaymentsActivity.class));
    }

    private void onVenueActivityClicked(View v) {
        startActivity(new Intent(this, RentVenueActivity.class));
    }

    private void onSoundActivityClicked(View v) {
        startActivity(new Intent(this, RentSoundActivity.class));
    }

    private void onOthersActivityClicked(View v) {
        startActivity(new Intent(this, MusicListActivity.class));
    }

    private void onAddActivityClicked(View v) {
        startActivity(new Intent(this, ArtistUploadActivity.class));
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out");
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
        builder.setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }


    private void onLogoutConfirmed(DialogInterface dialog, int which) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void fetchEventImages() throws DatabaseException {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<String> imageUrls = new ArrayList<>();
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    String imageUrl = eventSnapshot.child("imageUrl").getValue(String.class);
                    if (imageUrl != null) {
                        imageUrls.add(imageUrl);
                    }
                }
                ImageSliderAdapter sliderAdapter = new ImageSliderAdapter(MainActivity.this, imageUrls);
                upcomingEventsSlider.setAdapter(sliderAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load events:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showEventDetailsDialog(String eventId) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.event_details);
        TextView eventTitle = dialog.findViewById(R.id.eventTitle);
        TextView eventDate = dialog.findViewById(R.id.eventDate);
        TextView eventLocation = dialog.findViewById(R.id.eventLocation);
        TextView eventContact = dialog.findViewById(R.id.eventContact);
        Button buyTicketButton = dialog.findViewById(R.id.buyTicketButton);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String evTitle = snapshot.child("title").getValue(String.class);
                    String evDate = snapshot.child("date").getValue(String.class);
                    String evLocation = snapshot.child("location").getValue(String.class);
                    String evContact = snapshot.child("contact").getValue(String.class);
                    eventTitle.setText(evTitle);
                    eventDate.setText(evDate);
                    eventLocation.setText(evLocation);
                    eventContact.setText(evContact);

                    buyTicketButton.setOnClickListener(v -> {
                        try {
                            downloadTicket(evTitle, evDate, evLocation, evContact);
                            startActivity(new Intent(MainActivity.this, PaymentsActivity.class));
                        } catch (IOException e) {
                            Toast.makeText(MainActivity.this, "Error downloading ticket", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Event not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load event: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.horizontalMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 20.0f, getResources().getDisplayMetrics()
            );
            window.setAttributes(layoutParams);
        }
    }

    private void downloadTicket(String eventTitle, String eventDate, String eventLocation, String eventContact) throws IOException {
        Bitmap ticketBitmap = Bitmap.createBitmap(800, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ticketBitmap);
        Paint paint = new Paint();

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 800, 400, paint);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRect(10, 10, 790, 390, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        canvas.drawText("Event: " + eventTitle, 30, 80, paint);
        canvas.drawText("Date: " + eventDate, 30, 150, paint);
        canvas.drawText("Location: " + eventLocation, 30, 220, paint);
        canvas.drawText("Contact: " + eventContact, 30, 290, paint);

        paint.setColor(Color.LTGRAY);
        canvas.drawRect(600, 50, 770, 220, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        canvas.drawText("Event Image", 620, 140, paint);

        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File ticketFile = new File(downloadsDir, eventTitle + "_" + System.currentTimeMillis() + ".png");

        try (FileOutputStream fileOutputStream = new FileOutputStream(ticketFile)) {
            ticketBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            Toast.makeText(this, "Ticket downloaded successfully!", Toast.LENGTH_SHORT).show();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(ticketFile));
            sendBroadcast(mediaScanIntent);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to save ticket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerViews() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        songsRecyclerView.setLayoutManager(layoutManager);
        SongAdapter songAdapter = new SongAdapter(this, songsList);
        songsAdapter = songAdapter;
        songsRecyclerView.setAdapter(songAdapter);

        artistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArtistAdapter artistAdapter = new ArtistAdapter(this, artistsList);
        this.artistAdapter = artistAdapter;
        artistsRecyclerView.setAdapter(artistAdapter);

        recommendedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecommendedAdapter recommendedAdapter = new RecommendedAdapter(this, recommendedList);
        this.recommendedAdapter = recommendedAdapter;
        recommendedRecyclerView.setAdapter(recommendedAdapter);
    }

    private void fetchSongsFromFirebase() throws DatabaseException {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("audios");
        databaseReference.limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                songsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Songs song = dataSnapshot.getValue(Songs.class);
                    if (song != null) {
                        Log.d("fetchSongsFromFirebase", "Fetched song: " + song.getTitle() + ", Artist: " + song.getArtistName() + ", profile: " + song.getProfileUrl());
                        songsList.add(song);
                    }
                }
                songsAdapter.notifyItemInserted(songsList.size() - 1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load songs: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchArtistsFromFirebase() throws DatabaseException {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                artistsList.clear();
                int count = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String position = dataSnapshot.child("position").getValue(String.class);
                    if ("artist".equals(position) && name != null) {
                        Artist user = new Artist(name, image, id);
                        artistsList.add(user);
                        count++;
                        if (count >= 10) break;
                    }
                }
                artistAdapter.notifyItemInserted(artistsList.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void fetchRecommendedItemsFromFirebase() throws DatabaseException {
        DatabaseReference recommendedRef = FirebaseDatabase.getInstance().getReference("Users");
        recommendedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recommendedList.clear();
                int count = 0;
                for (DataSnapshot recommendedSnapshot : dataSnapshot.getChildren()) {
                    String name = recommendedSnapshot.child("name").getValue(String.class);
                    String imageUrl = recommendedSnapshot.child("image").getValue(String.class);
                    String position = recommendedSnapshot.child("position").getValue(String.class);
                    if (name != null && "artist".equals(position)) {
                        RecommendedItems recommendedItem = new RecommendedItems(name, imageUrl);
                        recommendedList.add(recommendedItem);
                        count++;
                        if (count >= 10) break;
                    }
                }
                recommendedAdapter.notifyItemInserted(recommendedList.size() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load recommended items: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
