package com.aerocast.widgetapp.worker

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.appwidget.GlanceAppWidget
import androidx.work.*
import com.aerocast.widgetapp.widgets.AQICurrentWidget
import com.aerocast.widgetapp.widgets.AQIForecastWidget
import com.aerocast.widgetapp.state.AQIInfo
import com.aerocast.widgetapp.state.AQIInfoStateDefinition
import com.aerocast.widgetapp.data.AQIRepository
import java.util.concurrent.TimeUnit
import android.util.Log

class AQIUpdateWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    companion object {

        private val uniqueWorkName = AQIUpdateWorker::class.java.simpleName

        fun enqueue(appContext: Context) {
            val manager = WorkManager.getInstance(appContext)
            // val requestBuilder = PeriodicWorkRequestBuilder<AQIUpdateWorker>(
            //     30,
            //     TimeUnit.MINUTES
            // )

            // For testing purposes, we can use a shorter interval
            val requestBuilder = OneTimeWorkRequestBuilder<AQIUpdateWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()
                
            // var workPolicy = ExistingPeriodicWorkPolicy.KEEP

            manager.enqueueUniqueWork(
                uniqueWorkName,
                // workPolicy,
                ExistingWorkPolicy.KEEP,
                requestBuilder
            )
        }

        fun cancel(appContext: Context) {
            WorkManager.getInstance(appContext).cancelUniqueWork(uniqueWorkName)
        }

    }

    override suspend fun doWork(): Result {
        Log.d("AQIWorker", "Worker started")
        val manager = GlanceAppWidgetManager(applicationContext)
        val currentIds = manager.getGlanceIds(AQICurrentWidget::class.java)
        val forecastIds = manager.getGlanceIds(AQIForecastWidget::class.java)

        return try {
            val data = AQIRepository.fetchAQI()

            // Update state to indicate loading
            Log.d("AQIWorker", "Fetching AQI")
            setWidgetState(
                currentIds,
                AQIInfo.Loading,
                AQICurrentWidget()
            )

            setWidgetState(
                forecastIds,
                AQIInfo.Loading,
                AQIForecastWidget()
            )

            // Update state with new data
            Log.d(
                "AQIWorker",
                "AQI fetched: ${data.currentAqi}"
            )
            setWidgetState(
                currentIds,
                AQIInfo.Available(
                    currentAqi = data.currentAqi,
                    forecastAqi = data.forecastAqi
                ),
                AQICurrentWidget()
            )

            setWidgetState(
                forecastIds,
                AQIInfo.Available(
                    currentAqi = data.currentAqi,
                    forecastAqi = data.forecastAqi
                ),
                AQIForecastWidget()
)

            Log.d("AQIWorker", "Widget updated")
            val nextRequest = OneTimeWorkRequestBuilder<AQIUpdateWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniqueWork(
                    uniqueWorkName,
                    ExistingWorkPolicy.REPLACE,
                    nextRequest
                )

            
            Log.d("AQIWorker", "Widget updated2")
            Result.success()
        } catch (e: Exception) {
            val unavailable = AQIInfo.Unavailable(e.message.orEmpty())

            setWidgetState(
                currentIds,
                AQIInfo.Unavailable(e.message.orEmpty()),
                AQICurrentWidget()
            )

            setWidgetState(
                forecastIds,
                AQIInfo.Unavailable(e.message.orEmpty()),
                AQIForecastWidget()
            )
            Log.e(
                    "AQIWorker",
                    "Failed: ${e.message}",
                    e
                )

            if (runAttemptCount < 3) {
                // Exponential backoff strategy will avoid the request to repeat
                // too fast in case of failures.
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    /**
     * Update the state of all widgets and then force update UI
     */
    private suspend fun setWidgetState(
        glanceIds: List<GlanceId>,
        newState: AQIInfo,
        widget: GlanceAppWidget
    ) {
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = applicationContext,
                definition = AQIInfoStateDefinition,
                glanceId = glanceId,
                updateState = { newState }
            )
        }

        widget.updateAll(applicationContext)
    }
}