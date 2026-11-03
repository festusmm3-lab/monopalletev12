package com.monopalette.launcher

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class AppDrawer(context: android.content.Context) : ScrollView(context) {
    init {
        setBackgroundColor(Color.rgb(20, 20, 20))

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 35, 20, 20)
        }

        val title = TextView(context).apply {
            text = "MonoPalette"
            textSize = 28f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        val subtitle = TextView(context).apply {
            text = "Your apps"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            setPadding(0, 8, 0, 24)
        }

        container.addView(title)
        container.addView(subtitle)

        val grid = GridLayout(context).apply {
            columnCount = 4
        }

        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val apps = context.packageManager
            .queryIntentActivities(intent, 0)
            .mapNotNull { info ->
                val activity = info.activityInfo ?: return@mapNotNull null
                if (activity.packageName == context.packageName) return@mapNotNull null

                AppInfo(
                    name = info.loadLabel(context.packageManager).toString(),
                    packageName = activity.packageName,
                    activityName = activity.name,
                    icon = info.loadIcon(context.packageManager)
                )
            }
            .distinctBy { it.packageName }
            .sortedBy { it.name.lowercase() }

        apps.forEach { app ->
            val tile = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(4, 12, 4, 12)
                background = GradientDrawable().apply {
                    setColor(Color.rgb(38, 38, 38))
                    cornerRadius = 18f
                }
            }

            val icon = ImageView(context).apply {
                setImageDrawable(app.icon)
                scaleType = ImageView.ScaleType.FIT_CENTER
                layoutParams = LinearLayout.LayoutParams(58, 58)
                IconHelper.applyMonochrome(this)
            }

            val label = TextView(context).apply {
                text = app.name
                textSize = 11f
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                maxLines = 2
                setPadding(2, 8, 2, 0)
            }

            tile.addView(icon)
            tile.addView(label)

            tile.setOnClickListener {
                val launchIntent = Intent().apply {
                    setClassName(app.packageName, app.activityName)
                }
                context.startActivity(launchIntent)
            }

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(5, 5, 5, 5)
            }

            grid.addView(tile, params)
        }

        container.addView(grid)
        addView(container)
    }
}
