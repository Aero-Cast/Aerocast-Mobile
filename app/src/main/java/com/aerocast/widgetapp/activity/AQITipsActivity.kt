package com.aerocast.widgetapp.activity

import com.aerocast.widgetapp.R
import android.os.Bundle
import androidx.annotation.DrawableRes
import android.graphics.Typeface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AQITipsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AQITipsScreen()
        }
    }
}

data class AdvisoryItemData(
    val title: String,
    val description: String,
    val cardBackgroundColor: Color,
    val badgeBackgroundColor: Color,
    @DrawableRes val image: Int
)

@Composable
fun AQITipsScreen() {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val headerFontSize = when {
        screenWidth < 360 -> 20.sp
        screenWidth < 400 -> 24.sp
        screenWidth < 450 -> 26.sp
        else -> 28.sp
    }

    val topicFontSize = when {
        screenWidth < 360 -> 20.sp
        screenWidth < 400 -> 23.sp
        screenWidth < 450 -> 26.sp
        else -> 28.sp
    }

    val descriptionFontSize = when {
        screenWidth < 360 -> 13.sp
        screenWidth < 400 -> 15.sp
        screenWidth < 450 -> 17.sp
        else -> 20.sp
    }

    val descriptionLineHeight = when {
        screenWidth < 360 -> 18.sp
        screenWidth < 400 -> 20.sp
        screenWidth < 450 -> 22.sp
        else -> 24.sp
    }

    val warmBackground = Color(0xFFFAF6EE)
    val yellowCardBg = Color(0xFFFFF2D1)
    val blueCardBg = Color(0xFFE2F0FE)
    val redBadgeBg = Color(0xFF6B0B0B)
    val tealBadgeBg = Color(0xFF0C4E5E)

    val SFpro = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_semi_bold, FontWeight.SemiBold)
    )

    val advisories = listOf(
        AdvisoryItemData(
            title = "Wear Mask Outside",
            description = "Air pollution just elevated, wearing a mask to reduce exposure to harmful particles.",
            cardBackgroundColor = yellowCardBg,
            badgeBackgroundColor = redBadgeBg,
            image = R.drawable.mask_image
        ),
        AdvisoryItemData(
            title = "Avoid Traffic",
            description = "Stay away from busy roads to limit exposure to exhaust fumes, which can worsen breathing.",
            cardBackgroundColor = yellowCardBg,
            badgeBackgroundColor = redBadgeBg,
            image = R.drawable.traffic_image
        ),
        AdvisoryItemData(
            title = "Keep Windows Closed",
            description = "Keeping windows closed helps prevent polluted air from entering indoors.",
            cardBackgroundColor = yellowCardBg,
            badgeBackgroundColor = redBadgeBg,
            image = R.drawable.windows_image
        ),
        AdvisoryItemData(
            title = "Limit Outdoor Chores",
            description = "Limiting outdoor activities helps prevent breathing difficulties, especially for sensitive group.",
            cardBackgroundColor = blueCardBg,
            badgeBackgroundColor = tealBadgeBg,
            image = R.drawable.skycraper_image
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = warmBackground
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                Text(
                    text = "Health Advisory",
                    fontSize = headerFontSize,
                    color = Color.Black,
                    fontFamily = SFpro,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(advisories) { advisory ->
                AdvisoryCard(data = advisory)
            }

            item {
                Spacer(Modifier.height(10.dp))

                Text(
                    text = "What causes bad air quality?",
                    fontSize = topicFontSize,
                    lineHeight = 28.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontFamily = SFpro,
                    fontWeight = FontWeight.SemiBold
                )
            }

            item {
                val causesText = buildAnnotatedString {
                    append("Bad air quality happens when tiny particles and harmful gases get into the air. ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("These come from cars, factories, power plants, and even burning wood or trash.")
                    }
                    append(" When the air is polluted, it can make breathing harder, irritate your lungs, and affect your heart and overall health.")
                }

                Text(
                    text = causesText,
                    fontSize = descriptionFontSize,
                    lineHeight = descriptionLineHeight,
                    color = Color(0xFF222222),
                    fontFamily = SFpro,
                    fontWeight = FontWeight.Normal
                )
            }

            item {
                Spacer(Modifier.height(14.dp))

                Text(
                    text = "Effects of Long Term Exposure",
                    fontSize = topicFontSize,
                    lineHeight = 28.sp,
                    fontFamily = SFpro,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                val effectsText = "Chronic exposure is linked to a permanent 10% to 15% reduction in lung capacity over a decade, even in healthy adults. Long-term exposure keeps your body in a state of \"systemic inflammation.\" This is a leading cause of secondary issues like Type 2 Diabetes and accelerated cognitive aging."

                Text(
                    text = effectsText,
                    fontSize = descriptionFontSize,
                    lineHeight = descriptionLineHeight,
                    color = Color(0xFF222222),
                    fontFamily = SFpro,
                    fontWeight = FontWeight.Normal
                )
            }

            item {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun AdvisoryCard(data: AdvisoryItemData) {

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val titleFontSize = when {
        screenWidth < 360 -> 12.sp
        screenWidth < 400 -> 15.sp
        screenWidth < 450 -> 18.sp
        else -> 20.sp
    }

    val descriptionFontSize = when {
        screenWidth < 360 -> 15.sp
        screenWidth < 400 -> 16.sp
        screenWidth < 450 -> 20.sp
        else -> 23.sp
    }

    val descriptionLineHeight = when {
        screenWidth < 360 -> 14.sp
        screenWidth < 400 -> 17.sp
        screenWidth < 450 -> 20.sp
        else -> 23.sp
    }

    val imageSize = when {
        screenWidth < 360 -> 75.dp
        screenWidth < 400 -> 90.dp
        screenWidth < 450 -> 110.dp
        else -> 110.dp
    }

    val SFpro = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_semi_bold, FontWeight.SemiBold)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(data.cardBackgroundColor)
            .padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = data.image),
                contentDescription = data.title,
                modifier = Modifier
                    .size(imageSize)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(data.badgeBackgroundColor)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = data.title,
                        color = Color.White,
                        fontSize = titleFontSize,
                        fontFamily = SFpro,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = data.description,
                    fontSize = descriptionFontSize,
                    lineHeight = descriptionLineHeight,
                    color = Color(0xFF2B2B2B),
                    fontFamily = SFpro,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}