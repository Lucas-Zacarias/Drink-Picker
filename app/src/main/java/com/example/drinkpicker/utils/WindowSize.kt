package com.example.drinkpicker.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf


/*
* Values obtained from
* https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes
*/

data class WindowSize(
    val width: WindowType,
    val height: WindowType
)

enum class WindowType { Compact, Medium, Expanded }

@Composable
fun rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidth by remember{
        mutableStateOf(configuration.screenWidthDp)
    }
    val screenHeight by remember{
        mutableStateOf(configuration.screenHeightDp)
    }

    Log.i("Device width", "$screenWidth")
    Log.i("Device height","$screenHeight")

    return WindowSize(
        width = getScreenWidth(screenWidth),
        height = getScreenHeight(screenHeight)
    )
}

fun getScreenWidth(width: Int): WindowType = when {
    width < 600 -> WindowType.Compact
    width < 840 -> WindowType.Medium
    else -> WindowType.Expanded
}

fun getScreenHeight(height: Int): WindowType = when {
    height < 480 -> WindowType.Compact
    height < 900 -> WindowType.Medium
    else -> WindowType.Expanded
}
