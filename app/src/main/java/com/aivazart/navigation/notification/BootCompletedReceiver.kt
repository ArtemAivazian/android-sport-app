package com.aivazart.navigation.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED && context != null) {
            val sharedPrefs = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val scheduledTime = sharedPrefs.getLong("scheduledTime", -1)
            if (scheduledTime != -1L) {
                AndroidAlarmScheduler.scheduleProteinReminder(context, scheduledTime)
            }
        }
    }
}