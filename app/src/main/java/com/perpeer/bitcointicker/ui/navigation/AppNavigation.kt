package com.perpeer.bitcointicker.ui.navigation

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.components.BottomNavigationBar
import com.perpeer.bitcointicker.ui.screens.CoinDetailScreen
import com.perpeer.bitcointicker.ui.screens.CoinsScreen
import com.perpeer.bitcointicker.ui.screens.FavoritesScreen
import com.perpeer.bitcointicker.ui.screens.ForgotPasswordScreen
import com.perpeer.bitcointicker.ui.screens.SignInScreen
import com.perpeer.bitcointicker.ui.screens.SignUpScreen
import com.perpeer.bitcointicker.viewmodel.MainViewModel

/**
 * Created by Pelşin KAPLAN on 7.01.2025.
 */
@Composable
fun AppNavigation(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    var currentRoute by remember { mutableStateOf<String?>(null) }
// Dinleme işlemi burada gerçekleşiyor
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route
            Log.d("CurrentRoute", currentRoute ?: "No route")
        }
    }
    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.Coins.route || currentRoute == Screen.Favorites.route) {
                BottomNavigationBar(navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = if (viewModel.isUserLoggedIn()) Screen.Coins.route else Screen.SignIn.route
        ) {
            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onSignUpSuccess = { navController.navigate(Screen.SignIn.route) }
                )
            }
            composable(Screen.SignIn.route) {
                SignInScreen(
                    onSignInSuccess = { navController.navigate(Screen.Coins.route) },
                    onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                    onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) }
                )
            }
            composable(Screen.ForgotPassword.route) {
                ForgotPasswordScreen(
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Coins.route) {
                CoinsScreen(
                    onCoinClick = { coin ->
                        navController.navigate(Screen.CoinDetail.route + Gson().toJson(coin))
                    },
                    signOut = { navController.navigate(Screen.SignIn.route) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    onCoinClick = { coin ->
                        navController.navigate(Screen.CoinDetail.route + Gson().toJson(coin))
                    })
            }
            composable(
                Screen.CoinDetail.route + Screen.CoinDetail.arguments,
                arguments = listOf(navArgument("coin") { type = NavType.StringType })
            ) { backStackEntry ->
                val coin =
                    Gson().fromJson(backStackEntry.arguments?.getString("coin"), Coin::class.java)
                CoinDetailScreen(selectedCoin = coin, onBack = { navController.popBackStack() })
            }
        }
    }
}
