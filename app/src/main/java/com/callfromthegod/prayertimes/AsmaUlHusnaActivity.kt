package com.callfromthegod.prayertimes

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class AsmaUlHusnaActivity : Activity() {
    private val gold = Color.rgb(212, 175, 98)
    private val ivory = Color.rgb(247, 240, 223)
    private val black = Color.rgb(18, 18, 18)

    private data class Name(val arabic: String, val transliteration: String, val meaning: String)

    private val names = listOf(
        Name("الرَّحْمَنُ","Ar-Rahman","The Most Compassionate"),
        Name("الرَّحِيمُ","Ar-Raheem","The Most Merciful"),
        Name("الْمَلِكُ","Al-Malik","The King"),
        Name("الْقُدُّوسُ","Al-Quddus","The Most Holy"),
        Name("السَّلَامُ","As-Salam","The Source of Peace"),
        Name("الْمُؤْمِنُ","Al-Mu'min","The Giver of Security"),
        Name("الْمُهَيْمِنُ","Al-Muhaymin","The Guardian"),
        Name("الْعَزِيزُ","Al-Aziz","The Almighty"),
        Name("الْجَبَّارُ","Al-Jabbar","The Compeller"),
        Name("الْمُتَكَبِّرُ","Al-Mutakabbir","The Supremely Great"),
        Name("الْخَالِقُ","Al-Khaliq","The Creator"),
        Name("الْبَارِئُ","Al-Bari","The Originator"),
        Name("الْمُصَوِّرُ","Al-Musawwir","The Fashioner"),
        Name("الْغَفَّارُ","Al-Ghaffar","The All-Forgiving"),
        Name("الْقَهَّارُ","Al-Qahhar","The All-Prevailing"),
        Name("الْوَهَّابُ","Al-Wahhab","The Bestower"),
        Name("الرَّزَّاقُ","Ar-Razzaq","The Provider"),
        Name("الْفَتَّاحُ","Al-Fattah","The Opener"),
        Name("الْعَلِيمُ","Al-Alim","The All-Knowing"),
        Name("الْقَابِضُ","Al-Qabid","The Withholder"),
        Name("الْبَاسِطُ","Al-Basit","The Expander"),
        Name("الْخَافِضُ","Al-Khafid","The One Who Lowers"),
        Name("الرَّافِعُ","Ar-Rafi","The One Who Raises"),
        Name("الْمُعِزُّ","Al-Mu'izz","The Giver of Honor"),
        Name("الْمُذِلُّ","Al-Mudhill","The One Who Humbles"),
        Name("السَّمِيعُ","As-Sami","The All-Hearing"),
        Name("الْبَصِيرُ","Al-Basir","The All-Seeing"),
        Name("الْحَكَمُ","Al-Hakam","The Judge"),
        Name("الْعَدْلُ","Al-Adl","The Utterly Just"),
        Name("اللَّطِيفُ","Al-Latif","The Most Subtle and Kind"),
        Name("الْخَبِيرُ","Al-Khabir","The All-Aware"),
        Name("الْحَلِيمُ","Al-Halim","The Most Forbearing"),
        Name("الْعَظِيمُ","Al-Azim","The Magnificent"),
        Name("الْغَفُورُ","Al-Ghafur","The Great Forgiver"),
        Name("الشَّكُورُ","Ash-Shakur","The Most Appreciative"),
        Name("الْعَلِيُّ","Al-Ali","The Most High"),
        Name("الْكَبِيرُ","Al-Kabir","The Most Great"),
        Name("الْحَفِيظُ","Al-Hafiz","The Preserver"),
        Name("الْمُقِيتُ","Al-Muqit","The Sustainer"),
        Name("الْحَسِيبُ","Al-Hasib","The Reckoner"),
        Name("الْجَلِيلُ","Al-Jalil","The Majestic"),
        Name("الْكَرِيمُ","Al-Karim","The Most Generous"),
        Name("الرَّقِيبُ","Ar-Raqib","The Watchful"),
        Name("الْمُجِيبُ","Al-Mujib","The One Who Responds"),
        Name("الْوَاسِعُ","Al-Wasi","The All-Encompassing"),
        Name("الْحَكِيمُ","Al-Hakim","The All-Wise"),
        Name("الْوَدُودُ","Al-Wadud","The Most Loving"),
        Name("الْمَجِيدُ","Al-Majeed","The Most Glorious"),
        Name("الْبَاعِثُ","Al-Ba'ith","The Resurrector"),
        Name("الشَّهِيدُ","Ash-Shahid","The All-Witnessing"),
        Name("الْحَقُّ","Al-Haqq","The Absolute Truth"),
        Name("الْوَكِيلُ","Al-Wakil","The Trustee"),
        Name("الْقَوِيُّ","Al-Qawiyy","The All-Strong"),
        Name("الْمَتِينُ","Al-Matin","The Firm"),
        Name("الْوَلِيُّ","Al-Waliyy","The Protecting Friend"),
        Name("الْحَمِيدُ","Al-Hamid","The Praiseworthy"),
        Name("الْمُحْصِي","Al-Muhsi","The All-Enumerating"),
        Name("الْمُبْدِئُ","Al-Mubdi","The Initiator"),
        Name("الْمُعِيدُ","Al-Mu'id","The Restorer"),
        Name("الْمُحْيِي","Al-Muhyi","The Giver of Life"),
        Name("الْمُمِيتُ","Al-Mumit","The Bringer of Death"),
        Name("الْحَيُّ","Al-Hayy","The Ever-Living"),
        Name("الْقَيُّومُ","Al-Qayyum","The Self-Subsisting"),
        Name("الْوَاجِدُ","Al-Wajid","The Finder"),
        Name("الْمَاجِدُ","Al-Maajid","The Noble"),
        Name("الْوَاحِدُ","Al-Wahid","The One"),
        Name("الْأَحَدُ","Al-Ahad","The Unique"),
        Name("الصَّمَدُ","As-Samad","The Eternal Refuge"),
        Name("الْقَادِرُ","Al-Qadir","The All-Powerful"),
        Name("الْمُقْتَدِرُ","Al-Muqtadir","The All-Determining"),
        Name("الْمُقَدِّمُ","Al-Muqaddim","The One Who Brings Forward"),
        Name("الْمُؤَخِّرُ","Al-Mu'akhkhir","The One Who Delays"),
        Name("الْأَوَّلُ","Al-Awwal","The First"),
        Name("الْآخِرُ","Al-Akhir","The Last"),
        Name("الظَّاهِرُ","Az-Zahir","The Manifest"),
        Name("الْبَاطِنُ","Al-Batin","The Hidden"),
        Name("الْوَالِي","Al-Wali","The Governor"),
        Name("الْمُتَعَالِي","Al-Muta'ali","The Most Exalted"),
        Name("الْبَرُّ","Al-Barr","The Source of Goodness"),
        Name("التَّوَّابُ","At-Tawwab","The Accepter of Repentance"),
        Name("الْمُنْتَقِمُ","Al-Muntaqim","The Just Requiter"),
        Name("الْعَفُوُّ","Al-Afuww","The Pardoner"),
        Name("الرَّؤُوفُ","Ar-Ra'uf","The Most Kind"),
        Name("مَالِكُ الْمُلْكِ","Malik-ul-Mulk","Owner of All Sovereignty"),
        Name("ذُو الْجَلَالِ وَالْإِكْرَامِ","Dhul-Jalali wal-Ikram","Lord of Majesty and Honor"),
        Name("الْمُقْسِطُ","Al-Muqsit","The Equitable"),
        Name("الْجَامِعُ","Al-Jami","The Gatherer"),
        Name("الْغَنِيُّ","Al-Ghani","The Self-Sufficient"),
        Name("الْمُغْنِي","Al-Mughni","The Enricher"),
        Name("الْمَانِعُ","Al-Mani","The Preventer"),
        Name("الضَّارُّ","Ad-Darr","The One Who Allows Adversity"),
        Name("النَّافِعُ","An-Nafi","The Bestower of Benefit"),
        Name("النُّورُ","An-Nur","The Light"),
        Name("الْهَادِي","Al-Hadi","The Guide"),
        Name("الْبَدِيعُ","Al-Badi","The Incomparable Originator"),
        Name("الْبَاقِي","Al-Baqi","The Everlasting"),
        Name("الْوَارِثُ","Al-Warith","The Inheritor"),
        Name("الرَّشِيدُ","Ar-Rashid","The Guide to the Right Path"),
        Name("الصَّبُورُ","As-Sabur","The Most Patient")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = black
        window.navigationBarColor = black

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(black)
            setPadding(dp(12), dp(10), dp(12), dp(10))
        }

        val title = TextView(this).apply {
            text = "ASMA-UL-HUSNA"
            textSize = 24f
            setTextColor(gold)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            setPadding(0, dp(10), 0, dp(4))
        }
        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "أَسْمَاءُ اللَّهِ الْحُسْنَى\\nThe 99 Beautiful Names of Allah"
            textSize = 16f
            setTextColor(ivory)
            gravity = Gravity.CENTER
            setPadding(0, dp(4), 0, dp(12))
        }
        root.addView(subtitle)

        val scroll = ScrollView(this)
        val grid = GridLayout(this).apply {
            columnCount = 2
            rowCount = (names.size + 1) / 2
            useDefaultMargins = false
            alignmentMode = GridLayout.ALIGN_BOUNDS
        }

        names.forEachIndexed { index, name ->
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(dp(8), dp(10), dp(8), dp(10))
                background = GradientDrawable().apply {
                    setColor(Color.rgb(29, 29, 29))
                    setStroke(dp(1), gold)
                    cornerRadius = dp(8).toFloat()
                }
            }

            card.addView(TextView(this).apply {
                text = (index + 1).toString()
                textSize = 12f
                setTextColor(gold)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })
            card.addView(TextView(this).apply {
                text = name.arabic
                textSize = 23f
                setTextColor(gold)
                gravity = Gravity.CENTER
                textDirection = View.TEXT_DIRECTION_RTL
                setPadding(0, dp(5), 0, dp(5))
            })
            card.addView(TextView(this).apply {
                text = name.transliteration
                textSize = 15f
                setTextColor(ivory)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.CENTER
            })
            card.addView(TextView(this).apply {
                text = name.meaning
                textSize = 12f
                setTextColor(Color.LTGRAY)
                gravity = Gravity.CENTER
                setPadding(0, dp(4), 0, 0)
            })

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(index % 2, 1f)
                rowSpec = GridLayout.spec(index / 2)
                setMargins(dp(4), dp(4), dp(4), dp(4))
            }
            grid.addView(card, params)
        }

        scroll.addView(grid, ScrollView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ))
        root.addView(scroll, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        root.addView(TextView(this).apply {
            text = "← BACK"
            textSize = 16f
            setTextColor(gold)
            gravity = Gravity.CENTER
            setPadding(0, dp(12), 0, dp(6))
            setOnClickListener { finish() }
        })

        setContentView(root)
    }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()
}
