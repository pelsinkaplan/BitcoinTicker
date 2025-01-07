package com.perpeer.bitcointicker.utils

import java.time.Duration
import java.time.LocalDateTime

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */
fun LocalDateTime.isMoreThanFiveMinutesAgo(): Boolean {
    val now = LocalDateTime.now() // Şu anki zaman
    val duration = Duration.between(this, now) // İki tarih/zaman arasındaki fark
    return duration.toMinutes() > 5 // 5 dakikadan fazla olup olmadığını kontrol et
}