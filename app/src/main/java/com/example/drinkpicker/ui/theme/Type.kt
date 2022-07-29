package com.example.drinkpicker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.drinkpicker.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

private val Uchen = FontFamily(
    fonts = listOf(Font(R.font.uchen_regular, FontWeight.W400))
)

val UchenTypography = Typography(
    h1 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W500,
        fontSize = 30.sp,
    ),
    h2 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
    ),
    h3 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    h4 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
    ),
    h5 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
    ),
    h6 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Uchen,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = White
    ),
    caption = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Uchen,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)