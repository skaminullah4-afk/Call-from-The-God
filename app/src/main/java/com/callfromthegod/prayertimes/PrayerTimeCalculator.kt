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
import kotlin.math.*

object PrayerTimeCalculator {

    const val DEFAULT_LAT = 20.5007
    const val DEFAULT_LNG = 86.4226

    private val defaultZone = ZoneId.of("Asia/Kolkata")

    enum class AsrMethod {
        HANAFI,
        SHAFII
    }

    data class DailyPrayerTimes(
        val fajr: String,
        val sunrise: String,
        val zawal: String,
        val dhuhr: String,
        val asrHanafi: String,
        val maghrib: String,
        val isha: String
    )

    fun getPrayerTimes(
        lat: Double = DEFAULT_LAT,
        lng: Double = DEFAULT_LNG,
        date: LocalDate = LocalDate.now(defaultZone),
        timeZone: String = "Asia/Kolkata",
        asrMethod: AsrMethod = AsrMethod.HANAFI
    ): DailyPrayerTimes {

        val zone = ZoneId.of(timeZone)
        val coordinates = Coordinates(lat, lng)

        val dayOfYear = date.dayOfYear
        val b = Math.toRadians(
            360.0 * (dayOfYear - 81) / 364.0
        )

        val equationOfTime = 9.87 * sin(2 * b) -
            7.53 * cos(b) -
            1.5 * sin(b)

        val solarNoonMinutes = 720.0 -
            (4.0 * lng) -
            equationOfTime +
            (zone.rules.getOffset(
                date.atTime(12, 0).atZone(zone).toInstant()
            ).totalSeconds / 60.0)

        val zawalTime = date.atStartOfDay(zone)
            .plusSeconds((solarNoonMinutes * 60).toLong())
            .format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH))

        val madhab = when (asrMethod) {
            AsrMethod.HANAFI -> Madhab.HANAFI
            AsrMethod.SHAFII -> Madhab.SHAFI
        }

        val params = CalculationMethod.KARACHI.parameters.copy(
            madhab = madhab
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
            formatter.format(
                java.time.Instant.parse(time.toString()).atZone(zone)
            )

        return DailyPrayerTimes(
            fajr = format(prayerTimes.fajr),
            sunrise = format(prayerTimes.sunrise),
            zawal = zawalTime,
            dhuhr = format(prayerTimes.dhuhr),
            asrHanafi = format(prayerTimes.asr),
            maghrib = format(prayerTimes.maghrib),
            isha = format(prayerTimes.isha)
        )
    }
}
