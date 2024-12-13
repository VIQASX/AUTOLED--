package com.example.autoled.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autoled.screens.AdjustScreen
import com.example.autoled.screens.BluetoothScreen
import com.example.autoled.screens.StyleScreen
import com.example.autoled.bluetooth.BLEManager

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    bleManager: BLEManager,
    onDeviceSelected: (device: android.bluetooth.BluetoothDevice) -> Unit
) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "adjust"
    ) {
        // Adjust Screen
        composable("adjust") {
            AdjustScreen(
                bleManager = bleManager,
                onNavigateToStyle = { navController.navigate("style") },
                onNavigateToBluetooth = { navController.navigate("bluetooth") }
            )
        }

        // Style Screen
        composable("style") {
            StyleScreen(
                bleManager = bleManager,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Bluetooth Screen
        composable("bluetooth") {
            BluetoothScreen(
                bleManager = bleManager,
                onNavigateBack = { navController.popBackStack() },
                onDeviceSelected = onDeviceSelected
            )
        }
    }
}