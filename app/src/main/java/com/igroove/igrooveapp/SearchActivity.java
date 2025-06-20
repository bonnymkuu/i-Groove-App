package com.igroove.igrooveapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.adapters.ArtistAdapter;
import com.igroove.igrooveapp.adapters.SongsAdapter;
import com.igroove.igrooveapp.model.Artist;
import com.igroove.igrooveapp.model.Songs;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SearchActivity extends AppCompatActivity {
    private ArtistAdapter artistAdapter;
    RecyclerView artistRecyclerView;
    SearchView searchView;
    RecyclerView songRecyclerView;
    private SongsAdapter songsAdapter;
    List<Songs> songsList = new ArrayList<>();
    List<Artist> artistsList = new ArrayList<>();
    List<Songs> filteredSongsList = new ArrayList<>();
    List<Artist> filteredArtistsList = new ArrayList<>();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.songRecyclerView = findViewById(R.id.songRecyclerView);
        this.artistRecyclerView = findViewById(R.id.artistRecyclerView);
        this.searchView = findViewById(R.id.searchView);
        this.songRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SongsAdapter songsAdapter = new SongsAdapter(this, this.filteredSongsList);
        this.songsAdapter = songsAdapter;
        this.songRecyclerView.setAdapter(songsAdapter);
        this.artistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArtistAdapter artistAdapter = new ArtistAdapter(this, this.filteredArtistsList);
        this.artistAdapter = artistAdapter;
        this.artistRecyclerView.setAdapter(artistAdapter);
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String newText) throws DatabaseException {
                SearchActivity.this.searchInFirebase(newText);
                return true;
            }
        });
    }

    public void searchInFirebase(String query) throws DatabaseException {
        if (query.isEmpty()) {
            fetchSongsFromFirebase();
            fetchArtistsFromFirebase();
        } else {
            searchSongsInFirebase(query);
            searchArtistsInFirebase(query);
        }
    }

    private void fetchSongsFromFirebase() throws DatabaseException {
        DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference("audios");
        songsRef.addValueEventListener(new ValueEventListener() {
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.this.songsList.clear();
                for (DataSnapshot songSnapshot : snapshot.getChildren()) {
                    Songs song = songSnapshot.getValue(Songs.class);
                    SearchActivity.this.songsList.add(song);
                }
                SearchActivity.this.filteredSongsList.clear();
                SearchActivity.this.filteredSongsList.addAll(SearchActivity.this.songsList);
                SearchActivity.this.songsAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Failed to load songs: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchArtistsFromFirebase() throws DatabaseException {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.this.artistsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Artist user = dataSnapshot.getValue(Artist.class);
                    if (user != null) {
                        String position = snapshot.child("position").getValue(String.class);
                        if (ExifInterface.TAG_ARTIST.equals(position)) {
                            SearchActivity.this.artistsList.add(user);
                        }
                    }
                }
                SearchActivity.this.artistAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void searchSongsInFirebase(String query) throws DatabaseException {
        final String lowercaseQuery = query.toLowerCase();
        DatabaseReference songsRef = FirebaseDatabase.getInstance().getReference("audios");
        songsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.this.filteredSongsList.clear();
                for (DataSnapshot songSnapshot : snapshot.getChildren()) {
                    Songs song = songSnapshot.getValue(Songs.class);
                    if (song != null) {
                        String songTitleLowercase = song.getTitle().toLowerCase();
                        if (songTitleLowercase.contains(lowercaseQuery)) {
                            SearchActivity.this.filteredSongsList.add(song);
                        }
                    }
                }
                SearchActivity.this.songsAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Failed to search songs: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchArtistsInFirebase(String query) throws DatabaseException {
        final String lowercaseQuery = query.toLowerCase();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SearchActivity.this.filteredArtistsList.clear();
                for (DataSnapshot artistSnapshot : snapshot.getChildren()) {
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    if (artist != null) {
                        String artistName = artist.getName();
                        if (artistName.contains(lowercaseQuery)) {
                            SearchActivity.this.filteredArtistsList.add(artist);
                        }
                    }
                }
                SearchActivity.this.artistAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Failed to search artists: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
