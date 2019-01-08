package com.example.mohitgupta.newtodoapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.ServiceConfigurationError;

public class Receiver extends BroadcastReceiver {


    private static String task;


    @Override
    public void onReceive(Context context, Intent intent) {


        PendingIntent pi = PendingIntent.getActivity(context, 01234, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(task.toString())
                .setContentText("Have U Completed this Task")
                .setAutoCancel(true);
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(01234, mBuilder.build());
    }

    public static void getTask(String Task)
    {
        task=Task;
        if(task==null)
        {
            task="xyz";
        }
    }


}

