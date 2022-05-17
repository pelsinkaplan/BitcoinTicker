package com.pelsinkaplan.bitcointicker.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import com.pelsinkaplan.bitcointicker.R

/**
 * Created by Pelşin KAPLAN on 16.05.2022.
 */
class CoinWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val data = inputData.keyValueMap
        createNotification(data["coinId"].toString(), data["userId"].toString())
        return Result.success()
    }

    private fun createNotification(coinId: String, userId: String) {

        val builder: NotificationCompat.Builder

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notifList: List<String> = listOf()
        val database =
            FirebaseDatabase.getInstance().getReference("notifications").child(userId)

        database.get().addOnSuccessListener {
            if (it.exists()) {
                notifList = it.value as List<String>
                database.setValue(notifList.plus(coinId))
            }
        }

        val channelId = "channelId"
        val channelName = "channelName"
        val channelIntroduction = "channelIntroduction"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        var channel: NotificationChannel? = notificationManager
            .getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(channelId, channelName, channelPriority)
            channel.description = channelIntroduction
            notificationManager.createNotificationChannel(channel)
        }

        builder = NotificationCompat.Builder(applicationContext, channelId)
        builder
            .setContentTitle("The price of the coin you follow has updated!")
            .setSmallIcon(R.drawable.ic_coin)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }
}