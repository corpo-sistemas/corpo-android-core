package com.corposistemas.android_core.compose.dialog

import com.corposistemas.android_core.enums.DialogType
import kotlinx.coroutines.flow.MutableStateFlow

class GlobalDialogController : GlobalDialogControllerImpl {

    data class GlobalDialogState(
        val visible: Boolean = false,
        val message: String = "",
        val title: String = "",
        val type: DialogType = DialogType.INFO,
        val onConfirm: () -> Unit = {},
        val onDismiss: () -> Unit = {}
    )

    val state = MutableStateFlow(GlobalDialogState())

    override fun show(
        type: DialogType,
        message: String,
        title: String?,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit
    ) {
        state.value = GlobalDialogState(
            visible = true,
            title = title ?: type.value,
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