package com.example.autoled.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoled.bluetooth.BLEManager

@Composable
fun StyleScreen(
    bleManager: BLEManager,
    onNavigateBack: () -> Unit
) {
    var speed by remember { mutableFloatStateOf(50f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Style Screen", style = MaterialTheme.typography.headlineMedium)

            // Buttons for different lighting effects
            Button(
                onClick = { bleManager.sendData("EFFECT:RAINBOW") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Rainbow Effect")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:CHASE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Chase Effect")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:STROBE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Strobe Effect")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:INWARD_RGB_CHASE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Inward RGB Chase")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:OUTWARD_RGB_CHASE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Outward RGB Chase")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:WAVE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Wave Effect")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:TWINKLE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Twinkle Effect")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:BREATHING") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Breathing Effect")
            }

            Button(
                onClick = { bleManager.sendData("EFFECT:SPARKLE") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sparkle Effect")
            }

            // Back to Adjust Screen Button
            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Adjust Screen")
            }
        }

        // Speed Slider
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Adjust Speed:")
            Slider(
                value = speed,
                onValueChange = {
                    speed = it
                    bleManager.sendData("SPEED:${speed.toInt()}")
                },
                valueRange = 0f..100f,
                modifier = Modifier.fillMaxWidth()
            )
            Text("Speed: ${speed.toInt()}%")
        }
    }
}