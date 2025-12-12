package com.corposistemas.android_core.compose.dialog

import com.corposistemas.android_core.enums.DialogType

interface GlobalDialogControllerImpl {

    fun show(
        type: DialogType = DialogType.INFO,
        message: String = "",
        title: String? = null,
        onConfirm: () -> Unit = {},
        onDismiss: () -> Unit = {}
    )

    fun hide()
}