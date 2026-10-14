package com.callfromthegod.prayertimes

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class QuranActivity : Activity() {

    private val gold = Color.rgb(232, 198, 105)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(12, 12, 12)
        window.navigationBarColor = Color.rgb(12, 12, 12)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 20, 16, 24)
            setBackgroundColor(Color.rgb(12, 12, 12))
        }

        val title = TextView(this).apply {
            text = "الْقُرْآنُ الْكَرِيمُ"
            textSize = 30f
            setTextColor(gold)
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 8, 0, 8)
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "THE HOLY QUR'AN"
            textSize = 13f
            setTextColor(gold)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 20)
        }
        root.addView(subtitle)

        val glass = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 24, 18, 24)
            background = GradientDrawable().apply {
                setColor(Color.argb(18, 255, 255, 255))
                setStroke(1, Color.argb(100, 232, 198, 105))
                cornerRadius = 24f
            }
        }

        val quranText = TextView(this).apply {
            setTextColor(gold)
            textSize = 25f
            gravity = Gravity.CENTER
            textDirection = TextView.TEXT_DIRECTION_RTL
            typeface = Typeface.create("serif", Typeface.NORMAL)
            setLineSpacing(12f, 1.15f)
        }

        try {
            val text = assets.open("quran-uthmani.txt")
                .bufferedReader(Charsets.UTF_8)
                .use { it.readText() }

            quranText.text = text
        } catch (e: Exception) {
            quranText.text =
                "Could not load Qur'an text.\nPlease check the installed text asset."
        }

        glass.addView(
            quranText,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        root.addView(
            glass,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        val scroll = ScrollView(this)
        scroll.addView(root)
        setContentView(scroll)
    }
}
