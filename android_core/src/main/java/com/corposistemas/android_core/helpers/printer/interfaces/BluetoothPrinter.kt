package com.corposistemas.android_core.helpers.printer.interfaces

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

@SuppressLint("MissingPermission")
object BluetoothPrinter {

    // The below uuid is the classic Bluetooth serial communication service,
    val PRINTER_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun getDeviceNames(context: Context): List<Pair<String, String>> {
        val devices = getPairedDevices(context)
        return devices.filterNotNull().map { Pair(it.address, it.name) }
    }

    /**
     *  Get the device with the given name.
     */
    fun getCurrentDevice(context: Context, deviceAddress: String?): BluetoothDevice? {
        val devices = getPairedDevices(context)
        return devices.firstOrNull { it?.address.equals(deviceAddress, ignoreCase = true) }
    }


    /**
     *  start the printing process
     */
    suspend fun startPrintingProcess(device: BluetoothDevice, lines: List<String>) {

        return withContext(Dispatchers.IO) {
            val textToPrint = lines.joinToString("\n") + "\n".repeat(5)
            val socket = device.createRfcommSocketToServiceRecord(PRINTER_UUID)
            socket.connect()
            val outputStream = socket.outputStream
            outputStream.write(textToPrint.normalized().toByteArray())
            outputStream.flush()

            delay(300L)

            outputStream.close()
            socket.close()
        }
    }

    /**
     *  Get a set containing the paired devices.
     */
    private fun getPairedDevices(context: Context): Set<BluetoothDevice?> {
        val blManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = blManager.adapter
        return adapter.bondedDevices
    }
}