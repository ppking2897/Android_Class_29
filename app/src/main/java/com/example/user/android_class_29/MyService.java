package com.example.user.android_class_29;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private Timer timer;
    private NotificationManager nmgr;
    private int i;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new MyTimerTask(), 3*1000, 3*1000);
        timer.schedule(new CancelTask(), 20*1000);

        nmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


    }
    private class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            notice();
        }
    }
    private class CancelTask extends TimerTask{
        @Override
        public void run() {
            if (timer != null){
                timer.purge();
                timer.cancel();
                timer = null;
            }
        }
    }
    public void notice(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.po1)
                        .setContentTitle("重要訊息")
                        .setContentText("您有通知請看一下");

        Intent resultIntent = new Intent(this,NoticeActivity.class);


        resultIntent.putExtra("key1", 123);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NoticeActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        Notification notification = builder.build();
        // mid 若為同數字  則認定同一個通知  只會跑出一個
        nmgr.notify(7, notification);
        i++;
    }

}
