package com.perpeer.bitcointicker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.components.CoinListItem
import com.perpeer.bitcointicker.viewmodel.BitcoinViewModel

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

@Composable
fun HomeScreen(viewModel: BitcoinViewModel, onCoinClick: (Coin) -> Unit) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCoinList by viewModel.filteredCoinList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { query -> viewModel.updateSearchQuery(query) },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        LazyColumn {
            items(filteredCoinList) { coin ->
                CoinListItem(coin = coin, onClick = { selectedCoin ->
                    onCoinClick(selectedCoin)
                })
            }
        }
    }
}

