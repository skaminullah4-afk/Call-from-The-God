package com.callfromthegod.prayertimes

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView

class QuranActivity : Activity() {

    private val gold = Color.rgb(232, 198, 105)
    private val dark = Color.rgb(12, 12, 12)
    private val versesBySurah =
        mutableMapOf<Int, MutableList<Pair<Int, String>>>()

    private lateinit var quranText: TextView
    private lateinit var scroll: ScrollView
    private var fontSize = 25f
    private val juzStarts = listOf(
    1 to (1 to 1),
    2 to (2 to 142),
    3 to (2 to 253),
    4 to (3 to 93),
    5 to (4 to 24),
    6 to (4 to 148),
    7 to (5 to 82),
    8 to (6 to 111),
    9 to (7 to 88),
    10 to (8 to 41),
    11 to (9 to 93),
    12 to (11 to 6),
    13 to (12 to 53),
    14 to (15 to 1),
    15 to (17 to 1),
    16 to (18 to 75),
    17 to (21 to 1),
    18 to (23 to 1),
    19 to (25 to 21),
    20 to (27 to 56),
    21 to (29 to 46),
    22 to (33 to 31),
    23 to (36 to 28),
    24 to (39 to 32),
    25 to (41 to 47),
    26 to (46 to 1),
    27 to (51 to 31),
    28 to (58 to 1),
    29 to (67 to 1),
    30 to (78 to 1)
)

    private val bismillah =
        "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"

    private fun arabicNumber(number: Int): String =
        number.toString().map {
            when (it) {
                '0' -> '٠'
                '1' -> '١'
                '2' -> '٢'
                '3' -> '٣'
                '4' -> '٤'
                '5' -> '٥'
                '6' -> '٦'
                '7' -> '٧'
                '8' -> '٨'
                else -> '٩'
            }
        }.joinToString("")

    private fun normalizeArabic(text: String): String =
        text.replace("ـ", "")
            .replace(Regex("[\\u064B-\\u065F\\u0670]"), "")
            .replace("ٱ", "ا")
            .trim()

    private fun beginsWithBismillah(text: String): Boolean {
    val normalized = normalizeArabic(text)
        .replace("ٱ", "ا")
        .replace("أ", "ا")
        .replace("إ", "ا")
        .replace("آ", "ا")
        .replace("ى", "ي")
        .replace("ة", "ه")
        .replace(Regex("\\s+"), " ")
        .trim()

    val normalizedBismillah = normalizeArabic(bismillah)
        .replace("ٱ", "ا")
        .replace("أ", "ا")
        .replace("إ", "ا")
        .replace("آ", "ا")
        .replace("ى", "ي")
        .replace("ة", "ه")
        .replace(Regex("\\s+"), " ")
        .trim()

    return normalized.startsWith(normalizedBismillah)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = dark
        window.navigationBarColor = dark

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 12, 16, 16)
            setBackgroundColor(dark)
        }

        val title = TextView(this).apply {
            text = "الْقُرْآنُ الْكَرِيمُ"
            textSize = 30f
            setTextColor(gold)
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setPadding(0, 8, 0, 4)
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "THE HOLY QUR'AN"
            textSize = 13f
            setTextColor(gold)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 12)
        }
        root.addView(subtitle)

        val spinner = Spinner(this).apply {
            setBackground(GradientDrawable().apply {
                setColor(Color.rgb(30, 30, 30))
                setStroke(1, gold)
                cornerRadius = 12f
            })

            setPopupBackgroundDrawable(GradientDrawable().apply {
                setColor(Color.rgb(30, 30, 30))
            })
        }
        
        val juzSpinner = Spinner(this).apply {
    setBackground(GradientDrawable().apply {
        setColor(Color.rgb(30, 30, 30))
        setStroke(1, gold)
        cornerRadius = 12f
    })

    setPopupBackgroundDrawable(GradientDrawable().apply {
        setColor(Color.rgb(30, 30, 30))
    })
        }

        val options = (1..114).map { "Surah $it" }

        val adapter = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            options
        ) {
            override fun getView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return (super.getView(position, convertView, parent)
                    as TextView).apply {
                    setTextColor(gold)
                    textSize = 18f
                    setPadding(12, 10, 12, 10)
                }
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                return TextView(this@QuranActivity).apply {
                    text = options[position]
                    textSize = 18f
                    setTextColor(gold)
                    setBackgroundColor(Color.rgb(30, 30, 30))
                    setPadding(20, 14, 20, 14)
                }
            }
        }

        spinner.adapter = adapter

        val juzAdapter = ArrayAdapter(
    this,
    android.R.layout.simple_spinner_item,
    (1..30).map { "Juz $it" }
).apply {
    setDropDownViewResource(
        android.R.layout.simple_spinner_dropdown_item
    )
}

