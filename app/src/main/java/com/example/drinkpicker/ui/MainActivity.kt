package com.example.drinkpicker.ui

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drinkpicker.R
import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.ui.theme.DrinkPickerTheme
import com.example.drinkpicker.ui.theme.VoteButton
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonState
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonType
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSCustomLoadingEffect
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSJetPackComposeProgressButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrinkPickerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White) {
                    Components()
                }
            }
        }
    }


    @Composable
    fun Components() {
        DrinkList(viewModel.drinkMockList.value)
    }

    @Composable
    fun DrinkList(drinks: List<Drink>) {
        val orientation = resources.configuration.orientation


        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(
                    items = drinks,
                    key = { drink ->
                        drink.id!!
                    }) { drink ->
                    DrinkItem(drink)
                }
            }
        } else if (orientation == ORIENTATION_LANDSCAPE) {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(
                    items = drinks,
                    key = { drink ->
                        drink.id!!
                    }) { drink ->
                        DrinkItem(drink)
                }
            }
        }
    }

    @Composable
    fun DrinkItem(drink: Drink) {
        DrinkView(
            name = drink.name ?: "",
            description = drink.description ?: "",
            imageId = drink.imageId!!,
            imageDescription = drink.imageDescription ?: ""
        )
    }


    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun DrinkView(
        name: String = "",
        description: String = "",
        imageId: Int,
        imageDescription: String = ""
    ) {
        var ssButtonState by remember { mutableStateOf(SSButtonState.IDLE) }
        val scrollState = rememberScrollState()
        val modifier =
            if(resources.configuration.orientation == ORIENTATION_LANDSCAPE)
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            else
                Modifier.fillMaxSize()

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(imageId),
                contentDescription = imageDescription,
                modifier = Modifier
                    .size(height = 400.dp, width = 300.dp)
                    .clip(RoundedCornerShape(10)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            SSJetPackComposeProgressButton(
                type = SSButtonType.CUSTOM,
                width = 250.dp,
                height = 50.dp,
                onClick = {
                    ssButtonState = SSButtonState.LOADING
                    Timer().schedule(1000) {
                        ssButtonState = SSButtonState.IDLE
                    }
                },
                buttonState = ssButtonState,
                assetColor = Color.White,
                text = name,
                fontSize = 24.sp,
                colors = ButtonDefaults.buttonColors(backgroundColor = VoteButton),
                customLoadingIconPainter = painterResource(R.drawable.loading_wine_cup),
                customLoadingEffect = SSCustomLoadingEffect(
                    rotation = true,
                    zoomInOut = false,
                    colorChanger = false
                )
            )

            Text(text = description,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 4.dp),
                color = Color.Black)

        }
    }
}