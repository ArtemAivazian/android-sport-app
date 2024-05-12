package com.aivazart.navigation.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.app.Notification
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.aivazart.navigation.MainActivity
import com.aivazart.navigation.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        // Intent to restart/open your MainActivity
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("fromNotification", true)
        }

        // PendingIntent to handle user interaction with the notification
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, AndroidAlarmScheduler.PROTEIN_CHANNEL_ID)
            .setContentTitle("Protein Intake Reminder")
            .setContentText("It's time to take your protein!")
            .setSmallIcon(R.drawable.baseline_baby_changing_station_24)
            .setContentIntent(pendingIntent) // Set the intent that will fire when the user taps the notification.
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Automatically remove the notification when tapped.
            .build()

        notificationManager.notify(1, notification)
    }
}
