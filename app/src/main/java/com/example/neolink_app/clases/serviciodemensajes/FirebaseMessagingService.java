package com.example.neolink_app.clases.serviciodemensajes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.neolink_app.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public static int NOTIFICATION_ID = 1;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        enviarlanotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),getString(R.string.CHANNEL_ID));

        /*
        if (remoteMessage.getData().size() > 0) {
            enviarlanotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),getString(R.string.CHANNEL_ID));
        }
         */


    }

    private void enviarlanotificacion(String title, String content, String CHANNEL_ID){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.appnotificationicon)
                .setColor(getResources().getColor(R.color.notificacion))
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Since android Oreo notification channel is needed.
        if (NOTIFICATION_ID > 1073741824) {
            NOTIFICATION_ID = 0;
        }
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID++, builder.build());
    }
}
