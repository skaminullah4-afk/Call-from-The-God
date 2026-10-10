package com.callfromthegod.prayertimes

import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.temporal.ChronoUnit
import java.util.Locale

object HijriCalendarHelper {

    private const val PREFS = "hijri_settings"
    private const val OFFSET_KEY = "day_offset"

    fun getOffset(context: android.content.Context): Int {
        return context.getSharedPreferences(
            PREFS,
            android.content.Context.MODE_PRIVATE
        ).getInt(OFFSET_KEY, 0).coerceIn(-5, 5)
    }

    fun setOffset(
        context: android.content.Context,
        offset: Int
    ) {
        context.getSharedPreferences(
            PREFS,
            android.content.Context.MODE_PRIVATE
        ).edit()
            .putInt(OFFSET_KEY, offset.coerceIn(-5, 5))
            .apply()
    }

    fun adjustedDate(
        date: LocalDate,
        offset: Int
    ): HijrahDate {
        return HijrahDate.from(date.plusDays(offset.toLong()))
    }

    fun formatHijriDate(
        date: LocalDate = LocalDate.now(),
        offset: Int = 0
    ): String {
        val hijri = adjustedDate(date, offset)

        val day = hijri.get(
            java.time.temporal.ChronoField.DAY_OF_MONTH
        )
        val month = hijri.get(
            java.time.temporal.ChronoField.MONTH_OF_YEAR
        )
        val year = hijri.get(
            java.time.temporal.ChronoField.YEAR
        )

        val months = listOf(
            "Muharram", "Safar", "Rabi al-Awwal",
            "Rabi al-Thani", "Jumada al-Awwal",
            "Jumada al-Thani", "Rajab", "Sha'ban",
            "Ramadan", "Shawwal", "Dhul Qadah",
            "Dhul Hijjah"
        )

        return "$day ${months[month - 1]} $year AH"
    }
}
