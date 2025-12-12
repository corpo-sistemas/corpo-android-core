package com.corposistemas.android_core.compose.dialog

import com.corposistemas.android_core.enums.DialogType

interface GlobalAlertDialogController {

    fun show(
        title: String,
        message: String,
        type: DialogType = DialogType.INFO,
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {}
    )

    fun hide()
}