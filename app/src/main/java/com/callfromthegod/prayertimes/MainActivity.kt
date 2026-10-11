package com.callfromthegod.prayertimes

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.graphics.Color
import android.view.ViewGroup
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : Activity() {

    private lateinit var citySpinner: Spinner
    private lateinit var asrMethodSpinner: Spinner
    private lateinit var citySubtitle: TextView
    private lateinit var dateText: TextView
    private lateinit var hijriDateText: TextView
    private lateinit var fajr: TextView
    private lateinit var sunrise: TextView
    private lateinit var zawal: TextView
    private lateinit var dhuhr: TextView
    private lateinit var asr: TextView
    private lateinit var maghrib: TextView
    private lateinit var isha: TextView

    private val asrMethods = listOf("Hanafi", "Shafi'i")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.openHijriCalendar).setOnClickListener {
            startActivity(Intent(this, HijriCalendarActivity::class.java))
        }

        citySpinner = findViewById(R.id.citySpinner)
        asrMethodSpinner = findViewById(R.id.asrMethodSpinner)
        citySubtitle = findViewById(R.id.citySubtitle)
        dateText = findViewById(R.id.dateText)
        hijriDateText = findViewById(R.id.hijriDateText)
        fajr = findViewById(R.id.fajr)
        sunrise = findViewById(R.id.sunrise)
        zawal = findViewById(R.id.zawal)
        dhuhr = findViewById(R.id.dhuhr)
        asr = findViewById(R.id.asr)
        maghrib = findViewById(R.id.maghrib)
        isha = findViewById(R.id.isha)

        val cities = PrayerCities.all

        fun makeAdapter(items: List<String>): ArrayAdapter<String> {
            return object : ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                items
            ) {
                override fun getView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    return super.getView(position, convertView, parent).also {
                        (it as TextView).setTextColor(Color.parseColor("#174A35"))
                    }
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    return super.getDropDownView(
                        position, convertView, parent
                    ).also {
                        (it as TextView).setTextColor(Color.parseColor("#174A35"))
                        it.setBackgroundColor(Color.parseColor("#F7F0DF"))
                    }
                }
            }.apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        citySpinner.adapter = makeAdapter(cities.map { it.name })
        asrMethodSpinner.adapter = makeAdapter(asrMethods)

        asrMethodSpinner.setSelection(0)

        findViewById<Button>(R.id.openQibla).setOnClickListener {
            val city = cities[citySpinner.selectedItemPosition]
            val intent = Intent(this, QiblaActivity::class.java)
            intent.putExtra("latitude", city.latitude)
            intent.putExtra("longitude", city.longitude)
            startActivity(intent)
        }

        val kendraparaIndex = cities.indexOfFirst {
            it.name == "Kendrapara"
        }
        citySpinner.setSelection(if (kendraparaIndex >= 0) kendraparaIndex else 0)

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val city = cities[citySpinner.selectedItemPosition]
                updatePrayerTimes(city)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        citySpinner.onItemSelectedListener = listener
        asrMethodSpinner.onItemSelectedListener = listener
    }

    private fun updatePrayerTimes(city: PrayerCity) {
        val zone = ZoneId.of(city.timeZone)
        val today = LocalDate.now(zone)

        dateText.text = today.format(
            DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy", Locale.ENGLISH)
        )

        hijriDateText.text = HijriCalendarHelper.formatHijriDate(
            today,
            HijriCalendarHelper.getOffset(this)
        )

        citySubtitle.text =
            "${city.name.uppercase(Locale.ENGLISH)} PRAYER TIMES"

        val selectedAsrMethod =
            if (asrMethodSpinner.selectedItemPosition == 1) {
                PrayerTimeCalculator.AsrMethod.SHAFII
            } else {
                PrayerTimeCalculator.AsrMethod.HANAFI
            }

        val times = PrayerTimeCalculator.getPrayerTimes(
            lat = city.latitude,
            lng = city.longitude,
            date = today,
            timeZone = city.timeZone,
            asrMethod = selectedAsrMethod
        )

        fajr.text = "Fajr: ${times.fajr}"
        sunrise.text = "Sunrise: ${times.sunrise}"
        zawal.text = "Zawāl (approx. solar noon): ${times.zawal}"
        dhuhr.text = "Dhuhr: ${times.dhuhr}"
        asr.text = "Asr (${asrMethods[asrMethodSpinner.selectedItemPosition]}): ${times.asr}"
        maghrib.text = "Maghrib: ${times.maghrib}"
        isha.text = "Isha: ${times.isha}"
    }

    override fun onResume() {
        super.onResume()

        if (::hijriDateText.isInitialized) {
            val city = PrayerCities.all.getOrNull(
                citySpinner.selectedItemPosition
            )
            val zone = ZoneId.of(city?.timeZone ?: "Asia/Kolkata")
            val today = LocalDate.now(zone)

            hijriDateText.text = HijriCalendarHelper.formatHijriDate(
                today,
                HijriCalendarHelper.getOffset(this)
            )
        }
    }
}
