package com.igroove.igrooveapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.Manifest;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ArtistUploadActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int REQUEST_CODE_AUDIO_PICK = 50;
    private static final int STORAGE_REQUEST_CODE = 200;
    Button addEventButton;
    TextView audioNameTextView;
    LinearLayout audioPlayerLayout;
    private SeekBar audioSeekBar;
    Button audioUploadButton;
    private Uri audioUri;
    String[] cameraPermission;
    String currentUserId, profile,selectedGenre;
    private ImageView eventImageView;
    Spinner genreSpinner;
    private Uri image_uri;
    private MediaPlayer mediaPlayer;
    private ImageView playPauseButton;
    StorageReference storageReference;
    EditText priceEditText;
    TextView songUploadTextView;
    String[] storagePermission;
    ProgressBar uploadProgressBar;
    private String username;
    private boolean isPlaying = false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_upload);

        // UI components
        songUploadTextView = findViewById(R.id.songUploadTextView);
        audioUploadButton = findViewById(R.id.audioUploadButton);
        uploadProgressBar = findViewById(R.id.uploadProgressBar);
        audioNameTextView = findViewById(R.id.audioNameTextView);
        audioPlayerLayout = findViewById(R.id.audioPlayerLayout);
        playPauseButton = findViewById(R.id.playPauseButton);
        eventImageView = findViewById(R.id.eventImageView);
        addEventButton = findViewById(R.id.addEventButton);
        priceEditText = findViewById(R.id.priceEditText);
        audioSeekBar = findViewById(R.id.audioSeekBar);
        genreSpinner = findViewById(R.id.genreSpinner);

        // Firebase references
        storageReference = FirebaseStorage.getInstance().getReference();
        currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        // Permissions
        cameraPermission = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        storagePermission = new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        // Spinner adapter setup
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.audio_genres,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        // Event Listeners
        songUploadTextView.setOnClickListener(v -> selectAudioFromDevice());
        playPauseButton.setOnClickListener(v -> togglePlayback());
        eventImageView.setOnClickListener(v -> handleImageSelection());
        audioUploadButton.setOnClickListener(v -> handleUploadButtonClick());
        addEventButton.setOnClickListener(v -> showEventDialog());
    }
    private void selectAudioFromDevice() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Audio"), REQUEST_CODE_AUDIO_PICK);
    }

    private void handleImageSelection() {
        if (checkStoragePermission()) {
            requestStoragePermission();
        } else {
            pickFromGallery();
        }
    }

    private void handleUploadButtonClick() {
        if (audioUri != null) {
            uploadAudioToFirebase(audioUri, priceEditText);
        } else {
            Toast.makeText(this, "No audio selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadAudioToFirebase(Uri audioUri, EditText priceEditText) {
        String originalAudioName = getAudioFileName(audioUri);
        String sanitizedAudioName = sanitizeFileName(originalAudioName);
        selectedGenre = genreSpinner.getSelectedItem().toString();
        String price = priceEditText.getText().toString().trim();

        // Fetch user info
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(currentUserId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    username = snapshot.child("name").getValue(String.class);
                    profile = snapshot.child("image").getValue(String.class);

                    startAudioUpload(originalAudioName, sanitizedAudioName, price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ArtistUploadActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAudioUpload(String originalAudioName, String sanitizedAudioName, String price) {
        String audioId = FirebaseDatabase.getInstance().getReference("audios").push().getKey();
        if (audioId == null) {
            Toast.makeText(this, "Failed to generate audio ID", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> audioDetails = new HashMap<>();
        audioDetails.put("title", originalAudioName);
        audioDetails.put("downloads", 0);
        audioDetails.put("genre", selectedGenre);
        audioDetails.put("price", price);
        audioDetails.put("userId", currentUserId);
        audioDetails.put("artistName", username);
        audioDetails.put("profileUrl", profile);
        audioDetails.put("audioId", audioId);

        StorageReference fileRef = storageReference.child("audios/" + sanitizedAudioName);
        uploadProgressBar.setVisibility(View.VISIBLE);

        fileRef.putFile(audioUri)
                .addOnProgressListener(snapshot -> {
                    double percent = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    uploadProgressBar.setIndeterminate(false);
                    uploadProgressBar.setProgress((int) percent);
                })
                .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    audioDetails.put("audioUrl", uri.toString());

                    FirebaseDatabase.getInstance()
                            .getReference("audios")
                            .child(audioId)
                            .setValue(audioDetails)
                            .addOnSuccessListener(aVoid -> {
                                uploadProgressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "Audio uploaded successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                uploadProgressBar.setVisibility(View.GONE);
                                Toast.makeText(this, "Failed to save audio details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }))
                .addOnFailureListener(e -> {
                    uploadProgressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to upload audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void showEventDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_event);

        EditText titleEditText = dialog.findViewById(R.id.eventTitleEditText);
        EditText locationEditText = dialog.findViewById(R.id.eventLocationEditText);
        EditText contactEditText = dialog.findViewById(R.id.eventContactEditText);
        TextView dateTextView = dialog.findViewById(R.id.eventDateTextView);
        Button dateButton = dialog.findViewById(R.id.eventDateButton);
        ImageView eventImageView = dialog.findViewById(R.id.eventImageView);
        Button uploadButton = dialog.findViewById(R.id.uploadEventButton);

        if (image_uri != null) {
            eventImageView.setImageURI(image_uri);
        }

        Calendar calendar = Calendar.getInstance();

        dateButton.setOnClickListener(v -> new DatePickerDialog(
                this,
                (view, year, month, day) -> {
                    String selectedDate = day + "/" + (month + 1) + "/" + year;
                    dateTextView.setText(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        uploadButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();
            String contact = contactEditText.getText().toString().trim();
            String date = dateTextView.getText().toString();

            if (title.isEmpty() || location.isEmpty() || contact.isEmpty() || date.equals("No date selected")) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (image_uri != null) {
                uploadEventDetails(title, location, contact, date, String.valueOf(image_uri));
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.horizontalMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            window.setAttributes(params);
        }
    }

    private void uploadEventDetails(
            String title,
            String location,
            String contact,
            String date,
            String imageUrl
    ) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("events");
        String eventId = eventsRef.push().getKey();

        if (eventId == null) {
            Toast.makeText(this, "Failed to generate event ID", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> eventDetails = new HashMap<>();
        eventDetails.put("title", title);
        eventDetails.put("location", location);
        eventDetails.put("contact", contact);
        eventDetails.put("date", date);
        eventDetails.put("imageUrl", imageUrl);
        eventDetails.put("uId", currentUserId);
        eventDetails.put("eventId", eventId);

        eventsRef.child(eventId).setValue(eventDetails)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Event uploaded successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to upload event", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pickFromGallery() {
        Intent i = new Intent("android.intent.action.PICK");
        i.setType("image/*");
        startActivityForResult(i, IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, this.storagePermission, STORAGE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length >= 2) {
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (cameraAccepted && storageAccepted) {
                    Toast.makeText(this, "Thank you", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Camera and Storage permissions are required", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Insufficient permissions granted", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickFromGallery();
            } else {
                Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            if (requestCode == REQUEST_CODE_AUDIO_PICK && uri != null) {
                audioUri = uri;
                Toast.makeText(this, "Selected Audio URI: " + uri, Toast.LENGTH_SHORT).show();

                String fileName = getAudioFileName(uri);
                audioNameTextView.setText(fileName);
                audioPlayerLayout.setVisibility(View.VISIBLE);
                setupMediaPlayer(uri);

            } else if (requestCode == IMAGE_PICK_GALLERY_CODE && uri != null) {
                image_uri = uri;
                eventImageView.setImageURI(uri);

            } else if (requestCode == IMAGE_PICK_CAMERA_CODE && image_uri != null) {
                eventImageView.setImageURI(image_uri);
            }
        }
    }
    private String getAudioFileName(Uri uri) {
        String audioName = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        audioName = cursor.getString(index);
                    }
                }
            }
        }

        if (audioName == null) {
            String path = uri.getPath();
            assert path != null;
            int lastSlash = path.lastIndexOf('/');
            if (lastSlash != -1) {
                audioName = path.substring(lastSlash + 1);
            } else {
                audioName = path;
            }
        }

        return audioName;
    }
    private void setupMediaPlayer(Uri uri) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(this, uri);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(mp -> {
                playPauseButton.setEnabled(true);
                playPauseButton.setOnClickListener(v -> togglePlayback());
            });

            mediaPlayer.setOnCompletionListener(mp -> resetAudioPlayer());
            updateSeekBar();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error initializing audio player: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String sanitizeFileName(String fileName) {
        if (fileName == null) return "untitled";

        // Replace characters not allowed in Firebase keys
        return fileName
                .replace(".", "_")
                .replace("#", "_")
                .replace("$", "_")
                .replace("[", "_")
                .replace("]", "_");
    }

    private void togglePlayback() {
        if (mediaPlayer.isPlaying()) {
            pauseAudio();
        } else {
            playAudio();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer == null) return;

        audioSeekBar.setMax(mediaPlayer.getDuration());

        Handler handler = new Handler();
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && isPlaying) {
                    audioSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        };

        handler.post(updateTask);

        audioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void playAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;
            playPauseButton.setImageResource(R.drawable.baseline_pause_circle_24);
            updateSeekBar();
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
        if (mediaPlayer != null) {
            stopAudio();
        }
    }

}
