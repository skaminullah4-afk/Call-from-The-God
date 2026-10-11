package com.callfromthegod.prayertimes

data class PrayerCity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val timeZone: String = "Asia/Kolkata"
)

object PrayerCities {
    val all = listOf(
        // India
        PrayerCity("Kendrapara", 20.5007, 86.4226),
        PrayerCity("Bhubaneswar", 20.2961, 85.8245),
        PrayerCity("Cuttack", 20.4625, 85.8828),
        PrayerCity("Kolkata", 22.5726, 88.3639),
        PrayerCity("New Delhi", 28.6139, 77.2090),
        PrayerCity("Mumbai", 19.0760, 72.8777),
        PrayerCity("Hyderabad", 17.3850, 78.4867),
        PrayerCity("Bengaluru", 12.9716, 77.5946),
        PrayerCity("Chennai", 13.0827, 80.2707),
        PrayerCity("Srinagar", 34.0837, 74.7973),
        PrayerCity("Guwahati", 26.1445, 91.7362),

        // South Asia
        PrayerCity("Karachi", 24.8607, 67.0011, "Asia/Karachi"),
        PrayerCity("Lahore", 31.5204, 74.3587, "Asia/Karachi"),
        PrayerCity("Islamabad", 33.6844, 73.0479, "Asia/Karachi"),
        PrayerCity("Dhaka", 23.8103, 90.4125, "Asia/Dhaka"),
        PrayerCity("Chittagong", 22.3569, 91.7832, "Asia/Dhaka"),
        PrayerCity("Kathmandu", 27.7172, 85.3240, "Asia/Kathmandu"),
        PrayerCity("Colombo", 6.9271, 79.8612, "Asia/Colombo"),
        PrayerCity("Male", 4.1755, 73.5093, "Indian/Maldives"),

        // Gulf and Middle East
        PrayerCity("Makkah", 21.3891, 39.8579, "Asia/Riyadh"),
        PrayerCity("Madinah", 24.4672, 39.6112, "Asia/Riyadh"),
        PrayerCity("Riyadh", 24.7136, 46.6753, "Asia/Riyadh"),
        PrayerCity("Jeddah", 21.4858, 39.1925, "Asia/Riyadh"),
        PrayerCity("Dubai", 25.2048, 55.2708, "Asia/Dubai"),
        PrayerCity("Abu Dhabi", 24.4539, 54.3773, "Asia/Dubai"),
        PrayerCity("Doha", 25.2854, 51.5310, "Asia/Qatar"),
        PrayerCity("Kuwait City", 29.3759, 47.9774, "Asia/Kuwait"),
        PrayerCity("Manama", 26.2285, 50.5860, "Asia/Bahrain"),
        PrayerCity("Muscat", 23.5880, 58.3829, "Asia/Muscat"),
        PrayerCity("Sana'a", 15.3694, 44.1910, "Asia/Aden"),
        PrayerCity("Amman", 31.9539, 35.9106, "Asia/Amman"),
        PrayerCity("Jerusalem", 31.7683, 35.2137, "Asia/Jerusalem"),
        PrayerCity("Beirut", 33.8938, 35.5018, "Asia/Beirut"),
        PrayerCity("Baghdad", 33.3152, 44.3661, "Asia/Baghdad"),
        PrayerCity("Tehran", 35.6892, 51.3890, "Asia/Tehran"),
        PrayerCity("Istanbul", 41.0082, 28.9784, "Europe/Istanbul"),

        // Southeast and East Asia
        PrayerCity("Kuala Lumpur", 3.1390, 101.6869, "Asia/Kuala_Lumpur"),
        PrayerCity("Jakarta", -6.2088, 106.8456, "Asia/Jakarta"),
        PrayerCity("Singapore", 1.3521, 103.8198, "Asia/Singapore"),
        PrayerCity("Bangkok", 13.7563, 100.5018, "Asia/Bangkok"),
        PrayerCity("Manila", 14.5995, 120.9842, "Asia/Manila"),
        PrayerCity("Hong Kong", 22.3193, 114.1694, "Asia/Hong_Kong"),
        PrayerCity("Tokyo", 35.6762, 139.6503, "Asia/Tokyo"),
        PrayerCity("Seoul", 37.5665, 126.9780, "Asia/Seoul"),
        PrayerCity("Beijing", 39.9042, 116.4074, "Asia/Shanghai"),

        // Africa
        PrayerCity("Cairo", 30.0444, 31.2357, "Africa/Cairo"),
        PrayerCity("Alexandria", 31.2001, 29.9187, "Africa/Cairo"),
        PrayerCity("Casablanca", 33.5731, -7.5898, "Africa/Casablanca"),
        PrayerCity("Rabat", 34.0209, -6.8416, "Africa/Casablanca"),
        PrayerCity("Lagos", 6.5244, 3.3792, "Africa/Lagos"),
        PrayerCity("Abuja", 9.0765, 7.3986, "Africa/Lagos"),
        PrayerCity("Nairobi", -1.2921, 36.8219, "Africa/Nairobi"),
        PrayerCity("Addis Ababa", 8.9806, 38.7578, "Africa/Addis_Ababa"),
        PrayerCity("Johannesburg", -26.2041, 28.0473, "Africa/Johannesburg"),
        PrayerCity("Khartoum", 15.5007, 32.5599, "Africa/Khartoum"),

        // Europe
        PrayerCity("London", 51.5072, -0.1276, "Europe/London"),
        PrayerCity("Paris", 48.8566, 2.3522, "Europe/Paris"),
        PrayerCity("Berlin", 52.5200, 13.4050, "Europe/Berlin"),
        PrayerCity("Rome", 41.9028, 12.4964, "Europe/Rome"),
        PrayerCity("Madrid", 40.4168, -3.7038, "Europe/Madrid"),
        PrayerCity("Amsterdam", 52.3676, 4.9041, "Europe/Amsterdam"),
        PrayerCity("Moscow", 55.7558, 37.6173, "Europe/Moscow"),
        PrayerCity("Athens", 37.9838, 23.7275, "Europe/Athens"),

        // North America
        PrayerCity("New York", 40.7128, -74.0060, "America/New_York"),
        PrayerCity("Washington DC", 38.9072, -77.0369, "America/New_York"),
        PrayerCity("Chicago", 41.8781, -87.6298, "America/Chicago"),
        PrayerCity("Denver", 39.7392, -104.9903, "America/Denver"),
        PrayerCity("Los Angeles", 34.0522, -118.2437, "America/Los_Angeles"),
        PrayerCity("Toronto", 43.6532, -79.3832, "America/Toronto"),
        PrayerCity("Vancouver", 49.2827, -123.1207, "America/Vancouver"),
        PrayerCity("Mexico City", 19.4326, -99.1332, "America/Mexico_City"),

        // South America
        PrayerCity("Sao Paulo", -23.5505, -46.6333, "America/Sao_Paulo"),
        PrayerCity("Rio de Janeiro", -22.9068, -43.1729, "America/Sao_Paulo"),
        PrayerCity("Buenos Aires", -34.6037, -58.3816, "America/Argentina/Buenos_Aires"),

        // Oceania
        PrayerCity("Sydney", -33.8688, 151.2093, "Australia/Sydney"),
        PrayerCity("Melbourne", -37.8136, 144.9631, "Australia/Melbourne"),
        PrayerCity("Perth", -31.9505, 115.8605, "Australia/Perth"),
        PrayerCity("Auckland", -36.8509, 174.7645, "Pacific/Auckland")
    )
}
