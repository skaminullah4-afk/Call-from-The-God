package com.callfromthegod.prayertimes

import java.util.Locale
import kotlin.math.acos
import kotlin.math.atan
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

object PrayerTimeCalculator {
    const val DEFAULT_LAT = 20.5007
    const val DEFAULT_LNG = 86.4226

    private const val FAJR_ANGLE = 18.0
    private const val ISHA_ANGLE = 18.0
    private const val ASR_FACTOR = 2.0

    data class PrayerTimes(
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
        dayOfYear: Int = java.time.LocalDate.now().dayOfYear
    ): PrayerTimes {
        val declination = 23.45 * sin(
            Math.toRadians(360.0 / 365.0 * (284 + dayOfYear))
        )
        val declRad = Math.toRadians(declination)
        val b = Math.toRadians(360.0 / 365.0 * (dayOfYear - 81))
        val eqTime = 9.87 * sin(2 * b) - 7.53 * cos(b) - 1.5 * sin(b)
        val solarNoon = 12.0 + (82.5 - lng) / 15.0 - eqTime / 60.0

        fun timeForAngle(angle: Double, isMorning: Boolean): Double {
            val latRad = Math.toRadians(lat)
            val denominator = cos(latRad) * cos(declRad)
            if (abs(denominator) < 1e-12) return solarNoon

            val cosOmega = (
                (sin(Math.toRadians(angle)) -
                    sin(latRad) * sin(declRad)) / denominator
            ).coerceIn(-1.0, 1.0)

            val omega = Math.toDegrees(acos(cosOmega)) / 15.0
            return if (isMorning) solarNoon - omega else solarNoon + omega
        }

        fun asrTime(): Double {
            val latRad = Math.toRadians(lat)
            val tanVal = abs(tan(latRad - declRad))
            val angle = Math.toDegrees(atan(1.0 / (ASR_FACTOR + tanVal)))
            return timeForAngle(angle, false)
        }

        fun format(time: Double): String {
            val t = ((time % 24.0) + 24.0) % 24.0
            val totalMinutes = (t * 60.0).toInt()
            val h = (totalMinutes / 60) % 24
            val m = totalMinutes % 60
            val amPm = if (h >= 12) "PM" else "AM"
            val hour12 = if (h % 12 == 0) 12 else h % 12
            return String.format(
                Locale.ENGLISH, "%02d:%02d %s", hour12, m, amPm
            )
        }

        return PrayerTimes(
            fajr = format(timeForAngle(-FAJR_ANGLE, true)),
            sunrise = format(timeForAngle(-0.833, true)),
            dhuhr = format(solarNoon),
            asrHanafi = format(asrTime()),
            maghrib = format(timeForAngle(-0.833, false)),
            isha = format(timeForAngle(-ISHA_ANGLE, false))
        )
    }
}
