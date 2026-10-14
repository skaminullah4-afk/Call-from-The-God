package com.callfromthegod.prayertimes

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView

class QuranActivity : Activity() {

    private val gold = Color.rgb(232, 198, 105)
    private val backgroundColor = Color.rgb(12, 12, 12)

    private lateinit var surahSpinner: Spinner
    private lateinit var quranText: TextView

    private val surahs = (1..114).toList()
    private val versesBySurah = mutableMapOf<Int, MutableList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = backgroundColor
        window.navigationBarColor = backgroundColor

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 20, 16, 24)
            setBackgroundColor(backgroundColor)
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
            setPadding(0, 0, 0, 16)
        }
        root.addView(subtitle)

        surahSpinner = Spinner(this)
        val surahNames = surahs.map { "Surah $it" }

        surahSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            surahNames
        ).apply {
            setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
            )
        }

        root.addView(
            surahSpinner,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 16
            }
        )

        val glass = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 24, 18, 24)
            background = GradientDrawable().apply {
                setColor(Color.argb(18, 255, 255, 255))
                setStroke(1, Color.argb(100, 232, 198, 105))
                cornerRadius = 24f
            }
        }

        quranText = TextView(this).apply {
            setTextColor(gold)
            textSize = 25f
            gravity = Gravity.CENTER
            textDirection = TextView.TEXT_DIRECTION_RTL
            typeface = Typeface.create("serif", Typeface.NORMAL)
            setLineSpacing(12f, 1.15f)
        }

        glass.addView(
            quranText,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        val scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(glass)
        }

        root.addView(
            scroll,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        setContentView(root)

        try {
            assets.open("quran-uthmani.txt")
                .bufferedReader(Charsets.UTF_8)
                .useLines { lines ->
                    lines.forEach { line ->
                        val firstSeparator = line.indexOf('|')
                        val secondSeparator =
                            if (firstSeparator >= 0) {
                                line.indexOf('|', firstSeparator + 1)
                            } else {
                                -1
                            }

                        if (firstSeparator > 0 &&
                            secondSeparator > firstSeparator
                        ) {
                            val surahNumber =
                                line.substring(0, firstSeparator).toIntOrNull()

                            val arabicText =
                                line.substring(secondSeparator + 1)

                            if (surahNumber in 1..114 &&
                                arabicText.isNotEmpty()
                            ) {
                                versesBySurah
                                    .getOrPut(surahNumber!!) {
                                        mutableListOf()
                                    }
                                    .add(arabicText)
                            }
                        }
                    }
                }

            showSurah(1)

            surahSpinner.onItemSelectedListener =
                object : android.widget.AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: android.widget.AdapterView<*>?,
                        view: android.view.View?,
                        position: Int,
                        id: Long
                    ) {
                        showSurah(surahs[position])
                    }

                    override fun onNothingSelected(
                        parent: android.widget.AdapterView<*>?
                    ) {}
                }

        } catch (e: Exception) {
            quranText.text =
                "Could not load Qur'an text.\nPlease check the installed text asset."
        }
    }

    private fun showSurah(number: Int) {
        val verses = versesBySurah[number]

        quranText.text = if (verses.isNullOrEmpty()) {
            "No verses found for Surah $number."
        } else {
            verses.joinToString("\n\n")
        }
    }
}
