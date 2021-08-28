package com.myapp.royalcounselling;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static int count = 0;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        try {

            Log.e("TAG",remoteMessage.getMessageId());
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"),remoteMessage.getData().get("activity"));
      //      super.onMessageReceived(remoteMessage);
            String message = remoteMessage.getData().get("message");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        getSharedPreferences("MYAPP", MODE_PRIVATE).edit().putString("TOKEN", s).apply();

    }

    private void sendNotification(String title, String messageBody,String activity) {
        Bundle extras = new Bundle();
        if(activity.contains("Login")){
            extras.putString("loadFragment","pptRequest");
        }
        NavDeepLinkBuilder na = new NavDeepLinkBuilder(getApplicationContext());
        na.setComponentName(NavigationDrawerActivity.class);
        na.setDestination(R.id.nav_view_ppt_requests);
        na.setGraph(R.navigation.mobile_navigation);
        na.setArguments(extras);
        PendingIntent pendingIntent = na.createPendingIntent();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        AudioAttributes audioAttributes = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel  = new NotificationChannel("Sesame", "Sesame", importance);
            mChannel.setDescription(messageBody);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setSound(defaultSoundUri,audioAttributes);
            mNotifyManager.createNotificationChannel(mChannel);
        }


    }

}
