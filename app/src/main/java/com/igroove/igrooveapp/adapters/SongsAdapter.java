package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Songs;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private final Context context;
    private final List<Songs> songsList;

    public SongsAdapter(Context context, List<Songs> songsList) {
        this.context = context;
        this.songsList = songsList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Songs song = songsList.get(position);
        holder.titleTextView.setText(song.getTitle());
        holder.artistTextView.setText(song.getArtistName());

        // Optional: Set a default or load song thumbnail here if needed
        // Glide.with(context).load(song.getImageUrl()).into(holder.thumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView artistTextView;
        TextView titleTextView;
        ImageView thumbnailImageView;

        public SongViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.songTitle);
            artistTextView = itemView.findViewById(R.id.songArtist);
            thumbnailImageView = itemView.findViewById(R.id.songThumbnail);
        }
    }
}
