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
import androidx.compose.ui.unit.dp
import com.corposistemas.android_core.enums.DialogType


val LocalDialogController = staticCompositionLocalOf<GlobalDialogController> {
    error("DialogController not provided")
}

/**
 * Provides a globally accessible dialog controller instance to the current composition and
 * attaches a dialog host that renders dialogs triggered through that controller.
 *
 * This function installs a [GlobalDialogControllerImpl] into a [CompositionLocalProvider]
 * using [LocalDialogController]. Any composable within the subtree can obtain the controller
 * through `LocalDialogController.current` and trigger dialogs by invoking
 * [GlobalDialogController.show].
 *
 * The dialog host ([GlobalDialogHost]) is included automatically and is rendered above the
 * provided [content], ensuring that dialogs appear regardless of the current UI screen or
 * navigation state.
 *
 * Typical usage:
 *
 * ```kotlin
 * setContent {
 *     ProvideGlobalDialogController {
 *         MyAppScreen()
 *     }
 * }
 * ```
 *
 * Inside any composable within this scope:
 *
 * ```kotlin
 * val dialog = LocalDialogController.current
 *
 * Button(onClick = {
 *     dialog.show(
 *         title = "Confirm Action",
 *         message = "Are you sure you want to continue?",
 *         onConfirm = { /* handle confirmation */ },
 *         onDismiss = { /* handle dismissal */ }
 *     )
 * }) {
 *     Text("Open Dialog")
 * }
 * ```
 *
 * @param controller The dialog controller instance to expose to the composition.
 *                   Defaults to a remembered instance of [GlobalDialogControllerImpl].
 * @param content The UI hierarchy that should have access to the global dialog controller.
 */
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