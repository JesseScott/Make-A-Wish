package tt.co.jesses.makeawish.receivers

import android.app.Notification.CATEGORY_ALARM
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v7.app.NotificationCompat
import android.util.Log
import tt.co.jesses.makeawish.NotificationActivity
import tt.co.jesses.makeawish.R

/**
 * Created by jessescott on 2017-02-22.
 */

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG, "Received Intent: " + intent.dataString)

        val notificationIntent = Intent(context, NotificationActivity::class.java)

        val stackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(NotificationActivity::class.java)
        stackBuilder.addNextIntent(notificationIntent)

        val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, "default")

        // TODO explore styles
        //val inboxStyle = NotificationCompat.InboxStyle
        //inboxStyle.setBigContentTitle("Event tracker details:")
        //inboxStyle.addLine("foooo")

        // TODO add RemoteInput to allow for inline wishing

        // TODO sound

        val notification = builder
                .setContentTitle(context.getString(R.string.receiver_title))
                .setContentText(context.getString(R.string.receiver_text))
                .setTicker(context.getString(R.string.receiver_ticker))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setCategory(CATEGORY_ALARM)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setStyle(inboxStyle)
                .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)

    }

    companion object {
        private val TAG = AlarmReceiver::class.java.simpleName
    }
}
