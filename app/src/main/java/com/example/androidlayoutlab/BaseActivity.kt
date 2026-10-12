package com.example.androidlayoutlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat

open class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
    }
}
