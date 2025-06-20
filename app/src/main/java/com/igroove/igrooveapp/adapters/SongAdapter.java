package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igroove.igrooveapp.BookingsActivity;
import com.igroove.igrooveapp.PaymentsActivity;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Songs;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongsViewHolder> {
    private final Context context;
    private final List<Songs> songs;

    public SongAdapter(Context context, List<Songs> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.playlists_item, parent, false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {
        final Songs song = songs.get(position);
        final String artistName = song.getArtistName();
        final String artistId = song.getUserId();
        final String title = song.getTitle();

        holder.titleTextView.setText(title);
        holder.titleTextView.setSelected(true);
        holder.artistTextView.setText(artistName != null ? artistName : context.getString(R.string.unknown_artist));
        holder.priceTextView.setText(String.format("Buy now for: R %s", song.getPrice()));

        Log.d("onBindViewHolder", "audio url is : " + song.getAudioUrl());
        Log.d("onBindViewHolder", "artist name: " + artistName);

        if (song.getProfileUrl() != null) {
            Glide.with(context)
                    .load(song.getProfileUrl())
                    .placeholder(R.drawable.baseline_camera_24)
                    .error(R.drawable.baseline_camera_24)
                    .into(holder.artistImageView);
        } else {
            holder.artistImageView.setImageResource(R.drawable.baseline_camera_24);
        }

        // Click listeners
        holder.artistImageView.setOnClickListener(view ->
                Toast.makeText(context, "Will be updated soon to see artist profile", Toast.LENGTH_SHORT).show()
        );

        holder.buyAirtimeButton.setOnClickListener(view ->
                showImageDialog(R.drawable.voda, R.drawable.mtn, R.drawable.telcom, "Buy With Airtime")
        );

        holder.buyCardButton.setOnClickListener(view ->
                showImageDialog(R.drawable.voda, R.drawable.mtn, R.drawable.telcom, "Buy With Data")
        );

        holder.buyOnlineButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, PaymentsActivity.class);
            intent.putExtra("song_title", title);
            context.startActivity(intent);
        });

        holder.itemView.setOnClickListener(view -> showPurchaseDialog(song));

        holder.bookingsTextView.setOnClickListener(view -> {
            Intent intent = new Intent(context, BookingsActivity.class);
            intent.putExtra("artistName", artistName);
            intent.putExtra("artistId", artistId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // ViewHolder class
    static class SongsViewHolder extends RecyclerView.ViewHolder {
        ImageView artistImageView;
        TextView artistTextView, bookingsTextView;
        TextView buyAirtimeButton, buyCardButton, buyOnlineButton;
        TextView priceTextView, titleTextView;

        public SongsViewHolder(View itemView) {
            super(itemView);
            buyAirtimeButton = itemView.findViewById(R.id.buyWithAirtime);
            buyCardButton = itemView.findViewById(R.id.buyWithBankCard);
            buyOnlineButton = itemView.findViewById(R.id.buyOnline);
            titleTextView = itemView.findViewById(R.id.songTitle);
            artistTextView = itemView.findViewById(R.id.artistName);
            priceTextView = itemView.findViewById(R.id.songPrice);
            artistImageView = itemView.findViewById(R.id.artistImage);
            bookingsTextView = itemView.findViewById(R.id.bookings);
        }
    }

    private void showPurchaseDialog(final Songs song) {
        new AlertDialog.Builder(context)
                .setTitle("Purchase Song")
                .setMessage("Do you want to buy " + song.getTitle() + " ?")
                .setPositiveButton("Buy", (dialog, which) -> {
                    Intent intent = new Intent(context, PaymentsActivity.class);
                    intent.putExtra("song_title", song.getTitle());
                    context.startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void showImageDialog(int res1, int res2, int res3, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(16, 16, 16, 16);

        int imageSize = (int) (context.getResources().getDisplayMetrics().density * 80);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageSize, imageSize);
        params.setMargins(8, 0, 8, 0);

        layout.addView(createImageView(res1, params));
        layout.addView(createImageView(res2, params));
        layout.addView(createImageView(res3, params));

        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(layout);
        builder.setView(scrollView);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private ImageView createImageView(int resId, LinearLayout.LayoutParams params) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resId);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
