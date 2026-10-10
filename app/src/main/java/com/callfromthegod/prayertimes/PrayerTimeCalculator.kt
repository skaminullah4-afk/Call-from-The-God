package com.callfromthegod.prayertimes

import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.CalculationParameters
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.DateComponents
import com.batoulapps.adhan.Madhab
import com.batoulapps.adhan.PrayerTimes
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object PrayerTimeCalculator {

    const val DEFAULT_LAT = 20.5007
    const val DEFAULT_LNG = 86.4226

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
        date: LocalDate = LocalDate.now()
    ): DailyPrayerTimes {

        val coordinates = Coordinates(lat, lng)
        val params = CalculationMethod.MUSLIM_WORLD_LEAGUE.parameters
        params.madhab = Madhab.HANAFI

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

        fun format(time: java.util.Date): String =
            time.toInstant()
                .atZone(ZoneId.of("Asia/Kolkata"))
                .format(formatter)

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
