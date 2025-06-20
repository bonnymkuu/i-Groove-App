package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igroove.igrooveapp.BookingsActivity;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Artist;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<Artist> artists;
    private final Context context;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.artist_item, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        final Artist artist = artists.get(position);

        // Set artist name
        if (artist.getName() != null) {
            holder.artistNameTextView.setText(artist.getName());
        } else {
            holder.artistNameTextView.setText("Unknown Artist");
        }

        // Set artist image
        if (artist.getImage() != null) {
            Glide.with(context)
                    .load(artist.getImage())
                    .placeholder(R.drawable.ic_user)
                    .into(holder.artistImage);
        } else {
            holder.artistImage.setImageResource(R.drawable.ic_user);
        }

        // Set click listener
        if (artist.getId() != null) {
            holder.itemView.setOnClickListener(view -> openBookingsActivity(artist));
        } else {
            Log.d("ArtistAdapter", "Artist Id is null for artist: " + artist.getName());
        }
    }

    private void openBookingsActivity(Artist artist) {
        Intent intent = new Intent(context, BookingsActivity.class);
        intent.putExtra("artistId", artist.getId());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void updateList(List<Artist> newList) {
        this.artists = newList;
        notifyDataSetChanged();
    }

    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        ImageView artistImage;
        TextView artistNameTextView;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            artistNameTextView = itemView.findViewById(R.id.artistName);
            artistImage = itemView.findViewById(R.id.artistImage);
        }
    }
}
