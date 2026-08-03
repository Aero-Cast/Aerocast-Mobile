package com.aerocast.widgetapp.data

import kotlinx.serialization.Serializable
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Serializable
data class AQIResponse(
    val currentAqi: Int,
    val forecastAqi: List<Int>
)

object AQIRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun fetchAQI(): AQIResponse {

        val historical_aqi = db.collection("historical_aqi")
            .document("2026-08-04")
            .get()
            .await()

        val aqi_forecasts = db.collection("aqi_forecasts")
            .document("latest")
            .get()
            .await()

        val forecasts = aqi_forecasts.get("forecasts") as List<Map<String, Any>>

        return AQIResponse(
            currentAqi = historical_aqi.getDouble("aqi")?.toInt() ?: 0,
            forecastAqi = forecasts.map { (it["predicted_aqi"] as Double).toInt() }
        )

        // return AQIResponse(
        //     currentAqi = 50,
        //     forecastAqi = listOf(40, 50, 60, 70, 80)
        // )
        
    }
}