package com.aerocast.widgetapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

class AQITipsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AQITipsScreen()
        }
    }
}

// Data model for advisory items
data class AdvisoryItemData(
    val title: String,
    val description: String,
    val cardBackgroundColor: Color,
    val badgeBackgroundColor: Color
)

@Composable
fun AQITipsScreen() {
    val warmBackground = Color(0xFFFAF6EE)
    val yellowCardBg = Color(0xFFFFF2D1)
    val blueCardBg = Color(0xFFE2F0FE)
    val redBadgeBg = Color(0xFF6B0B0B)
    val tealBadgeBg = Color(0xFF0C4E5E)

    val advisories = listOf(
        AdvisoryItemData(
            title = "Wear Mask Outside",
            description = "Air pollution just elevated, wearing a mask to reduce exposure to harmful particles.",
            cardBackgroundColor = yellowCardBg,
            badgeBackgroundColor = redBadgeBg
        ),
        AdvisoryItemData(
            title = "Avoid Traffic",
            description = "Stay away from busy roads to limit exposure to exhaust fumes, which can worsen breathing.",
            cardBackgroundColor = yellowCardBg,
            badgeBackgroundColor = redBadgeBg
        ),
        AdvisoryItemData(
            title = "Keep Windows Closed",
            description = "Keeping windows closed helps prevent polluted air from entering indoors.",
            cardBackgroundColor = yellowCardBg,
            badgeBackgroundColor = redBadgeBg
        ),
        AdvisoryItemData(
            title = "Limit Outdoor Chores",
            description = "Limiting outdoor activities helps prevent breathing difficulties, especially for sensitive group.",
            cardBackgroundColor = blueCardBg,
            badgeBackgroundColor = tealBadgeBg
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
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            items(advisories) { advisory ->
                AdvisoryCard(data = advisory)
            }

            item {
                Spacer(Modifier.height(14.dp))

                Text(
                    text = "What causes bad air quality?",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                val causesText = buildAnnotatedString {
                    append("Bad air quality happens when tiny particles and harmful gases get into the air. ")
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic
                        )
                    ) {
                        append("These come from cars, factories, power plants, and even burning wood or trash.")
                    }
                    append(" When the air is polluted, it can make breathing harder, irritate your lungs, and affect your heart and overall health.")
                }

                Text(
                    text = causesText,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF222222),
                    fontFamily = FontFamily.SansSerif
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
            // Image Placeholder: Replace this Box with Image(...) once you add your assets
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Image",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content Area (Pill Header + Description)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Pill Title Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(data.badgeBackgroundColor)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = data.title,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Description Text
                Text(
                    text = data.description,
                    fontSize = 13.sp,
                    lineHeight = 17.sp,
                    color = Color(0xFF2B2B2B),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun AQITipsScreenPreview() {
    AQITipsScreen()
}