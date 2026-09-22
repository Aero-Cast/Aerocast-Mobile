package com.aerocast.widgetapp.activity

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.tools.screenshot.PreviewTest

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
@PreviewTest
fun AQITipsScreenshot() {
    AQITipsScreen()
}