package com.perpeer.bitcointicker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

@Entity(tableName = "cache_table")
data class Coin(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String
)
