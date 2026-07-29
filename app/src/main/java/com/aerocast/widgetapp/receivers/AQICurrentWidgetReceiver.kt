package com.aerocast.widgetapp.receivers

import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.aerocast.widgetapp.widgets.AQICurrentWidget

class AQICurrentWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget = AQICurrentWidget()

}