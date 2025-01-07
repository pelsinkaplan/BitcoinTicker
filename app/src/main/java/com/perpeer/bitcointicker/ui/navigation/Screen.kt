package com.perpeer.bitcointicker.ui.navigation

sealed class Screen(
    val route: String,
    val arguments: String? = "",
    val name: String,
) {

    data object Coins :
        Screen(route = "coins", arguments = null, name = "Coins")

    data object CoinDetail :
        Screen(route = "coin_detail/", arguments = "{coin}", name = "Coin Detail")
}
