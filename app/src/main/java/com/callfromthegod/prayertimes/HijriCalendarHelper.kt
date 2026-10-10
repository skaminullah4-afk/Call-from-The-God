package com.callfromthegod.prayertimes

import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object HijriCalendarHelper {

    fun formatHijriDate(date: LocalDate = LocalDate.now()): String {
        val hijriDate = HijrahDate.from(date)

        val day = hijriDate.get(
            java.time.temporal.ChronoField.DAY_OF_MONTH
        )
        val month = hijriDate.get(
            java.time.temporal.ChronoField.MONTH_OF_YEAR
        )
        val year = hijriDate.get(
            java.time.temporal.ChronoField.YEAR
        )

        val monthNames = listOf(
            "Muharram",
            "Safar",
            "Rabi al-Awwal",
            "Rabi al-Thani",
            "Jumada al-Awwal",
            "Jumada al-Thani",
            "Rajab",
            "Sha'ban",
            "Ramadan",
            "Shawwal",
            "Dhul Qadah",
            "Dhul Hijjah"
        )

        val monthName = monthNames.getOrElse(month - 1) {
            "Unknown month"
        }

        return "$day $monthName $year AH"
    }
}
