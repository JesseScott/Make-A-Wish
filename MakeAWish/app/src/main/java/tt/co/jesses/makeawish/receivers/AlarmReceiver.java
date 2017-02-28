package tt.co.jesses.makeawish.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import tt.co.jesses.makeawish.NotificationActivity;
import tt.co.jesses.makeawish.R;

import static android.app.Notification.CATEGORY_ALARM;

/**
 * Created by jessescott on 2017-02-22.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "Received Intent: " + intent.getDataString());

        Intent notificationIntent = new Intent(context, NotificationActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        // TODO explore styles
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Event tracker details:");
        inboxStyle.addLine("foooo");

        // TODO add RemoteInput to allow for inline wishing

        // TODO sound

        Notification notification = builder
                .setContentTitle(context.getString(R.string.receiver_title))
                .setContentText(context.getString(R.string.receiver_text))
                .setTicker(context.getString(R.string.receiver_ticker))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setCategory(CATEGORY_ALARM)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setStyle(inboxStyle)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
}
