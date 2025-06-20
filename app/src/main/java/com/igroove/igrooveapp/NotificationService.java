package com.igroove.igrooveapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.igroove.igrooveapp.model.Bookings;

import java.util.Objects;

/* loaded from: classes3.dex */
public class NotificationService extends Service {
    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) throws DatabaseException {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).child("Notifications");
        ref.addValueEventListener(new ValueEventListener() {
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Bookings bookings = childSnapshot.getValue(Bookings.class);
                    if (bookings != null) {
                        NotificationHelper.sendNotification(NotificationService.this.getApplicationContext(), "New Event: " + bookings.getLocation(), "Date: " + bookings.getDate() + ", Time: " + bookings.getTime());
                    }
                }
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("NotificationService", "Database error: " + error.getMessage());
            }
        });
        return Service.START_STICKY;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }
}