juzSpinner.adapter = juzAdapter
        
        root.addView(
            spinner,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 8
            }
        )

        root.addView(
    juzSpinner,
    LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply {
        bottomMargin = 8
    }
)

        val fontControls = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 10)
        }

        fun makeFontButton(label: String): Button =
            Button(this).apply {
                text = label
                textSize = 18f
                setTextColor(gold)
                background = GradientDrawable().apply {
                    setColor(Color.rgb(30, 30, 30))
                    setStroke(1, gold)
                    cornerRadius = 12f
                }
                isAllCaps = false
            }

        val decreaseButton = makeFontButton("A−")
        val increaseButton = makeFontButton("A+")

        fontControls.addView(
            decreaseButton,
            LinearLayout.LayoutParams(100, 48).apply {
                rightMargin = 12
            }
        )
        fontControls.addView(
            increaseButton,
            LinearLayout.LayoutParams(100, 48)
        )
        root.addView(fontControls)

        increaseButton.setOnClickListener {
            if (fontSize < 44f) {
                fontSize += 2f
                quranText.textSize = fontSize
            }
        }

        decreaseButton.setOnClickListener {
            if (fontSize > 16f) {
                fontSize -= 2f
                quranText.textSize = fontSize
            }
        }

        val glass = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 18, 16, 22)
            background = GradientDrawable().apply {
                setColor(Color.argb(18, 255, 255, 255))
                setStroke(1, Color.argb(100, 232, 198, 105))
                cornerRadius = 24f
            }
        }

        quranText = TextView(this).apply {
            setTextColor(gold)
            textSize = fontSize
            gravity = Gravity.CENTER
            textDirection = View.TEXT_DIRECTION_RTL
            typeface = Typeface.create("serif", Typeface.NORMAL)
            setLineSpacing(8f, 1.1f)
        }

        glass.addView(
            quranText,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )

        scroll = ScrollView(this).apply {
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
                        val first = line.indexOf('|')
                        val second = if (first >= 0) {
                            line.indexOf('|', first + 1)
                        } else {
                            -1
                        }

                        if (first > 0 && second > first) {
                            val surah =
                                line.substring(0, first).toIntOrNull()
                            val ayah =
                                line.substring(first + 1, second).toIntOrNull()
                            val arabic = line.substring(second + 1)

                            if (surah != null &&
                                ayah != null &&
                                surah in 1..114 &&
                                arabic.isNotEmpty()
                            ) {
                                versesBySurah.getOrPut(surah) {
                                    mutableListOf()
                                }.add(ayah to arabic)
                            }
                        }
                    }
                }

            fun showSurah(number: Int) {
                val verses = versesBySurah[number]
                if (verses.isNullOrEmpty()) {
                    quranText.text = "No verses found for Surah $number."
                    return
                }

                val display = StringBuilder()
                display.append("سُورَةُ ${arabicNumber(number)}\n\n")

                var displayedVerses: List<Pair<Int, String>> = verses

                if (number == 1) {
                    // Al-Fatiha: show Bismillah separately.
                    display.append("$bismillah\n\n")

                    // The first source record is Bismillah.
                    displayedVerses = verses.drop(1)
                } else if (number != 9 &&
                    beginsWithBismillah(verses.first().second)
                ) {
                    // Move an existing Bismillah to the top.
                    display.append("$bismillah\n\n")

                    // Remove only the Bismillah prefix from the displayed
                    // first verse, leaving the source file unchanged.
                    val firstItem = verses.first()
val normalizedFirst = normalizeArabic(firstItem.second)
val normalizedBismillah = normalizeArabic(bismillah)

val remainingText = firstItem.second
    .replaceFirst(
        Regex("^\\s*بِسْمِ\\s+ٱللَّهِ\\s+ٱلرَّحْمَـٰنِ\\s+ٱلرَّحِيمِ\\s*"),
        ""
    )
    .trim()

displayedVerses = if (remainingText.isNotEmpty()) {
    listOf(firstItem.first to remainingText) + verses.drop(1)
} else {
    verses.drop(1)
}

                    displayedVerses = if (remainingText.isNotEmpty()) {
                        listOf(firstItem.first to remainingText) + verses.drop(1)
                    } else {
                        verses.drop(1)
                    }
                }

                displayedVerses.forEachIndexed { index, item ->
                    if (index > 0) display.append(" ")
                    display.append(item.second)

                    val ayahNumber = if (number == 1) {
                        index + 1
                    } else {
                        item.first
                    }

                    display.append(" ۝")
                    display.append(arabicNumber(ayahNumber))
                }

                quranText.text = display.toString()
                scroll.post { scroll.scrollTo(0, 0) }
            }

            showSurah(1)

            spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        showSurah(position + 1)
                    }

                    override fun onNothingSelected(
                        parent: AdapterView<*>?
                    ) {}
                }
               
            juzSpinner.onItemSelectedListener =
    object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            val start = juzStarts[position]
            showSurah(start.second.first)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

        } catch (e: Exception) {
            quranText.text = "Could not load Qur'an text."
        }
    }
}
