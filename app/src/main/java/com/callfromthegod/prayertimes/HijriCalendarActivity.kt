package com.callfromthegod.prayertimes

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.TextStyle
import java.util.Locale

class HijriCalendarActivity : Activity() {

    private var displayedMonth = HijrahDate.now()
    private lateinit var monthTitle: TextView
    private lateinit var adjustmentTitle: TextView
    private lateinit var calendarGrid: GridLayout

    private val gold = Color.rgb(212, 175, 98)
    private val ivory = Color.rgb(247, 240, 223)
    private val dark = Color.rgb(23, 21, 17)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 24, 20, 24)
            setBackgroundColor(dark)
        }

        val title = TextView(this).apply {
            text = "HIJRI CALENDAR"
            textSize = 25f
            setTextColor(gold)
            gravity = Gravity.CENTER
        }
        root.addView(title)

        monthTitle = TextView(this).apply {
            textSize = 20f
            setTextColor(ivory)
            gravity = Gravity.CENTER
            setPadding(0, 24, 0, 20)
        }
        root.addView(monthTitle)

        val adjustmentLabel = TextView(this).apply {
            text = "Hijri date adjustment (days)"
            textSize = 14f
            setTextColor(gold)
            gravity = Gravity.CENTER
        }
        root.addView(adjustmentLabel)

        val adjustmentSpinner = Spinner(this)
        val adjustmentOptions = (-15..15).map { value ->
            if (value > 0) "+$value" else value.toString()
        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            adjustmentOptions
        )
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        adjustmentSpinner.adapter = adapter
        
        adjustmentSpinner.setSelection(
            HijriCalendarHelper.getOffset(this) + 15
        )

        root.addView(adjustmentSpinner)
        
        adjustmentSpinner.onItemSelectedListener =
        object : android.widget.AdapterView.OnItemSelectedListener {
            
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                val selectedOffset = position - 15
                
                HijriCalendarHelper.setOffset(
                    this@HijriCalendarActivity,
                    selectedOffset
                )
                
                displayedMonth = HijrahDate.from(
                    LocalDate.now().plusDays(selectedOffset.toLong())
                )

                renderCalendar()
            }

            override fun onNothingSelected(
                parent: android.widget.AdapterView<*>?
                    ) {
            }
        }

        val navigation = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val previous = Button(this).apply {
            text = "Previous"
            setOnClickListener {
                displayedMonth = displayedMonth.minus(
                    1,
                    java.time.temporal.ChronoUnit.MONTHS
                )
                renderCalendar()
            }
        }

        val next = Button(this).apply {
            text = "Next"
            setOnClickListener {
                displayedMonth = displayedMonth.plus(
                    1,
                    java.time.temporal.ChronoUnit.MONTHS
                )
                renderCalendar()
            }
        }

        navigation.addView(previous)
        navigation.addView(next)
        root.addView(navigation)

        calendarGrid = GridLayout(this).apply {
            columnCount = 7
            setPadding(0, 20, 0, 0)
        }
        root.addView(
            calendarGrid,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )

        val note = TextView(this).apply {
            text = "Hijri dates may differ from local moon sighting."
            textSize = 12f
            setTextColor(gold)
            setPadding(0, 24, 0, 0)
        }
        root.addView(note)

        setContentView(root)
        renderCalendar()
    }

    private fun renderCalendar() {
        calendarGrid.removeAllViews()

        val year = displayedMonth.get(
            java.time.temporal.ChronoField.YEAR
        )
        val month = displayedMonth.get(
            java.time.temporal.ChronoField.MONTH_OF_YEAR
        )

        val monthNames = listOf(
            "Muharram", "Safar", "Rabi al-Awwal",
            "Rabi al-Thani", "Jumada al-Awwal",
            "Jumada al-Thani", "Rajab", "Sha'ban",
            "Ramadan", "Shawwal", "Dhul Qadah",
            "Dhul Hijjah"
        )

        monthTitle.text = "${monthNames[month - 1]} $year AH"

        val weekdays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        weekdays.forEach { day ->
            addCell(day, true)
        }

        val firstDay = HijrahDate.of(year, month, 1)
        val adjustedFirstGregorian = LocalDate.from(firstDay)
            .minusDays(HijriCalendarHelper.getOffset(this).toLong())

        val offset = adjustedFirstGregorian.dayOfWeek.value - 1

        repeat(offset) {
            addCell("", false)
        }

        val nextMonth = firstDay.plus(
            1,
            java.time.temporal.ChronoUnit.MONTHS
        )
        val daysInMonth = java.time.temporal.ChronoUnit.DAYS
            .between(firstDay, nextMonth).toInt()

        for (day in 1..daysInMonth) {
            val arabicDay = day.toString()
                .replace('0', '٠')
                .replace('1', '١')
                .replace('2', '٢')
                .replace('3', '٣')
                .replace('4', '٤')
                .replace('5', '٥')
                .replace('6', '٦')
                .replace('7', '٧')
                .replace('8', '٨')
                .replace('9', '٩')

            addCell(arabicDay, false)
        }

    }

    private fun addCell(value: String, isHeader: Boolean) {
        val cell = TextView(this).apply {
            text = value
            gravity = Gravity.CENTER
            textSize = if (isHeader) 12f else 15f
            setTextColor(if (isHeader) gold else ivory)
            setPadding(2, 12, 2, 12)
        }

        calendarGrid.addView(
            cell,
            GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(
                    GridLayout.UNDEFINED, 1f
                )
                rowSpec = GridLayout.spec(
                    GridLayout.UNDEFINED
                )
            }
        )
    }
}

