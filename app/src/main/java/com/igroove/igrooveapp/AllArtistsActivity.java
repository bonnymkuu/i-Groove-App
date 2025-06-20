package com.igroove.igrooveapp;

import android.os.Bundle;
import android.util.Log;

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
import com.igroove.igrooveapp.model.Artist;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AllArtistsActivity extends AppCompatActivity {
    private ArtistAdapter artistAdapter;
    private List<Artist> artistsList = new ArrayList<>();
    private RecyclerView artistsRecyclerView;
    SearchView searchView;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_artists);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.artistsRecyclerView);
        this.artistsRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArtistAdapter artistAdapter = new ArtistAdapter(this, this.artistsList);
        this.artistAdapter = artistAdapter;
        this.artistsRecyclerView.setAdapter(artistAdapter);
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: com.igroove.igrooveapp.AllArtistsActivity.1
            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String newText) throws DatabaseException {
                AllArtistsActivity.this.searchArtistsInFirebase(newText);
                AllArtistsActivity.this.fetchArtistsFromFirebase();
                return true;
            }
        });
        fetchArtistsFromFirebase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void searchArtistsInFirebase(String query) {
        List<Artist> filteredList = new ArrayList<>();
        for (Artist model : this.artistsList) {
            if (model.getName() != null && model.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(model);
            }
        }
        this.artistAdapter.updateList(filteredList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchArtistsFromFirebase() throws DatabaseException {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        usersRef.limitToFirst(10).addValueEventListener(new ValueEventListener() { // from class: com.igroove.igrooveapp.AllArtistsActivity.2
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllArtistsActivity.this.artistsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Artist user = (Artist) dataSnapshot.getValue(Artist.class);
                    if (user != null) {
                        AllArtistsActivity.this.artistsList.add(user);
                        String position = (String) snapshot.child("position").getValue(String.class);
                        if (ExifInterface.TAG_ARTIST.equals(position)) {
                            Log.d("", "");
                        }
                    }
                }
                AllArtistsActivity.this.artistAdapter.notifyItemInserted(AllArtistsActivity.this.artistsList.size());
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
