package com.igroove.igrooveapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.*;
import com.igroove.igrooveapp.ArtistProfileActivity;
import com.igroove.igrooveapp.MainActivity;
import com.igroove.igrooveapp.R;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {

    private final Context context;
    private final List<String> imageList;

    public ImageSliderAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final String imageUrl = imageList.get(position);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.baseline_camera_24)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> handleImageClick(imageUrl));
    }

    private void handleImageClick(String imageUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");

        databaseReference.orderByChild("imageUrl").equalTo(imageUrl)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                            String eventId = eventSnapshot.getKey();

                            if (context instanceof MainActivity) {
                                ((MainActivity) context).showEventDetailsDialog(eventId);
                            }

                            if (context instanceof ArtistProfileActivity) {
                                ((ArtistProfileActivity) context).showEventDetailsDialog();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(context, "Failed to load event ID: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderImageView);
        }
    }
}
