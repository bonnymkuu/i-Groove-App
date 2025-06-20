package com.igroove.igrooveapp;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;

/* loaded from: classes3.dex */
public class NotificationHelper {
    private static final String CHANNEL_ID = "event_channel";

    public static void sendNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(R.drawable.app_logo).setContentTitle(title).setContentText(message).setPriority(0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
