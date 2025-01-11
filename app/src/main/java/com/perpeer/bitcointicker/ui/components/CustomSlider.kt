package com.perpeer.bitcointicker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Pelşin KAPLAN on 9.01.2025.
 */
@Composable
fun CustomSlider(
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
            text = "Refresh Interval: ${sliderValue.toLong()} minutes",
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
            valueRange = 15f..60f, // Allow intervals from 5 to 60 seconds
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