package com.corposistemas.android_core.compose.dialog

import com.corposistemas.android_core.enums.DialogType

interface GlobalDialogControllerImpl {

    fun show(
        type: DialogType = DialogType.INFO,
        title: String = "",
        message: String = "",
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {}
    )

    fun hide()
}