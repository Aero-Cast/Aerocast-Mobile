package com.aerocast.widgetapp.receivers

import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.aerocast.widgetapp.widgets.AQICurrentWidget
import com.aerocast.widgetapp.worker.AQIUpdateWorker
import android.content.Context

class AQICurrentWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget = AQICurrentWidget()

     override fun onEnabled(context: Context) {
        super.onEnabled(context)
        AQIUpdateWorker.enqueue(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        AQIUpdateWorker.cancel(context)
    }

}