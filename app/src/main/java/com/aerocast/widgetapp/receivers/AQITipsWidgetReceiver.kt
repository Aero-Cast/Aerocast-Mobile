package com.aerocast.widgetapp.receivers

import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.aerocast.widgetapp.widgets.AQITipsWidget
import com.aerocast.widgetapp.worker.AQIUpdateWorker
import android.content.Context

class AQITipsWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget = AQITipsWidget()

     override fun onEnabled(context: Context) {
        super.onEnabled(context)
        AQIUpdateWorker.enqueue(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        AQIUpdateWorker.cancel(context)
    }

}