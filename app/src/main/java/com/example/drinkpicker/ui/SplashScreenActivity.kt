package com.example.drinkpicker.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.drinkpicker.R
import com.example.drinkpicker.ui.theme.DrinkPickerTheme
import java.util.*
import kotlin.concurrent.schedule

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goToMainActivity()

        setContent {

            DrinkPickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Components()
                }
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Timer().schedule(2000) {
            startActivity(intent)
            finish()
        }
    }


}

@Composable
private fun Components() {
    Box(contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(R.drawable.drink_picker_icon),
            contentDescription = "Drink Picker logo",
            modifier = Modifier.size(200.dp).clip(shape = CircleShape),
            alignment = Alignment.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    DrinkPickerTheme {
        Components()
    }
}