package com.example.autoled.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autoled.bluetooth.BLEManager

@Composable
fun BluetoothScreen(
    bleManager: BLEManager,
    onNavigateBack: () -> Unit,
    onDeviceSelected: (android.bluetooth.BluetoothDevice) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Bluetooth Screen")

        Spacer(modifier = Modifier.height(16.dp))

        // List paired devices or available BLE devices (Placeholder for BLE implementation)
        Text("Available Devices:")
        // Add logic here to scan for BLE devices and allow selection using `bleManager`

        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        Button(onClick = onNavigateBack) {
            Text("Back to Adjust Screen")
        }
    }
}