package com.corposistemas.android.core.helpers.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionsHelper {

    fun getMissingBluetoothPermissions(context: Context): List<String> {
        val targetSdk = context.applicationInfo.targetSdkVersion
        val requiredPermissions = getRequiredBluetoothPermissions(targetSdk)

        return requiredPermissions.filter { perm ->
            ActivityCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getRequiredBluetoothPermissions(targetSdkVersion: Int): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && targetSdkVersion >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && targetSdkVersion >= Build.VERSION_CODES.Q) {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        } else arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}