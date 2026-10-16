package com.callfromthegod.prayertimes

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class QuranSelectionActivity : Activity() {

    private val gold = Color.rgb(232, 198, 105)
private val dark = Color.rgb(12, 12, 12)
private val cardDark = Color.rgb(22, 20, 16)
private val ink = Color.rgb(245, 222, 165)

    private lateinit var list: LinearLayout
    private lateinit var surahTab: TextView
    private lateinit var parahTab: TextView

    private var showingParah = false

    private val surahNames = listOf(
        "الْفَاتِحَة", "الْبَقَرَة", "آلِ عِمْرَان", "النِّسَاء",
        "الْمَائِدَة", "الْأَنْعَام", "الْأَعْرَاف", "الْأَنْفَال",
        "التَّوْبَة", "يُونُس", "هُود", "يُوسُف", "الرَّعْد",
        "إِبْرَاهِيم", "الْحِجْر", "النَّحْل", "الْإِسْرَاء",
        "الْكَهْف", "مَرْيَم", "طَه", "الْأَنْبِيَاء", "الْحَجّ",
        "الْمُؤْمِنُون", "النُّور", "الْفُرْقَان", "الشُّعَرَاء",
        "النَّمْل", "الْقَصَص", "الْعَنْكَبُوت", "الرُّوم",
        "لُقْمَان", "السَّجْدَة", "الْأَحْزَاب", "سَبَأ",
        "فَاطِر", "يس", "الصَّافَّات", "ص", "الزُّمَر",
        "غَافِر", "فُصِّلَت", "الشُّورَى", "الزُّخْرُف",
        "الدُّخَان", "الْجَاثِيَة", "الْأَحْقَاف", "مُحَمَّد",
        "الْفَتْح", "الْحُجُرَات", "ق", "الذَّارِيَات",
        "الطُّور", "النَّجْم", "الْقَمَر", "الرَّحْمَٰن",
        "الْوَاقِعَة", "الْحَدِيد", "الْمُجَادِلَة", "الْحَشْر",
        "الْمُمْتَحَنَة", "الصَّف", "الْجُمُعَة", "الْمُنَافِقُون",
        "التَّغَابُن", "الطَّلَاق", "التَّحْرِيم", "الْمُلْك",
        "الْقَلَم", "الْحَاقَّة", "الْمَعَارِج", "نُوح",
        "الْجِنّ", "الْمُزَّمِّل", "الْمُدَّثِّر", "الْقِيَامَة",
        "الْإِنْسَان", "الْمُرْسَلَات", "النَّبَأ", "النَّازِعَات",
        "عَبَسَ", "التَّكْوِير", "الْإِنْفِطَار", "الْمُطَفِّفِين",
        "الْإِنْشِقَاق", "الْبُرُوج", "الطَّارِق", "الْأَعْلَى",
        "الْغَاشِيَة", "الْفَجْر", "الْبَلَد", "الشَّمْس",
        "اللَّيْل", "الضُّحَى", "الشَّرْح", "التِّين",
        "الْعَلَق", "الْقَدْر", "الْبَيِّنَة", "الزَّلْزَلَة",
        "الْعَادِيَات", "الْقَارِعَة", "التَّكَاثُر", "الْعَصْر",
        "الْهُمَزَة", "الْفِيل", "قُرَيْش", "الْمَاعُون",
        "الْكَوْثَر", "الْكَافِرُون", "النَّصْر", "الْمَسَدّ",
        "الْإِخْلَاص", "الْفَلَق", "النَّاس"
    )

    private val parahNames = listOf(
        "الم", "سَيَقُولُ", "تِلْكَ الرُّسُلُ", "لَنْ تَنَالُوا",
        "وَالْمُحْصَنَاتُ", "لَا يُحِبُّ اللَّهُ", "وَإِذَا سَمِعُوا",
        "وَلَوْ أَنَّنَا", "قَالَ الْمَلَأُ", "وَاعْلَمُوا",
        "يَعْتَذِرُونَ", "وَمَا مِنْ دَابَّةٍ", "وَمَا أُبَرِّئُ",
        "رُبَمَا", "سُبْحَانَ الَّذِي", "قَالَ أَلَمْ",
        "اقْتَرَبَ", "قَدْ أَفْلَحَ", "وَقَالَ الَّذِينَ",
        "أَمَّنْ خَلَقَ", "اتْلُ مَا أُوحِيَ", "وَمَنْ يَقْنُتْ",
        "وَمَا لِيَ", "فَمَنْ أَظْلَمُ", "إِلَيْهِ يُرَدُّ",
        "حم", "قَالَ فَمَا خَطْبُكُمْ", "قَدْ سَمِعَ اللَّهُ",
        "تَبَارَكَ الَّذِي", "عَمَّ"
    )

    private val colors = listOf(purple, green, blue, pink)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = dark
        window.navigationBarColor = dark

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(dark)
        }

        val tabs = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        surahTab = makeTab("سورة", true)
        parahTab = makeTab("پارہ", false)

        tabs.addView(surahTab, LinearLayout.LayoutParams(0, dp(64), 1f))
        tabs.addView(parahTab, LinearLayout.LayoutParams(0, dp(64), 1f))
        root.addView(tabs)

        val scroll = ScrollView(this)
        list = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(8), dp(8), dp(8), dp(16))
        }

        scroll.addView(list, ViewGroup.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.WRAP_CONTENT
))

        root.addView(scroll, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        setContentView(root)

        surahTab.setOnClickListener { showList(false) }
        parahTab.setOnClickListener { showList(true) }

        showList(false)
    }

    private fun makeTab(label: String, selected: Boolean) =
        TextView(this).apply {
            text = label
            textSize = 20f
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(if (selected) Color.WHITE else ink)
            setBackgroundColor(if (selected) purple else Color.WHITE)
        }

    private fun showList(parah: Boolean) {
        showingParah = parah

        surahTab.setBackgroundColor(if (!parah) purple else Color.WHITE)
        parahTab.setBackgroundColor(if (parah) purple else Color.WHITE)
        surahTab.setTextColor(if (!parah) Color.WHITE else ink)
        parahTab.setTextColor(if (parah) Color.WHITE else ink)

        list.removeAllViews()

        val names = if (parah) parahNames else surahNames

        names.forEachIndexed { index, name ->
            val accent = colors[index % colors.size]

            val card = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(14), dp(12), dp(12), dp(12))
                background = GradientDrawable().apply {
                    setColor(Color.WHITE)
                    setStroke(dp(1), Color.rgb(225, 225, 225))
                    cornerRadius = dp(22).toFloat()
                }
            }

            val number = TextView(this).apply {
                text = (index + 1).toString()
                textSize = 20f
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)
                background = GradientDrawable().apply {
                    setColor(accent)
                    cornerRadius = dp(24).toFloat()
                }
            }

            val arabic = TextView(this).apply {
                text = name
                textSize = 27f
                gravity = Gravity.CENTER
                setTextColor(ink)
                typeface = Typeface.create("serif", Typeface.NORMAL)
                textDirection = View.TEXT_DIRECTION_RTL
                setPadding(dp(8), dp(8), dp(8), dp(8))
            }

            card.addView(arabic, LinearLayout.LayoutParams(0, dp(64), 1f))
            card.addView(number, LinearLayout.LayoutParams(dp(56), dp(56)))

            card.setOnClickListener {
                val intent = android.content.Intent(
                    this, QuranActivity::class.java
                )
                intent.putExtra("selection_type", if (parah) "parah" else "surah")
                intent.putExtra("selection_number", index + 1)
                startActivity(intent)
            }

            list.addView(card, LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(12)
            })
        }
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
