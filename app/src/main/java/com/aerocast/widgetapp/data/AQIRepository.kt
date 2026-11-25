
package com.aerocast.widgetapp.data

import kotlinx.serialization.Serializable
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import android.util.Log
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Serializable
data class AQIResponse(
    val forecastAqi: List<Int>
)

object AQIRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchAQI(): AQIResponse {

        // val startDate = java.time.LocalDate.parse("2026-10-20")
        // val endDate = java.time.LocalDate.parse("2026-10-25")

        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(5)

        // val forecastAqi = (0..java.time.temporal.ChronoUnit.DAYS
        val forecastAqi = (0..ChronoUnit.DAYS
            .between(startDate, endDate).toInt()).map { dayOffset ->

            val date = startDate.plusDays(dayOffset.toLong()).toString()

            val document = db.collection("historical_aqi")
                .document(date)
                .get()
                .await()

            val aqi = document.getDouble("aqi")?.toInt() ?: 0

            Log.d("AQIRepository", "$date: $aqi")

            aqi
        }

        Log.d("AQIRepository", "Forecast AQI: $forecastAqi")

        return AQIResponse(
            forecastAqi = forecastAqi
        )
    }
}
