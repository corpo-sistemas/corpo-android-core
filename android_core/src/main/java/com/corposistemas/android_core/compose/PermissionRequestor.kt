package com.corposistemas.android_core.compose
import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.corposistemas.android_core.enums.PermissionState
import com.corposistemas.android_core.helpers.permissions.PermissionsHelper

@Composable
fun PermissionRequestor(content: @Composable () -> Unit) {
    val ctx = LocalContext.current
    var state by remember { mutableStateOf(PermissionState.UNKNOWN) }

    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            val isDenied = results.any { granted -> !granted.value }
            state = if (isDenied) PermissionState.DENIED else PermissionState.GRANTED
        }
    )

    LaunchedEffect(Unit) {
        val missingBlPerms = PermissionsHelper.getMissingBluetoothPermissions(ctx)
        if (missingBlPerms.isEmpty()) {
            state = PermissionState.GRANTED
            return@LaunchedEffect
        }
        multiplePermissionLauncher.launch(missingBlPerms.toTypedArray())
    }

    when (state) {
        PermissionState.UNKNOWN -> UnknownView()
        PermissionState.DENIED -> ForceCloseDialog(ctx)
        PermissionState.GRANTED -> content()
    }
}

@Composable
private fun UnknownView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }
    }
}


@Composable
private fun ForceCloseDialog(context: Context) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Permiso requerido") },
        text = {
            Text(
                """
                Necesitas activar los permisos de Bluetooth para que la aplicación funcione correctamente.
                Presiona el botón de aceptar para salir de la aplicación y volver a activar los permisos.
                """.trimIndent()
            )
        },
        confirmButton = {
            TextButton(onClick = {
                val activity = context as? Activity
                activity?.finish()
            }) {
                Text("Salir")
            }
        }
    )
}