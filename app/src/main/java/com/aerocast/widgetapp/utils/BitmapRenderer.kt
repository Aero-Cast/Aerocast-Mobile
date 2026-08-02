package com.aerocast.widgetapp.utils

import android.content.Context
import android.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun createCustomTextBitmap(
    context: Context,
    text: String,
    textColor: Color,
    fontSize: Float,
    fontFamily: String
): Bitmap {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor.toArgb()
        textSize = fontSize
        typeface = Typeface.createFromAsset(
            context.assets,
            fontFamily
        )
    }

    val bounds = Rect()

    paint.getTextBounds(
        text,
        0,
        text.length,
        bounds
    )

    val width = bounds.width()
    val height = bounds.height()

    val bitmap = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    canvas.drawText(
        text,
        -bounds.left.toFloat(),
        -bounds.top.toFloat(),
        paint
    )

    return bitmap
}

fun createPillTextBitmap(
    context: Context,
    text: String,
    textColor: Color,
    backgroundColor: Color,
    fontSize: Float,
    fontFamily: String,
    horizontalPadding: Int = 0,
    verticalPadding: Int = 0,
    cornerRadius: Float = 0f,
): Bitmap {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.textSize = fontSize
        this.color = textColor.toArgb()
        typeface = Typeface.createFromAsset(
            context.assets,
            fontFamily
        )
    }

    val bounds = Rect()

    paint.getTextBounds(
        text,
        0,
        text.length,
        bounds
    )

    val width = bounds.width() + (horizontalPadding * 2)
    val height = bounds.height() + (verticalPadding * 2)

    val bitmap = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = backgroundColor.toArgb()
    }

    val rect = RectF(
        0f,
        0f,
        width.toFloat(),
        height.toFloat()
    )

    canvas.drawRoundRect(
        rect,
        cornerRadius,
        cornerRadius,
        backgroundPaint
    )

    canvas.drawText(
        text,
        horizontalPadding.toFloat() - bounds.left,
        verticalPadding.toFloat() - bounds.top,
        paint
    )

    return bitmap
}