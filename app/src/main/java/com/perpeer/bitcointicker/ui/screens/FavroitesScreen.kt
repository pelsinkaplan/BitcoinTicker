package com.perpeer.bitcointicker.ui.screens

/**
 * Created by Pelşin KAPLAN on 7.01.2025.
 */

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.components.FavoritesListItem
import com.perpeer.bitcointicker.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onCoinClick: (Coin) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val favorites by viewModel.favoriteCoins.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchFavoriteCoins()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                favorites.isEmpty() -> {
                    Text(
                        text = "No favorite coins added.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(favorites) { coin ->
                            FavoritesListItem(
                                item = coin,
                                onClick = onCoinClick,
                                onRemoveClick = { viewModel.removeCoinFromFavorites(coin.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

