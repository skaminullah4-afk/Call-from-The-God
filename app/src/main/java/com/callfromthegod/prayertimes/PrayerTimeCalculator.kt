package com.callfromthegod.prayertimes

import com.batoulapps.adhan2.CalculationMethod
import com.batoulapps.adhan2.Coordinates
import com.batoulapps.adhan2.data.DateComponents
import com.batoulapps.adhan2.Madhab
import com.batoulapps.adhan2.PrayerTimes
import kotlin.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object PrayerTimeCalculator {

    const val DEFAULT_LAT = 20.5007
    const val DEFAULT_LNG = 86.4226

    private val zone = ZoneId.of("Asia/Kolkata")

    data class DailyPrayerTimes(
        val fajr: String,
        val sunrise: String,
        val dhuhr: String,
        val asrHanafi: String,
        val maghrib: String,
        val isha: String
    )

    fun getPrayerTimes(
        lat: Double = DEFAULT_LAT,
        lng: Double = DEFAULT_LNG,
        date: LocalDate = LocalDate.now(zone)
    ): DailyPrayerTimes {

        val coordinates = Coordinates(lat, lng)

        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters.copy(
            madhab = Madhab.HANAFI
        )

        val dateComponents = DateComponents(
            date.year,
            date.monthValue,
            date.dayOfMonth
        )

        val prayerTimes = PrayerTimes(
            coordinates,
            dateComponents,
            params
        )

        val formatter = DateTimeFormatter.ofPattern(
            "hh:mm a",
            Locale.ENGLISH
        )

        fun format(time: Instant): String =
            formatter.format(time.atZone(zone))

        return DailyPrayerTimes(
            fajr = format(prayerTimes.fajr),
            sunrise = format(prayerTimes.sunrise),
            dhuhr = format(prayerTimes.dhuhr),
            asrHanafi = format(prayerTimes.asr),
            maghrib = format(prayerTimes.maghrib),
            isha = format(prayerTimes.isha)
        )
    }
}
