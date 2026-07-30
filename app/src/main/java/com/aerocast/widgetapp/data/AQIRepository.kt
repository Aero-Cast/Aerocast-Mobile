package com.aerocast.widgetapp.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

@Serializable
data class AQIResponse(
    val currentAqi: Int,
    val forecastAqi: List<Int>
)

object AQIRepository {

    private val client = OkHttpClient()

    private val json = Json {
        ignoreUnknownKeys = true
    }


    fun fetchAQI(): AQIResponse {

        val request = Request.Builder()
            .url("https://your-project.web.app/aqi.json")
            .build()


        client.newCall(request).execute().use { response ->

            if (!response.isSuccessful) {
                throw Exception("HTTP ${response.code}")
            }


            val body = response.body.string()

            return json.decodeFromString(
                AQIResponse.serializer(),
                body
            )
        }
    }
}