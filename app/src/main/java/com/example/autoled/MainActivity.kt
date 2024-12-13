package com.example.autoled

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.autoled.bluetooth.BLEManager
import com.example.autoled.navigation.AppNavigation
import com.example.autoled.ui.theme.AUTOLEDTheme

class MainActivity : ComponentActivity() {

    private lateinit var bleManager: BLEManager

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.all { it.value }) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bleManager = BLEManager(this)

        // Request Bluetooth permissions
        checkBluetoothPermissions()

        setContent {
            MainContent(bleManager)
        }
    }

    private fun checkBluetoothPermissions() {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )

        if (permissions.all { ContextCompat.checkSelfPermission(this, it) == android.content.pm.PackageManager.PERMISSION_GRANTED }) {
            return
        }
        permissionLauncher.launch(permissions)
    }
}

@Composable
fun MainContent(bleManager: BLEManager) {
    AUTOLEDTheme {
        Scaffold { innerPadding ->
            AppNavigation(
                modifier = Modifier.padding(innerPadding),
                bleManager = bleManager,
                onDeviceSelected = { device ->
                    bleManager.connectToDevice(device)
                }
            )
        }
    }
}