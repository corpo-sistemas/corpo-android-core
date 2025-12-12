package com.corposistemas.android_core.compose.dialog

import com.corposistemas.android_core.enums.DialogType
import kotlinx.coroutines.flow.MutableStateFlow

class GlobalDialogControllerImpl : GlobalDialogController {

    data class GlobalDialogState(
        val visible: Boolean = false,
        val title: String = "",
        val message: String = "",
        val type: DialogType = DialogType.INFO,
        val onConfirm: () -> Unit = {},
        val onDismiss: () -> Unit = {}
    )

    val state = MutableStateFlow(GlobalDialogState())

    override fun show(
        title: String,
        message: String,
        type: DialogType,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        state.value = GlobalDialogState(
            visible = true,
            title = title,
            message = message,
            type = type,
            onConfirm = {
                onConfirm()
                hide()
            },
            onDismiss = {
                onDismiss()
                hide()
            }
        )
    }

    override fun hide() {
        state.value = GlobalDialogState()
    }
}