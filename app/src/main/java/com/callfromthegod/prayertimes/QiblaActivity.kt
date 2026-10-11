package com.callfromthegod.prayertimes

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.GeomagneticField
import kotlin.math.*

class QiblaActivity : Activity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var rotationSensor: Sensor? = null

    private var qiblaBearing = 0.0
    private var magneticDeclination = 0f

    private lateinit var compassView: CompassView
    private lateinit var directionText: TextView
    private lateinit var statusText: TextView

    private val gold = Color.rgb(230, 190, 100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(15, 15, 15)
        window.navigationBarColor = Color.rgb(15, 15, 15)

        val lat = intent.getDoubleExtra("latitude", 20.4625)
        val lng = intent.getDoubleExtra("longitude", 85.8828)

        qiblaBearing = calculateQibla(lat, lng)

        val field = GeomagneticField(
            lat.toFloat(),
            lng.toFloat(),
            0f,
            System.currentTimeMillis()
        )
        magneticDeclination = field.declination

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(16, 8, 16, 8)
            setBackgroundColor(Color.rgb(15, 15, 15))
        }

        // Keep content clear of Android's bottom navigation area.
        layout.setOnApplyWindowInsetsListener { view, insets ->
            view.setPadding(
                16,
                8,
                16,
                8 + insets.systemWindowInsetBottom
            )
            insets
        }

        fun addText(
            text: String,
            size: Float,
            color: Int
        ): TextView {
            return TextView(this).apply {
                this.text = text
                textSize = size
                setTextColor(color)
                gravity = Gravity.CENTER
                setPadding(6, 5, 6, 5)
                layout.addView(
                    this,
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }
        }

        addText("قِبْلَة", 34f, gold)
        addText("Qibla Direction", 26f, gold)

        directionText = addText(
            "Qibla: ${qiblaBearing.roundToInt()}° from North",
            20f,
            Color.WHITE
        )

        compassView = CompassView()
        layout.addView(
            compassView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        statusText = addText(
            "Keep your phone flat and away from magnets.",
            14f,
            Color.LTGRAY
        )

        setContentView(layout)

        sensorManager = getSystemService(SENSOR_SERVICE)
            as SensorManager

        rotationSensor = sensorManager.getDefaultSensor(
            Sensor.TYPE_ROTATION_VECTOR
        )

        if (rotationSensor == null) {
            statusText.text =
                "Compass sensor unavailable on this device."
        }
    }

    private fun calculateQibla(
        latitude: Double,
        longitude: Double
    ): Double {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLng = Math.toRadians(39.8262)
        val userLat = Math.toRadians(latitude)
        val userLng = Math.toRadians(longitude)

        val y = sin(kaabaLng - userLng)
        val x = cos(userLat) * tan(kaabaLat) -
            sin(userLat) * cos(kaabaLng - userLng)

        return (Math.toDegrees(atan2(y, x)) + 360) % 360
    }

    override fun onResume() {
        super.onResume()
        rotationSensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (::sensorManager.isInitialized) {
            sensorManager.unregisterListener(this)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ROTATION_VECTOR) {
            return
        }

        val rotationMatrix = FloatArray(9)
        val orientation = FloatArray(3)

        SensorManager.getRotationMatrixFromVector(
            rotationMatrix,
            event.values
        )
        SensorManager.getOrientation(
            rotationMatrix,
            orientation
        )

        val magneticAzimuth = Math.toDegrees(
            orientation[0].toDouble()
        )

        val trueAzimuth =
            (magneticAzimuth + magneticDeclination + 360) % 360

        compassView.deviceHeading = trueAzimuth.toFloat()
        compassView.qiblaBearing = qiblaBearing.toFloat()
        compassView.invalidate()

        val relative =
            (qiblaBearing - trueAzimuth + 360) % 360

        directionText.text =
            "Qibla: ${qiblaBearing.roundToInt()}° from North"

        statusText.text =
            "Turn ${relative.roundToInt()}° clockwise from your current direction."
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {}

    inner class CompassView : View(this@QiblaActivity) {

        var deviceHeading = 0f
        var qiblaBearing = 0f

        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val cx = width / 2f
            val cy = height / 2f
            val radius = min(cx, cy) * 0.80f

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            paint.color = gold
            canvas.drawCircle(cx, cy, radius, paint)

            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 25f
            paint.color = gold

            canvas.drawText(
                "N",
                cx,
                cy - radius + 29f,
                paint
            )

            canvas.save()
            canvas.rotate(-deviceHeading, cx, cy)

            // Larger, clearer white direction pointer.
            val pointerY = cy - radius + 70f
            val pointer = android.graphics.Path().apply {
                moveTo(cx, pointerY - 27f)
                lineTo(cx - 17f, pointerY + 15f)
                lineTo(cx, pointerY + 7f)
                lineTo(cx + 17f, pointerY + 15f)
                close()
            }

            paint.color = Color.WHITE
            canvas.drawPath(pointer, paint)

            // Larger Kaaba emoji.
            canvas.rotate(qiblaBearing, cx, cy)
            paint.textSize = 46f
            paint.textAlign = Paint.Align.CENTER

            canvas.drawText(
                "🕋",
                cx,
                cy - radius + 137f,
                paint
            )

            canvas.restore()

            paint.color = gold
            paint.textSize = 13f
            paint.textAlign = Paint.Align.CENTER

            canvas.drawText(
                "Keep phone flat",
                cx,
                cy + radius - 10f,
                paint
            )
        }
    }
}
