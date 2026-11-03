package com.monopalette.launcher

import android.app.Activity
import android.os.Bundle
import android.graphics.Color

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(20, 20, 20)
        window.navigationBarColor = Color.BLACK
        setContentView(AppDrawer(this))
    }
}
