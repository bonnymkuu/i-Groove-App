package com.igroove.igrooveapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.OnlineAudioModel;

import java.util.List;

public class ProfileSongsAdapter extends RecyclerView.Adapter<ProfileSongsAdapter.SongViewHolder> {

    private final List<OnlineAudioModel> songList;

    public ProfileSongsAdapter(List<OnlineAudioModel> songList) {
        this.songList = songList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_downloads, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        OnlineAudioModel song = songList.get(position);
        holder.songTitleTextView.setText(song.getTitle());
        holder.downloadsTextView.setText(String.format("Downloads: %d", song.getDownloads()));
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView songTitleTextView;
        TextView downloadsTextView;

        public SongViewHolder(View itemView) {
            super(itemView);
            songTitleTextView = itemView.findViewById(R.id.songTitle);
            downloadsTextView = itemView.findViewById(R.id.downloadsTextView);
        }
    }
}
