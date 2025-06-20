package com.igroove.igrooveapp;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class RentSoundActivity extends AppCompatActivity {

    private ImageView profileImage, playPauseButton;
    private SeekBar audioSeekBar;
    private EditText priceEditText;
    private ProgressBar progressBar;
    Button selectAudioButton, submitAudioButton;
    private TextView audioNameTextView;
    private LinearLayout audioPlayerLayout;
    private Uri audioUri;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    private FirebaseStorage storage;
    private StorageReference audioStorageRef;
    private DatabaseReference databaseRef;
    private String currentUserId, profileImageUrl;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_sound);

        // Initialize views
        profileImage = findViewById(R.id.profileImage);
        audioSeekBar = findViewById(R.id.audioSeekBar);
        priceEditText = findViewById(R.id.priceEditText);
        progressBar = findViewById(R.id.uploadProgressBar);
        playPauseButton = findViewById(R.id.playPauseButton);
        selectAudioButton = findViewById(R.id.selectAudioButton);
        submitAudioButton = findViewById(R.id.submitAudioButton);
        audioNameTextView = findViewById(R.id.audioNameTextView);
        audioPlayerLayout = findViewById(R.id.audioPlayerLayout);

        audioStorageRef = FirebaseStorage.getInstance().getReference().child("AudioFiles");
        databaseRef = FirebaseDatabase.getInstance().getReference("AudioRentals");

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }

        fetchProfileUrl();

        playPauseButton.setOnClickListener(this::handlePlayPauseClick);
        selectAudioButton.setOnClickListener(view -> openAudioFilePicker());
        submitAudioButton.setOnClickListener(view -> uploadAudioToFirebase());
    }

    private void handlePlayPauseClick(View v) {
        if (isPlaying) {
            pauseAudio();
        } else {
            playAudio();
        }
    }

    private void fetchProfileUrl() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    profileImageUrl = snapshot.child("image").getValue(String.class);
                    if (profileImageUrl != null) {
                        Glide.with(RentSoundActivity.this).load(profileImageUrl).into(profileImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void openAudioFilePicker() {
        Intent audioIntent = new Intent(Intent.ACTION_GET_CONTENT);
        audioIntent.setType("audio/*");
        startActivityForResult(audioIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            audioUri = data.getData();
            if (audioUri != null) {
                Toast.makeText(this, "Audio selected", Toast.LENGTH_SHORT).show();
                String audioFileName = getAudioFileName(audioUri);
                audioNameTextView.setText(audioFileName);
                audioPlayerLayout.setVisibility(View.VISIBLE);
                setupMediaPlayer(audioUri);
            } else {
                Toast.makeText(this, "Failed to load audio", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please select an audio file", Toast.LENGTH_SHORT).show();
        }
    }

    private String getAudioFileName(Uri audioUri) {
        String audioName = null;
        if ("content".equals(audioUri.getScheme())) {
            Cursor cursor = getContentResolver().query(audioUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                audioName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
        }

        if (audioName == null) {
            String path = audioUri.getPath();
            int lastSlash = path.lastIndexOf('/');
            audioName = (lastSlash != -1) ? path.substring(lastSlash + 1) : path;
        }

        return audioName;
    }

    private void setupMediaPlayer(Uri audioUri) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, audioUri);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(mp -> {
                playPauseButton.setEnabled(true);
                playPauseButton.setOnClickListener(this::handlePlayPauseClick);
            });

            mediaPlayer.setOnCompletionListener(mp -> resetAudioPlayer());

            updateSeekBar();

        } catch (IOException e) {
            Toast.makeText(this, "Error initializing player: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer == null) return;

        audioSeekBar.setMax(mediaPlayer.getDuration());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && isPlaying) {
                    audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;
            playPauseButton.setImageResource(R.drawable.baseline_pause_circle_24);
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            playPauseButton.setImageResource(R.drawable.baseline_play_circle_filled_24);
        }
    }

    private void resetAudioPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            playPauseButton.setImageResource(R.drawable.baseline_play_circle_filled_24);
        }
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
            playPauseButton.setImageResource(R.drawable.baseline_play_circle_filled_24);
            audioSeekBar.setProgress(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAudio();
    }

    private void uploadAudioToFirebase() {
        String price = priceEditText.getText().toString().trim();
        if (audioUri == null) {
            Toast.makeText(this, "Please select an audio file", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please enter a price", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        String audioName = sanitizeFileName(getAudioFileName(audioUri));
        String timestamp = String.valueOf(System.currentTimeMillis());
        String soundId = databaseRef.push().getKey();

        if (soundId == null) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Failed to generate sound ID", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference filePath = audioStorageRef.child("Sounds/" + audioName);
        filePath.putFile(audioUri)
                .addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    saveAudioToDatabase(soundId, uri.toString(), price, audioName, timestamp);
                }))
                .addOnFailureListener(this::onAudioUploadFailure);
    }

    private void saveAudioToDatabase(String soundId, String downloadUrl, String price, String audioName, String timestamp) {
        Map<String, Object> audioDetails = new HashMap<>();
        audioDetails.put("soundId", soundId);
        audioDetails.put("audioUrl", downloadUrl);
        audioDetails.put("price", price);
        audioDetails.put("audioName", audioName);
        audioDetails.put("userId", currentUserId);
        audioDetails.put("profileImageUrl", profileImageUrl);
        audioDetails.put("type", "venue");
        audioDetails.put("timestamp", ServerValue.TIMESTAMP);


        databaseRef.child(soundId).setValue(audioDetails)
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Audio uploaded successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to upload audio", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onAudioUploadFailure(Exception e) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("RentSoundActivity", "Upload failed", e);
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replace(".", "_").replace("#", "_").replace("$", "_").replace("[", "_").replace("]", "_");
    }
}
