package com.perpeer.bitcointicker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.perpeer.bitcointicker.data.cache.PreferenceManager.Companion.preferenceManager
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.components.FavouriteButton
import com.perpeer.bitcointicker.viewmodel.BitcoinViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    selectedCoin: Coin?, onBack: () -> Unit, viewModel: BitcoinViewModel = hiltViewModel(),
) {
    selectedCoin?.id?.let { viewModel.fetchCoinDetail(it) }
    val selectedCoinDetail by viewModel.coinDetail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = selectedCoin?.name ?: "Coin Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        selectedCoinDetail?.let { coin ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Coin Görseli
                coin.image.large.let {
                    GlideImage(
                        imageModel = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .size(200.dp, 200.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )
                }

                // Coin Temel Bilgiler
                Text(
                    text = "Name: ${coin.name}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Symbol: ${coin.symbol}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Hashing Algorithm: ${coin.hashing_algorithm ?: "Not available"}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Açıklama
                Text(
                    text = "Description:",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = coin.description.en ?: "No description available.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Piyasa Verileri
                coin.market_data?.let { marketData ->
                    Text(
                        text = "Current Price: ${marketData.current_price["usd"] ?: "N/A"} USD",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Price Change (24h): ${marketData.price_change_percentage_24h ?: "N/A"}%",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // Yenileme Aralığı Girişi
                var refreshInterval by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = refreshInterval,
                    onValueChange = { refreshInterval = it },
                    label = { Text("Refresh Interval (seconds)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                // Favorilere Ekle Butonu
                var isFavourite by remember {
                    mutableStateOf(preferenceManager.favoriteCoins?.favoriteCoinList?.any { it.id == selectedCoin?.id } == true)
                } // Favori durumu

                FavouriteButton(
                    isFavourite = isFavourite,
                    onFavouriteClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            selectedCoin?.let {
                                if (isFavourite) {
                                    preferenceManager.deleteFavoriteCoinWithId(it.id) // Favorilerden kaldır
                                } else {
                                    preferenceManager.addNewFavoriteCoin(it) // Favorilere ekle
                                }
                                isFavourite = !isFavourite // Durumu değiştir
                            }
                        }
                    }
                )
            }
        } ?: Text(
            text = "No details available.",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
