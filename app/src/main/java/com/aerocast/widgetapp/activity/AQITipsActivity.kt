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


data class AQILevelData(
    val range: String,
    val category: String,
    val description: String,
    val color: Color
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
        screenWidth < 400 -> 28.sp
        screenWidth < 450 -> 30.sp
        else -> 38.sp
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
                    fontSize = topicFontSize,
                    color = Color.Black,
                    fontFamily = SFpro,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(advisories) { advisory ->
                AdvisoryCard(data = advisory)
            }

            item {
                Spacer(Modifier.height(10.dp))

                AirQualityIndexGuide()
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

@Composable
fun AirQualityIndexGuide() {
    val screenWidth = LocalConfiguration.current.screenWidthDp

    val titleFontSize = when {
        screenWidth < 360 -> 20.sp
        screenWidth < 400 -> 28.sp
        screenWidth < 450 -> 30.sp
        else -> 38.sp
    }

    val cellFontSize = when {
        screenWidth < 360 -> 15.sp
        screenWidth < 400 -> 16.sp
        screenWidth < 450 -> 20.sp
        else -> 18.sp
    }

    val guidanceFontSize = when {
        screenWidth < 360 -> 15.sp
        screenWidth < 400 -> 16.sp
        screenWidth < 450 -> 20.sp
        else -> 18.sp
    }

    val sfPro = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_semi_bold, FontWeight.SemiBold)
    )

    val levels = listOf(
        AQILevelData(
            range = "0-50",
            category = "Good",
            description = "Enjoy outdoor activities.",
            color = Color(0xFFC5E1A5) // Soft green
        ),
        AQILevelData(
            range = "51-100",
            category = "Fair",
            description = "Sensitive people should take care.",
            color = Color(0xFFFFF59D) // Soft yellow
        ),
        AQILevelData(
            range = "101-150",
            category = "Unhealthy for Sensitive Groups",
            description = "Sensitive groups should limit prolonged outdoor exertion.",
            color = Color(0xFFFFCC80) // Soft orange
        ),
        AQILevelData(
            range = "151-200",
            category = "Unhealthy",
            description = "Reduce prolonged outdoor activity.",
            color = Color(0xFFEF9A9A) // Soft red
        ),
        AQILevelData(
            range = "201-300",
            category = "Very Unhealthy",
            description = "Reduce outdoor exposure.",
            color = Color(0xFFCE93D8) // Soft purple
        ),
        AQILevelData(
            range = "301-500",
            category = "Hazardous",
            description = "Avoid outdoor exertion.",
            color = Color(0xFF4A000B) // Dark maroon
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section Header
        Text(
            text = "Air Quality Index Guide",
            fontSize = titleFontSize,
            fontFamily = sfPro,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF322100)
        )

        Text(
            text = "Understand the AQI levels and what they mean for your health.",
            fontSize = cellFontSize,
            fontFamily = sfPro,
            color = Color(0xFF514A40)
        )

        // Column Header Titles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AQI",
                modifier = Modifier.weight(0.85f),
                fontSize = cellFontSize,
                fontFamily = sfPro,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF322100)
            )

            Text(
                text = "Category",
                modifier = Modifier.weight(1.5f),
                fontSize = cellFontSize,
                fontFamily = sfPro,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF322100)
            )

            Text(
                text = "Health Guidance",
                modifier = Modifier.weight(1.8f),
                fontSize = cellFontSize,
                fontFamily = sfPro,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF322100)
            )
        }

        // Rounded Cards for each AQI level
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            levels.forEach { level ->
                AQIRowCard(
                    level = level,
                    cellFontSize = cellFontSize,
                    guidanceFontSize = guidanceFontSize,
                    sfPro = sfPro
                )
            }
        }
    }
}

@Composable
fun AQIRowCard(
    level: AQILevelData,
    cellFontSize: androidx.compose.ui.unit.TextUnit,
    guidanceFontSize: androidx.compose.ui.unit.TextUnit,
    sfPro: FontFamily
) {
    // Determine text color based on background darkness for proper contrast
    val textColor = if (level.color == Color(0xFF4A000B)) Color.White else Color(0xFF322100)
    val descriptionTextColor = if (level.color == Color(0xFF4A000B)) Color(0xFFE0E0E0) else Color(0xFF423B30)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(level.color)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // AQI Range Column
        Text(
            text = level.range,
            modifier = Modifier.weight(0.85f),
            fontSize = cellFontSize,
            fontFamily = sfPro,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )

        // Category Column
        Text(
            text = level.category,
            modifier = Modifier.weight(1.5f),
            fontSize = cellFontSize,
            lineHeight = (cellFontSize.value + 3).sp,
            fontFamily = sfPro,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )

        // Health Guidance Column
        Text(
            text = level.description,
            modifier = Modifier.weight(1.8f),
            fontSize = guidanceFontSize,
            lineHeight = (guidanceFontSize.value + 3).sp,
            fontFamily = sfPro,
            fontWeight = FontWeight.Normal,
            color = descriptionTextColor
        )
    }
}

