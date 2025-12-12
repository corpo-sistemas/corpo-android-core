package com.corposistemas.android_core.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.corposistemas.android_core.enums.DialogType


val LocalDialogController = staticCompositionLocalOf<GlobalDialogController> {
    error("DialogController not provided")
}

@Composable
fun ProvideGlobalDialogController(
    controller: GlobalDialogControllerImpl = remember { GlobalDialogControllerImpl() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDialogController provides controller) {
        Box {
            content()
            GlobalDialogHost(controller = controller)
        }
    }
}

@Composable
private fun GlobalDialogHost(controller: GlobalDialogControllerImpl) {
    val state by controller.state.collectAsState()
    if (!state.visible) return

    val (icon, tint) = when (state.type) {
        DialogType.INFO -> Icons.Default.Info to MaterialTheme.colorScheme.primary
        DialogType.SUCCESS -> Icons.Default.Check to MaterialTheme.colorScheme.tertiary
        DialogType.WARNING -> Icons.Default.Warning to MaterialTheme.colorScheme.secondary
        DialogType.ERROR -> Icons.Default.Close to MaterialTheme.colorScheme.error
    }

    AlertDialog(
        onDismissRequest = state.onDismiss,
        title = {
            Text(text = state.title)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier
                        .size(25.dp)
                )
                Text(state.title)
            }
        },
        text = {
            Text(text = state.message)
        },
        confirmButton = {
            TextButton(onClick = state.onConfirm) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = state.onDismiss) {
                Text("Cancelar")
            }
        }
    )
}