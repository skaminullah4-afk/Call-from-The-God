package com.callfromthegod.prayertimes

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val today = LocalDate.now()

        findViewById<TextView>(R.id.dateText).text =
            today.format(
                DateTimeFormatter.ofPattern(
                    "EEEE, d MMMM yyyy",
                    Locale.ENGLISH
                )
            )

        val times = PrayerTimeCalculator.getPrayerTimes(date = today)

        findViewById<TextView>(R.id.fajr).text =
            "Fajr: ${times.fajr}"

        findViewById<TextView>(R.id.sunrise).text =
            "Sunrise: ${times.sunrise}"

        findViewById<TextView>(R.id.dhuhr).text =
            "Dhuhr: ${times.dhuhr}"

        findViewById<TextView>(R.id.asr).text =
            "Asr (Hanafi): ${times.asrHanafi}"

        findViewById<TextView>(R.id.maghrib).text =
            "Maghrib: ${times.maghrib}"

        findViewById<TextView>(R.id.isha).text =
            "Isha: ${times.isha}"
    }
}
