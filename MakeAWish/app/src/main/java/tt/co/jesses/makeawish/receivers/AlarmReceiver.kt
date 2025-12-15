package tt.co.jesses.makeawish.receivers

import android.app.Notification.CATEGORY_ALARM
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import tt.co.jesses.makeawish.MainActivity
import tt.co.jesses.makeawish.R
import tt.co.jesses.makeawish.ui.navigation.Screen

/**
 * Created by jessescott on 2017-02-22.
 */

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG, "Received Intent: " + intent.dataString)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("navigation_route", Screen.NOTIFICATION.route)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

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
