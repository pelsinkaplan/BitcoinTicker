package com.perpeer.bitcointicker.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.components.LineChartView
import com.perpeer.bitcointicker.viewmodel.CoinDetailViewModel
import com.skydoves.landscapist.glide.GlideImage
import java.text.DecimalFormat


/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    selectedCoin: Coin, onBack: () -> Unit, viewModel: CoinDetailViewModel = hiltViewModel(),
) {
    var refreshInterval by remember { mutableStateOf<Long>(30) }
    var daysInterval by remember { mutableStateOf<Int?>(15) }
    var hoursInterval by remember { mutableStateOf<Int?>(null) }
    val selectedCoinDetail by viewModel.coinDetail.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val marketData by viewModel.marketData.collectAsState()

    LaunchedEffect(selectedCoin.id, daysInterval, hoursInterval) {
        viewModel.fetchCoinDetail(selectedCoin.id)
        viewModel.checkItemIsFavorite(selectedCoin.id)
        daysInterval?.let { viewModel.fetchMarketDataDaily(selectedCoin.id, it) }
        hoursInterval?.let { viewModel.fetchMarketDataHourly(selectedCoin.id, it) }
    }

    DisposableEffect(refreshInterval) {
        val interval = refreshInterval
        if (interval != null && interval > 0) {
            viewModel.startAutoRefresh(selectedCoin.id, interval)
        } else {
            viewModel.stopAutoRefresh()
        }
        onDispose { viewModel.stopAutoRefresh() }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleFavorite(selectedCoin) },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
            ) {
                // favorite button; if the coin added to favorites, icon shows filled favorite icon else outlined favorite icon
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Remove or Add from Favourites",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End, // Sağ alt köşe
        topBar = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TopAppBar(
                    modifier = Modifier.weight(1f),
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            selectedCoinDetail?.let { coin ->
                                // Coin Görseli
                                coin.image.large.let {
                                    GlideImage(
                                        imageModel = it,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(30.dp, 30.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedCoin.name + "(${selectedCoin.symbol})",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }

                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
                // Piyasa Verileri
                selectedCoinDetail?.let { coin ->
                    coin.market_data.let { marketData ->
                        val priceFormatter =
                            DecimalFormat("#,###.######") // Customize decimal places as needed

                        val currentPrice = marketData.current_price["usd"]?.let {
                            priceFormatter.format(it)
                        } ?: "N/A"

                        val priceChange = marketData.price_change_percentage_24h.let {
                            priceFormatter.format(it)
                        } ?: "N/A"

                        Column(
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text(
                                text = "$$currentPrice",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "$priceChange%",
                                color = if (marketData.price_change_percentage_24h < 0) Color.Red else Color.Green,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                    }
                }
            }

        }
    ) { innerPadding ->
        selectedCoinDetail?.let { coin ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Price Changes in Last 30 Days",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (marketData.isNotEmpty()) {

                    LineChartView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        chartData = marketData
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val dailyButtonList = listOf(1, 7, 15, 30)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                daysInterval = null
                                hoursInterval = 1
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (hoursInterval == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                contentColor = if (hoursInterval == 1) Color.White else MaterialTheme.colorScheme.onSurface

                            ), border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Text("1H")
                        }
                        dailyButtonList.forEach {
                            Button(
                                onClick = {
                                    hoursInterval = null
                                    daysInterval = it
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (daysInterval == it) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                                    contentColor = if (daysInterval == it) Color.White else MaterialTheme.colorScheme.onSurface

                                ), border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            ) {
                                Text("${it}D")
                            }
                        }
                    }
                } else {
                    Text(text = "Loading data...", modifier = Modifier.padding(16.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hashing Algorithm: ${coin.hashing_algorithm ?: "Not available"}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                // Açıklama
                Text(
                    text = "About:",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = coin.description.en ?: "No description available.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                RefreshIntervalSlider(
                    modifier = Modifier.padding(bottom = 32.dp),
                    currentValue = refreshInterval,
                    onValueChange = { refreshInterval = it },
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
        } ?: Text(
            text = "No details available.",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RefreshIntervalSlider(
    modifier: Modifier = Modifier,
    currentValue: Long,
    onValueChange: (Long) -> Unit
) {
    var sliderValue by remember { mutableStateOf(currentValue.toFloat()) }
    var isDragging by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Refresh Interval: ${sliderValue.toLong()} seconds",
            style = MaterialTheme.typography.bodyLarge
        )

        Slider(
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                isDragging = true // Indicate that the user is dragging the slider
            },
            onValueChangeFinished = {
                isDragging = false // Dragging is complete
                onValueChange(sliderValue.toLong()) // Commit the final value
            },
            valueRange = 5f..60f, // Allow intervals from 5 to 60 seconds
            steps = 10 // Number of steps in the slider (optional)
        )
        if (isDragging) {
            Text(
                text = "Adjusting interval...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}




