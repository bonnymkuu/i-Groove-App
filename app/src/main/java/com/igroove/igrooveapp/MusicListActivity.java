package com.igroove.igrooveapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.adapters.AudioAdapter;
import com.igroove.igrooveapp.adapters.OnlineAudioAdapter;
import com.igroove.igrooveapp.model.OfflineAudioModel;
import com.igroove.igrooveapp.model.OnlineAudioModel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class MusicListActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_READ_STORAGE = 124;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 123;
    private static final String TAG = "MusicListActivity";
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    private AudioAdapter localAdapter;
    RecyclerView localMusicRecyclerView;
    private ArrayList<OfflineAudioModel> localSongList;
    private OnlineAudioAdapter onlineAdapter;
    RecyclerView onlineMusicRecyclerView;
    private ArrayList<OnlineAudioModel> onlineSongList;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        this.onlineMusicRecyclerView = findViewById(R.id.onlineMusicRecyclerView);
        this.localMusicRecyclerView = findViewById(R.id.localMusicRecyclerView);
        this.onlineMusicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.localMusicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.onlineSongList = new ArrayList<>();
        this.localSongList = new ArrayList<>();
        this.onlineAdapter = new OnlineAudioAdapter(this, this.onlineSongList);
        this.localAdapter = new AudioAdapter(this, this.localSongList, this::playLocalMusic);
        this.onlineMusicRecyclerView.setAdapter(this.onlineAdapter);
        this.localMusicRecyclerView.setAdapter(this.localAdapter);
        if (checkPermission()) {
            Log.d(TAG, "onCreate: Permission granted loading Local Music");
            loadLocalMusic();
        } else {
            Log.d(TAG, "onCreate: Permission not granted, requesting permission");
            requestPermission();
        }
        loadOnlineMusic();
    }

    private void loadLocalMusic() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQUEST_CODE_READ_STORAGE);
        }
        Log.d(TAG, "loadLocalMusic: Started scanning for music files");
        this.executorService.execute(() -> {
            ContentResolver contentResolver = getContentResolver();
            String[] projection = {"title", "_data", "_id"};
            Uri externalAudioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Uri internalAudioUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
            scanUriForMusic(contentResolver, externalAudioUri, projection);
            scanUriForMusic(contentResolver, internalAudioUri, projection);
            runOnUiThread(() -> localAdapter.notifyDataSetChanged());
        });
    }

    private void scanUriForMusic(ContentResolver contentResolver, Uri audioUri, String[] projection) {
        Log.d(TAG, "scanUriForMusic: Querying URI " + audioUri.toString());
        Cursor cursor = contentResolver.query(audioUri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                OfflineAudioModel offlineAudioModel = new OfflineAudioModel(title, path);
                this.localSongList.add(offlineAudioModel);
            }
            cursor.close();
        } else {
            Log.d(TAG, "scanUriForMusic: no audio found");
            runOnUiThread(() -> Toast.makeText(MusicListActivity.this, "No local music found", Toast.LENGTH_SHORT).show());
        }
    }

    private void loadOnlineMusic() throws DatabaseException {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("audios");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onlineSongList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    OnlineAudioModel song = dataSnapshot.getValue(OnlineAudioModel.class);
                    if (song != null) {
                        onlineSongList.add(song);
                    }
                }
                onlineAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MusicListActivity.this, "Failed to load songs: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void playLocalMusic(OfflineAudioModel song) {
        Intent intent = new Intent(this, PlayingMusicActivity.class);
        intent.putExtra("songPath", song.getPath());
        intent.putExtra("songName", song.getTitle());
        startActivity(intent);
    }

    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        Log.d(TAG, "checkPermission: permission check result = " + result);
        return result == 0;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            Toast.makeText(this, "Storage permission is needed to load local music", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "requestPermission: Show Permission Rationale ");
        }
        Log.d(TAG, "requestPermission: Requesting permission check result = ");
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQUEST_CODE_STORAGE_PERMISSION);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            Log.d(TAG, "onRequestPermissionsResult: Permission result received");
            if (grantResults.length > 0 && grantResults[0] == 0) {
                Log.d(TAG, "onRequestPermissionsResult: Permission granted, loading local music");
                loadLocalMusic();
            } else {
                Log.d(TAG, "onRequestPermissionsResult: Permission denied");
                loadLocalMusic();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = this.localMusicRecyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.localAdapter);
        }
    }
}
