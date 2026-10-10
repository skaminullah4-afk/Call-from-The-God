package com.callfromthegod.prayertimes

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : Activity() {

    private lateinit var citySpinner: Spinner
    private lateinit var citySubtitle: TextView
    private lateinit var dateText: TextView
    private lateinit var fajr: TextView
    private lateinit var sunrise: TextView
    private lateinit var dhuhr: TextView
    private lateinit var asr: TextView
    private lateinit var maghrib: TextView
    private lateinit var isha: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        citySpinner = findViewById(R.id.citySpinner)
        citySubtitle = findViewById(R.id.citySubtitle)
        dateText = findViewById(R.id.dateText)
        fajr = findViewById(R.id.fajr)
        sunrise = findViewById(R.id.sunrise)
        dhuhr = findViewById(R.id.dhuhr)
        asr = findViewById(R.id.asr)
        maghrib = findViewById(R.id.maghrib)
        isha = findViewById(R.id.isha)

        val cities = PrayerCities.all

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cities.map { it.name }
        )
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        citySpinner.adapter = adapter

        val kendraparaIndex = cities.indexOfFirst {
            it.name == "Kendrapara"
        }
        citySpinner.setSelection(
            if (kendraparaIndex >= 0) kendraparaIndex else 0
        )

        citySpinner.onItemSelectedListener =
            object : android.widget.AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    updatePrayerTimes(cities[position])
                }

                override fun onNothingSelected(
                    parent: android.widget.AdapterView<*>?
                ) {}
            }
    }

    private fun updatePrayerTimes(city: PrayerCity) {
        val today = LocalDate.now(
            java.time.ZoneId.of(city.timeZone)
        )

        dateText.text = today.format(
            DateTimeFormatter.ofPattern(
                "EEEE, d MMMM yyyy",
                Locale.ENGLISH
            )
        )

        citySubtitle.text = "${city.name.uppercase(Locale.ENGLISH)} PRAYER TIMES"

        val times = PrayerTimeCalculator.getPrayerTimes(
            lat = city.latitude,
            lng = city.longitude,
            date = today,
            timeZone = city.timeZone
        )

        fajr.text = "Fajr: ${times.fajr}"
        sunrise.text = "Sunrise: ${times.sunrise}"
        dhuhr.text = "Dhuhr: ${times.dhuhr}"
        asr.text = "Asr (Hanafi): ${times.asrHanafi}"
        maghrib.text = "Maghrib: ${times.maghrib}"
        isha.text = "Isha: ${times.isha}"
    }
}
