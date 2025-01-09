package com.perpeer.bitcointicker.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.perpeer.bitcointicker.ui.navigation.Screen

/**
 * Created by Pelşin KAPLAN on 7.01.2025.
 */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Coins, Screen.Favorites) // Alt menüde gösterilecek ekranlar
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    BottomNavigation(
        modifier = Modifier
            .height(60.dp)
    ) {
        items.forEach { screen ->
            BottomNavigationItem(
                label = {
                    Text(
                        screen.name,
                        modifier = Modifier.padding(top = 4.dp),
                        color = if (currentRoute == screen.route) Color.White else Color.Gray
                    )
                },
                icon = {
                    Icon(
                        imageVector = screen.icon
                            ?: Icons.Rounded.Home, // İstediğiniz ikonları özelleştirin
                        contentDescription = screen.name,
                        tint = if (currentRoute == screen.route) Color.White else Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                },
                selected = currentRoute == screen.route, // Seçim durumunu kontrol ediyoruz
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate((screen.route)) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}