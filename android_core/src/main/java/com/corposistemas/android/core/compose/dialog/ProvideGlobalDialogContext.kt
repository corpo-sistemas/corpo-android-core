package com.corposistemas.android.core.compose.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import com.corposistemas.android.core.enums.DialogType


val LocalDialogContext = staticCompositionLocalOf<GlobalDialogControllerImpl> {
    error("DialogController not provided")
}

@Composable
fun ProvideGlobalDialogContext(
    controller: GlobalDialogController = remember { GlobalDialogController() },
    content: @Composable () -> Unit
) {

    CompositionLocalProvider(LocalDialogContext provides controller) {
        Box {
            content()
            GlobalDialogHost(controller = controller)
        }
    }
}

@Composable
private fun GlobalDialogHost(controller: GlobalDialogController) {
    val state by controller.state.collectAsState()
    if (!state.visible) return

    AlertDialog(
        onDismissRequest = {
            if (state.type == DialogType.LOADING) return@AlertDialog
            state.onDismiss()
        },
        title = {
            GlobalDialogTitle(state.title, state.type)
        },
        text = {
            GlobalDialogBody(state.message, state.type)
        },
        confirmButton = {
            if (state.type == DialogType.LOADING) return@AlertDialog
            TextButton(onClick = state.onConfirm) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            if (state.type != DialogType.CONFIRM) return@AlertDialog
            TextButton(onClick = state.onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun GlobalDialogTitle(title: String, dialogType: DialogType) {
    val (icon, tint) = when (dialogType) {
        DialogType.INFO -> Icons.Default.Info to MaterialTheme.colorScheme.primary
        DialogType.SUCCESS -> Icons.Default.Check to MaterialTheme.colorScheme.tertiary
        DialogType.WARNING -> Icons.Default.Warning to MaterialTheme.colorScheme.secondary
        DialogType.ERROR -> Icons.Default.Close to MaterialTheme.colorScheme.error
        DialogType.CONFIRM -> Icons.Default.QuestionMark to MaterialTheme.colorScheme.primary
        DialogType.LOADING -> null to null
    }
    if (icon == null || tint == null) return
    if (dialogType == DialogType.LOADING) return

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(25.dp))
        Text(title)
    }
}

@Composable
private fun GlobalDialogBody(message: String, dialogType: DialogType) {
    when (dialogType) {
        DialogType.LOADING -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Cargando...")
            }
        }

        else -> {
            Text(text = message)
        }
    }
}