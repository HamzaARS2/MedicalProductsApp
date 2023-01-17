package com.ars.groceriesapp.utils

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun hideNavigationBars(window: Window, view: View, hide: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowInsetsControllerCompat(window, view).let { controller ->
            if (hide) {
                controller.hide(WindowInsetsCompat.Type.navigationBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            else
                controller.show(WindowInsetsCompat.Type.navigationBars())
        }
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

}

fun setBlackStatusBarIcons(window: Window) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.decorView.windowInsetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
}