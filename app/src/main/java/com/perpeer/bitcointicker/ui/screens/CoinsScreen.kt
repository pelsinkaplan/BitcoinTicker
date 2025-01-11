package com.perpeer.bitcointicker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.perpeer.bitcointicker.R
import com.perpeer.bitcointicker.data.cache.PreferenceManager.Companion.preferenceManager
import com.perpeer.bitcointicker.data.model.Coin
import com.perpeer.bitcointicker.ui.components.CoinListItem
import com.perpeer.bitcointicker.ui.components.CustomTextField
import com.perpeer.bitcointicker.utils.isMoreThanFiveMinutesAgo
import com.perpeer.bitcointicker.viewmodel.CoinsViewModel
import java.time.LocalDateTime

/**
 * Created by Pelşin KAPLAN on 6.01.2025.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsScreen(
    viewModel: CoinsViewModel = hiltViewModel(),
    onCoinClick: (Coin) -> Unit,
    signOut: () -> Unit
) {
    val fetchFromAPI = preferenceManager.coinsSaveTime.isMoreThanFiveMinutesAgo()

    // Listeyi sadece ilk kez oluşturulduğunda veya API çağrılması gerektiğinde getir
    LaunchedEffect(fetchFromAPI) {
        viewModel.fetchCoinList(fetchFromAPI)
        if (fetchFromAPI) preferenceManager.coinsSaveTime = LocalDateTime.now()
    }

    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredCoinList by viewModel.filteredCoinList.collectAsState()
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                TopAppBar(
                    modifier = Modifier.weight(1f),
                    title = {
                        Text(
                            text = "Bitcoin Ticker",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "App Icon",
                            Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }
                )
                Icon(
                    imageVector = Icons.Rounded.ExitToApp,
                    contentDescription = "Exit App Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable {
                            viewModel.signOut()
                            signOut()
                        }
                )
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            CustomTextField(
                value = searchQuery,
                onValueChange = { query -> viewModel.updateSearchQuery(query) },
                label = "Search",
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn {
                items(filteredCoinList) { coin ->
                    CoinListItem(item = coin, onClick = { selectedCoin ->
                        onCoinClick(selectedCoin)
                    })
                }
            }
        }
    }
}

