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
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.igroove.igrooveapp.R;
import com.igroove.igrooveapp.model.Sound;

import java.util.HashMap;
import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.SoundViewHolder> {

    private final Context context;
    private final List<Sound> soundList;
    private final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public SoundAdapter(Context context, List<Sound> soundList) {
        this.context = context;
        this.soundList = soundList;
    }

    @Override
    public SoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venue, parent, false);
        return new SoundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SoundViewHolder holder, int position) {
        final Sound sound = soundList.get(position);

        holder.soundNameTextView.setText(sound.getSoundName());
        holder.soundPriceTextView.setText("Price: R" + sound.getPrice());

        Glide.with(context)
                .load(sound.getProfileImageUrl())
                .placeholder(R.drawable.baseline_camera_24)
                .into(holder.profileImageView);

        holder.itemView.setOnClickListener(view -> showDetailsDialog(sound));
    }

    @Override
    public int getItemCount() {
        return soundList.size();
    }

    public static class SoundViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView soundNameTextView;
        TextView soundPriceTextView;

        public SoundViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.venueIv);
            soundNameTextView = itemView.findViewById(R.id.venue);
            soundPriceTextView = itemView.findViewById(R.id.distance);
        }
    }

    private void showDetailsDialog(final Sound sound) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_venue_details, null);
        final EditText locationInput = dialogView.findViewById(R.id.inputLocation);
        final EditText dateInput = dialogView.findViewById(R.id.inputDate);
        final EditText timeInput = dialogView.findViewById(R.id.inputTime);

        new AlertDialog.Builder(context)
                .setTitle("Enter Booking Details")
                .setView(dialogView)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String location = locationInput.getText().toString().trim();
                    String date = dateInput.getText().toString().trim();
                    String time = timeInput.getText().toString().trim();

                    if (TextUtils.isEmpty(location) || TextUtils.isEmpty(date) || TextUtils.isEmpty(time)) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        saveBookingDetailsToFirebase(sound, location, date, time);
                    } catch (DatabaseException e) {
                        Toast.makeText(context, "Failed to book: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void saveBookingDetailsToFirebase(Sound sound, String location, String date, String time) throws DatabaseException {
        if (currentUser == null) return;

        DatabaseReference bookingRef = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(sound.getUserId())
                .child("Notifications");

        String bookingId = bookingRef.push().getKey();
        if (bookingId == null) return;

        HashMap<String, Object> bookingDetails = new HashMap<>();
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
                        Toast.makeText(context, "Failed to save booking.", Toast.LENGTH_SHORT).show()
                );
    }
}
