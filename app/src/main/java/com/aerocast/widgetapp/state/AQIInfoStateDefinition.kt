package com.aerocast.widgetapp.state

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.glance.state.GlanceStateDefinition
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import com.aerocast.widgetapp.state.AQIInfo

/**
 * Provides our own definition of "Glance state" using Kotlin serialization.
 */
object AQIInfoStateDefinition : GlanceStateDefinition<AQIInfo> {

    private const val DATA_STORE_FILENAME = "aqi_info"

    /**
     * Use the same file name regardless of the widget instance to share data between them
     *
     * If you need different state/data for each instance, create a store using the provided fileKey
     */
    private val Context.datastore by dataStore(DATA_STORE_FILENAME, AQIStateSerializer)

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<AQIInfo> {
        return context.datastore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }

    /**
     * Custom serializer for AQIInfo using Json.
     */
    object AQIStateSerializer : Serializer<AQIInfo> {

        override val defaultValue = AQIInfo.Unavailable("No AQI data available")
        
        private val json = Json { ignoreUnknownKeys = true }

        override suspend fun readFrom(input: InputStream): AQIInfo = try {
            json.decodeFromString(
                AQIInfo.serializer(),
                input.readBytes().decodeToString()
            )
        } catch (exception: Exception) {
            throw CorruptionException("Could not read aqi data: ${exception.message}")
        }

        override suspend fun writeTo(t: AQIInfo, output: OutputStream) {
            output.use {
                it.write(
                    json.encodeToString(AQIInfo.serializer(), t).encodeToByteArray()
                )
            }
        }
    }
}