package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igroove.igrooveapp.PaymentsActivity;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.OnlineAudioModel;

import java.util.List;

public class OnlineAudioAdapter extends RecyclerView.Adapter<OnlineAudioAdapter.AudioViewHolder> {

    private final List<OnlineAudioModel> audioList;
    private final Context context;

    public OnlineAudioAdapter(Context context, List<OnlineAudioModel> audioList) {
        this.context = context;
        this.audioList = audioList;
    }

    @Override
    public AudioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_item, parent, false);
        return new AudioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AudioViewHolder holder, int position) {
        final OnlineAudioModel audio = audioList.get(position);
        holder.audioTitleTextView.setText(audio.getTitle());

        Glide.with(context)
                .load(audio.getProfile())
                .placeholder(R.drawable.baseline_camera_24)
                .error(R.drawable.baseline_camera_24)
                .into(holder.profileImageView);

        holder.itemView.setOnClickListener(view -> handleAudioItemClick(audio));
    }

    @Override
    public int getItemCount() {
        return audioList.size();
    }

    private void handleAudioItemClick(OnlineAudioModel audio) {
        showPurchaseDialog(audio);
    }

    private void showPurchaseDialog(final OnlineAudioModel audio) {
        new AlertDialog.Builder(context)
                .setTitle("Purchase Song")
                .setMessage("Do you want to buy " + audio.getTitle() + "?")
                .setPositiveButton("Buy", (dialog, i) -> handleBuyClick(audio))
                .setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss())
                .create()
                .show();
    }

    private void handleBuyClick(OnlineAudioModel audio) {
        Intent intent = new Intent(context, PaymentsActivity.class);
        intent.putExtra("song_title", audio.getTitle());
        context.startActivity(intent);
    }

    public static class AudioViewHolder extends RecyclerView.ViewHolder {
        TextView audioTitleTextView;
        ImageView profileImageView;

        public AudioViewHolder(View itemView) {
            super(itemView);
            audioTitleTextView = itemView.findViewById(R.id.audioTitle);
            profileImageView = itemView.findViewById(R.id.profileImageView);
        }
    }
}
