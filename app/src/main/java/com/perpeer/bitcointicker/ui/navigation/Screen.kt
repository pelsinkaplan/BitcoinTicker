package com.perpeer.bitcointicker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val arguments: String? = "",
    val name: String,
    val icon: ImageVector? = null
) {

    data object SignIn :
        Screen(route = "sign_in", arguments = null, name = "Sign In")

    data object SignUp :
        Screen(route = "sign_up", arguments = null, name = "Sign Up")

    data object ForgotPassword :
        Screen(route = "forgot_password", arguments = null, name = "Forgot Password")

    data object CoinDetail :
        Screen(route = "coin_detail/", arguments = "{coin}", name = "Coin Detail")

    data object Coins :
        Screen(route = "coins", arguments = null, name = "Coins", icon = Icons.Rounded.Home)

    data object Favorites :
        Screen(
            route = "favorites",
            arguments = null,
            name = "Favorites",
            icon = Icons.Rounded.Favorite
        )
}
