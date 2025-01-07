package com.perpeer.bitcointicker.utils

import com.perpeer.bitcointicker.data.model.Coin
import java.util.Locale

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */
object FilterUtils {
    fun filterCoins(
        filterItem: String,
        list: List<Coin>
    ): MutableList<Coin> {
        val tempList: MutableList<Coin> = ArrayList()
        for (i in list) {
            if (filterItem.lowercase(Locale.getDefault()) in i.name.lowercase(Locale.getDefault())) {
                tempList.add(i)
            } else if (filterItem.lowercase(Locale.getDefault()) in i.symbol.lowercase(Locale.getDefault())) {
                tempList.add(i)
            }
        }
        return tempList
    }
}