package com.callfromthegod.prayertimes

data class PrayerCity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String = "Asia/Kolkata"
)

object PrayerCities {
    val all = listOf(
        PrayerCity("Kendrapara", 20.5007, 86.4226),
        PrayerCity("Bhubaneswar", 20.2961, 85.8245),
        PrayerCity("Cuttack", 20.4625, 85.8828),
        PrayerCity("Kolkata", 22.5726, 88.3639),
        PrayerCity("New Delhi", 28.6139, 77.2090),
        PrayerCity("Mumbai", 19.0760, 72.8777),
        PrayerCity("Hyderabad", 17.3850, 78.4867),
        PrayerCity("Bengaluru", 12.9716, 77.5946),
        PrayerCity("Chennai", 13.0827, 80.2707),
        PrayerCity("Makkah", 21.3891, 39.8579),
        PrayerCity("Madinah", 24.4672, 39.6112)
    )
}
