package com.example.autoled.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.autoled.bluetooth.BLEManager

@Composable
fun AdjustScreen(
    bleManager: BLEManager,
    onNavigateToStyle: () -> Unit,
    onNavigateToBluetooth: () -> Unit
) {
    var brightness by remember { mutableFloatStateOf(100f) }
    var red by remember { mutableFloatStateOf(255f) }
    var green by remember { mutableFloatStateOf(255f) }
    var blue by remember { mutableFloatStateOf(255f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Brightness Slider
        Text("Adjust Brightness:")
        Slider(
            value = brightness,
            onValueChange = {
                brightness = it
                bleManager.sendData("BRIGHTNESS:${brightness.toInt()}")
            },
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Brightness: ${brightness.toInt()}%")

        // RGB Sliders
        Text("Adjust Color:")
        Text("Red: ${red.toInt()}")
        Slider(
            value = red,
            onValueChange = {
                red = it
                sendColorToBLE(bleManager, red, green, blue)
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Green: ${green.toInt()}")
        Slider(
            value = green,
            onValueChange = {
                green = it
                sendColorToBLE(bleManager, red, green, blue)
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Blue: ${blue.toInt()}")
        Slider(
            value = blue,
            onValueChange = {
                blue = it
                sendColorToBLE(bleManager, red, green, blue)
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )

        // Navigation Buttons
        Button(
            onClick = onNavigateToStyle,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Go to Style Screen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onNavigateToBluetooth) {
            Text("Go to Bluetooth Screen")
        }
    }
}

private fun sendColorToBLE(bleManager: BLEManager, red: Float, green: Float, blue: Float) {
    val colorHex = String.format("#%02X%02X%02X", red.toInt(), green.toInt(), blue.toInt())
    bleManager.sendData("COLOR:$colorHex")
}