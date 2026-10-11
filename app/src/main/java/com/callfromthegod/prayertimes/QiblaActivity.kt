package com.callfromthegod.prayertimes

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class QiblaActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gold = Color.rgb(230, 190, 100)
        val background = Color.rgb(15, 15, 15)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 24, 24, 24)
            setBackgroundColor(background)
        }

        fun addText(
            text: String,
            size: Float,
            color: Int
        ) {
            val view = TextView(this).apply {
                this.text = text
                textSize = size
                setTextColor(color)
                gravity = Gravity.CENTER
                setPadding(8, 12, 8, 12)
            }
            layout.addView(view)
        }

        addText("قِبْلَة", 34f, gold)
        addText("Qibla Direction", 26f, gold)
        addText("🕋", 64f, gold)
        addText(
            "Your Qibla compass will appear here.",
            18f,
            Color.WHITE
        )
        addText(
            "Next, we will connect the phone's compass sensor.",
            14f,
            Color.LTGRAY
        )

        setContentView(layout)
    }
}

