package com.igroove.igrooveapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Venue;

import java.util.HashMap;
import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> {

    private final Context context;
    private final List<Venue> venueList;
    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public VenueAdapter(Context context, List<Venue> venueList) {
        this.context = context;
        this.venueList = venueList;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venue, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, int position) {
        Venue venue = venueList.get(position);

        holder.venueName.setText(venue.getVenueName());
        holder.distance.setText(venue.getDistance());

        if (venue.getImageUrl() != null) {
            Glide.with(context)
                    .load(venue.getImageUrl())
                    .placeholder(R.drawable.baseline_camera_24)
                    .into(holder.venueImage);
        } else {
            holder.venueImage.setImageResource(R.drawable.baseline_camera_24);
        }

        holder.itemView.setOnClickListener(v -> showDetailsDialog(venue));
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    static class VenueViewHolder extends RecyclerView.ViewHolder {
        TextView venueName, distance;
        ImageView venueImage;

        public VenueViewHolder(View itemView) {
            super(itemView);
            venueName = itemView.findViewById(R.id.venue);
            distance = itemView.findViewById(R.id.distance);
            venueImage = itemView.findViewById(R.id.venueIv);
        }
    }

    private void showDetailsDialog(Venue venue) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_venue_details, null);
        EditText locationInput = dialogView.findViewById(R.id.inputLocation);
        EditText dateInput = dialogView.findViewById(R.id.inputDate);
        EditText timeInput = dialogView.findViewById(R.id.inputTime);

        new AlertDialog.Builder(context)
                .setTitle("Enter Booking Details")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String location = locationInput.getText().toString().trim();
                    String date = dateInput.getText().toString().trim();
                    String time = timeInput.getText().toString().trim();

                    if (TextUtils.isEmpty(location) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else {
                        saveBookingDetailsToFirebase(venue, location, date, time);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveBookingDetailsToFirebase(Venue venue, String location, String date, String time) {
        if (currentUser == null) {
            Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference bookingRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(venue.getUserId())
                .child("Notifications");

        String bookingId = bookingRef.push().getKey();
        if (bookingId == null) {
            Toast.makeText(context, "Failed to generate booking ID", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> bookingDetails = new HashMap<>();
        bookingDetails.put("venueName", venue.getVenueName());
        bookingDetails.put("location", location);
        bookingDetails.put("date", date);
        bookingDetails.put("time", time);
        bookingDetails.put("userId", currentUser.getUid());

        bookingRef.child(currentUser.getUid()).child(bookingId)
                .setValue(bookingDetails)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(context, "Booking saved!", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(context, "Failed to save booking: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
