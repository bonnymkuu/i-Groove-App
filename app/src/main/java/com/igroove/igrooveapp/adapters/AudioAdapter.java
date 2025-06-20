package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.OfflineAudioModel;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {

    private final ArrayList<OfflineAudioModel> audioList;
    private final Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClicked(OfflineAudioModel offlineAudioModel);
    }

    public AudioAdapter(Context context, ArrayList<OfflineAudioModel> audioList, OnItemClickListener listener) {
        this.context = context;
        this.audioList = audioList;
        this.listener = listener;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_item, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, int position) {
        OfflineAudioModel offlineAudioModel = audioList.get(position);
        holder.songTitleTextView.setText(offlineAudioModel.getTitle());

        holder.itemView.setOnClickListener(view -> handleAudioClick(offlineAudioModel));
    }

    private void handleAudioClick(OfflineAudioModel offlineAudioModel) {
        listener.onItemClicked(offlineAudioModel);
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView songTitleTextView;

        public AudioViewHolder(View itemView) {
            super(itemView);
            songTitleTextView = itemView.findViewById(R.id.audioTitle);
        }
    }
}
