package com.perpeer.bitcointicker.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.perpeer.bitcointicker.R
import com.perpeer.bitcointicker.data.model.FirestoreCoin

/**
 * Created by Pelşin KAPLAN on 9.01.2025.
 */

object NotificationUtils {

    private const val CHANNEL_ID = "price_alert_channel"

    fun showPriceChangeNotification(context: Context, coin: FirestoreCoin, isIncrease: Boolean) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Kanal oluşturma (Android O ve üstü için)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Price Alerts",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Bildirim oluşturma
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Price Alert: ${coin.name}")
            .setContentText("Price ${if (isIncrease) "increased" else "decreased"}! Come and look at it.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(coin.id.hashCode(), notification)
    }
}
