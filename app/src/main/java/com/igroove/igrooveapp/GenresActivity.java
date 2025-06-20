package com.igroove.igrooveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.igroove.igrooveapp.adapters.GenresAdapter;
import com.igroove.igrooveapp.model.Genre;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class GenresActivity extends AppCompatActivity {
    private ImageView addActivity;
    private ImageView genresImageView;
    private ImageView othersActivity;
    private ImageView searchIcon;
    private ImageView soundActivity;
    private ImageView userPic;
    private ImageView venueActivity;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        RecyclerView genresRecyclerView = findViewById(R.id.genresRecyclerView);
        this.userPic = findViewById(R.id.userPic);
        this.searchIcon = findViewById(R.id.searchIcon);
        this.addActivity = findViewById(R.id.addActivity);
        this.venueActivity = findViewById(R.id.venueActivity);
        this.soundActivity = findViewById(R.id.soundActivity);
        this.othersActivity = findViewById(R.id.othersActivity);
        this.genresImageView = findViewById(R.id.genresImageView);

        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("Pop", R.drawable.app_logo));
        genreList.add(new Genre("Hip Hop", R.drawable.n_c));
        genreList.add(new Genre("Rock", R.drawable.amanda));
        genreList.add(new Genre("Jazz", R.drawable.jazz));
        genreList.add(new Genre("Gospel", R.drawable.app_logo));
        genreList.add(new Genre("Classical", R.drawable.amanda));
        genreList.add(new Genre("Afro beats", R.drawable.afro));
        genreList.add(new Genre("Reggae", R.drawable.dube));
        genreList.add(new Genre("Country", R.drawable.hugh));
        genreList.add(new Genre("Metal", R.drawable.app_logo));
        genreList.add(new Genre("Folk", R.drawable.app_logo));
        genreList.add(new Genre("Others", R.drawable.hugh));

        GenresAdapter genresAdapter = new GenresAdapter(this, genreList);
        genresRecyclerView.setAdapter(genresAdapter);
        genresRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        setupOnClickListener();
    }
    private void setupOnClickListener() {
        searchIcon.setOnClickListener(this::openSearchActivity);
        userPic.setOnClickListener(this::openArtistProfile);
        genresImageView.setOnClickListener(this::refreshGenres);
        venueActivity.setOnClickListener(this::openVenueRental);
        soundActivity.setOnClickListener(this::openSoundRental);
        othersActivity.setOnClickListener(this::openMusicList);
        addActivity.setOnClickListener(this::openArtistUpload);
    }

    // Opens the SearchActivity
    private void openSearchActivity(View v) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    // Opens the ArtistProfileActivity
    private void openArtistProfile(View v) {
        startActivity(new Intent(this, ArtistProfileActivity.class));
    }

    // Refreshes the current GenresActivity
    private void refreshGenres(View v) {
        startActivity(new Intent(this, GenresActivity.class));
    }

    // Opens the RentVenueActivity
    private void openVenueRental(View v) {
        startActivity(new Intent(this, RentVenueActivity.class));
    }

    // Opens the RentSoundActivity
    private void openSoundRental(View v) {
        startActivity(new Intent(this, RentSoundActivity.class));
    }

    // Opens the MusicListActivity
    private void openMusicList(View v) {
        startActivity(new Intent(this, MusicListActivity.class));
    }

    // Opens the ArtistUploadActivity
    private void openArtistUpload(View v) {
        startActivity(new Intent(this, ArtistUploadActivity.class));
    }

}
