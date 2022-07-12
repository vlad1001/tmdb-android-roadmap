package com.example.moviestmdb.core_ui.util

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider

object CircularOutlineProvider: ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setOval(
            view.paddingLeft,
            view.paddingTop,
            view.width - view.paddingRight,
            view.height - view.paddingBottom
        )
    }
}