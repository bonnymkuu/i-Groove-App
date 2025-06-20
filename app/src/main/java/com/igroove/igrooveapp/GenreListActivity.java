package com.igroove.igrooveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.adapters.OnlineAudioAdapter;
import com.igroove.igrooveapp.model.OnlineAudioModel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class GenreListActivity extends AppCompatActivity {
    private OnlineAudioAdapter audioAdapter;
    private List<OnlineAudioModel> audioList;
    private RecyclerView genreAudioRecyclerView;
    private TextView genreNotFoundTV;
    private String selectedGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws DatabaseException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);
        this.genreAudioRecyclerView = findViewById(R.id.genreAudioRecyclerView);
        this.genreNotFoundTV = findViewById(R.id.genreNotFound);
        this.audioList = new ArrayList<>();
        OnlineAudioAdapter onlineAudioAdapter = new OnlineAudioAdapter(this, this.audioList);
        this.audioAdapter = onlineAudioAdapter;
        this.genreAudioRecyclerView.setAdapter(onlineAudioAdapter);
        this.selectedGenre = getIntent().getStringExtra("genre");
        TextView genreTitle = findViewById(R.id.genreTitle);
        genreTitle.setText(this.selectedGenre);
        fetchAudiosForGenre();
        findViewById(R.id.backButton).setOnClickListener(v -> startActivity(new Intent(GenreListActivity.this, MainActivity.class)));
    }

    private void fetchAudiosForGenre() throws DatabaseException {
        DatabaseReference audioRef = FirebaseDatabase.getInstance()
                .getReference("audios");

        audioRef.orderByChild("genre")
                .equalTo(this.selectedGenre)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        audioList.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            OnlineAudioModel song = dataSnapshot.getValue(OnlineAudioModel.class);
                            if (song != null) {
                                audioList.add(song);
                            }
                        }

                        if (audioList.isEmpty()) {
                            genreNotFoundTV.setVisibility(View.VISIBLE);
                            genreAudioRecyclerView.setVisibility(View.GONE);
                        } else {
                            genreNotFoundTV.setVisibility(View.GONE);
                            genreAudioRecyclerView.setVisibility(View.VISIBLE);
                        }

                        audioAdapter.notifyItemChanged(audioList.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(GenreListActivity.this, "Failed to load audios", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
