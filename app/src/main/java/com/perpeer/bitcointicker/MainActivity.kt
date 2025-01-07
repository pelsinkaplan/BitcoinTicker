package com.perpeer.bitcointicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.perpeer.bitcointicker.data.cache.PreferenceManager.Companion.preferenceManager
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.navigation.Screen
import com.perpeer.bitcointicker.ui.screens.CoinDetailScreen
import com.perpeer.bitcointicker.ui.screens.HomeScreen
import com.perpeer.bitcointicker.utils.isMoreThanFiveMinutesAgo
import com.perpeer.bitcointicker.viewmodel.BitcoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bitcoinViewModel: BitcoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bitcoinViewModel.fetchCoinList(
            preferenceManager.coinsSaveTime?.isMoreThanFiveMinutesAgo() ?: false
        )

        setContent {
            MaterialTheme {
                AppNavigation(viewModel = bitcoinViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: BitcoinViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Coins.route) {
        composable(Screen.Coins.route) {
            HomeScreen(
                viewModel = viewModel,
                onCoinClick = { coin ->
                    navController.navigate(Screen.CoinDetail.route + Gson().toJson(coin))
                }
            )
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