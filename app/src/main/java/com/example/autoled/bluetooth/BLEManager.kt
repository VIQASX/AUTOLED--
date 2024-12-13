package com.example.autoled.bluetooth

import android.Manifest
import android.bluetooth.*
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*

class BLEManager(private val context: Context) {
    private var bluetoothGatt: BluetoothGatt? = null

    companion object {
        private const val TAG = "BLEManager"
        const val SERVICE_UUID = "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
        const val CHARACTERISTIC_UUID = "beb5483e-36e1-4688-b7f5-ea07361b26a8"
    }

    fun connectToDevice(device: BluetoothDevice) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "BLUETOOTH_CONNECT permission not granted.")
            return
        }

        bluetoothGatt = device.connectGatt(context, false, object : BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        Log.d(TAG, "Connected to GATT server.")
                        gatt.discoverServices()
                    }
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        Log.d(TAG, "Disconnected from GATT server.")
                        bluetoothGatt?.close()
                        bluetoothGatt = null
                    }
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    val service = gatt.getService(UUID.fromString(SERVICE_UUID))
                    val characteristic = service?.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID))
                    if (characteristic != null) {
                        enableNotifications(gatt, characteristic)
                    } else {
                        Log.e(TAG, "Characteristic not found.")
                    }
                } else {
                    Log.e(TAG, "Failed to discover services: $status")
                }
            }

            override fun onCharacteristicWrite(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, status: Int) {
                Log.d(TAG, "Write completed with status: $status")
            }
        })
    }

    private fun enableNotifications(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "BLUETOOTH_CONNECT permission not granted.")
            return
        }

        gatt.setCharacteristicNotification(characteristic, true)
        val descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"))
        descriptor?.let {
            it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            gatt.writeDescriptor(it)
        }
    }

    fun sendData(data: String) {
        val service = bluetoothGatt?.getService(UUID.fromString(SERVICE_UUID))
        val characteristic = service?.getCharacteristic(UUID.fromString(CHARACTERISTIC_UUID))
        characteristic?.let {
            it.value = data.toByteArray()
            bluetoothGatt?.writeCharacteristic(it)
        }
    }

    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}
