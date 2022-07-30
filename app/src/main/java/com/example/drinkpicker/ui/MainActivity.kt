package com.example.drinkpicker.ui

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.drinkpicker.R
import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.ui.theme.DrinkPickerTheme
import com.example.drinkpicker.ui.theme.VoteButton
import com.example.drinkpicker.utils.WindowSize
import com.example.drinkpicker.utils.WindowType
import com.example.drinkpicker.utils.rememberWindowSize
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
                val windowSize = rememberWindowSize()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Components(windowSize = windowSize)
                }
            }
        }
    }


    @Composable
    fun Components(windowSize: WindowSize) {
        DrinkList(drinks = viewModel.drinkMockList.value, windowSize = windowSize)
    }

    @Composable
    fun DrinkList(drinks: List<Drink>, windowSize: WindowSize) {
        if (windowSize.width == WindowType.Compact //for mobile device size in portrait mode
            || windowSize.height == WindowType.Expanded //for tablet device size in portrait mode
        ) {
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
                    DrinkItem(drink, isPortrait = true, windowSize)
                }
            }
        } else if (windowSize.width == WindowType.Expanded //for tablet device size in landscape mode
            || windowSize.height == WindowType.Compact //for mobile device size in landscape mode
        ) {
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
                    DrinkItem(drink, isPortrait = false, windowSize)
                }
            }
        }
    }

    @Composable
    fun DrinkItem(drink: Drink, isPortrait: Boolean, windowSize: WindowSize) {
        DrinkView(
            name = drink.name ?: "",
            description = drink.description ?: "",
            imageId = drink.imageId!!,
            imageDescription = drink.imageDescription ?: "",
            isPortrait = isPortrait,
            windowSize = windowSize
        )
    }


    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun DrinkView(
        name: String = "",
        description: String = "",
        imageId: Int,
        imageDescription: String = "",
        isPortrait: Boolean,
        windowSize: WindowSize
    ) {
        var ssButtonState by remember { mutableStateOf(SSButtonState.IDLE) }
        val scrollState = rememberScrollState()
        val modifier = if (isPortrait) {
            Modifier.fillMaxSize()
        } else {
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(imageId),
                contentDescription = imageDescription,
                modifier = Modifier
                    .size(
                        height = if (isATabletDevice(isPortrait, windowSize)) 400.dp else 300.dp,
                        width = if (isATabletDevice(isPortrait, windowSize)) 300.dp else 200.dp
                    )
                    .clip(RoundedCornerShape(10)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            SSJetPackComposeProgressButton(
                type = SSButtonType.CUSTOM,
                width = if (isATabletDevice(isPortrait, windowSize)) 250.dp else 200.dp,
                height = if (isATabletDevice(isPortrait, windowSize)) 50.dp else 40.dp,
                onClick = {
                    ssButtonState = SSButtonState.LOADING
                    Timer().schedule(1000) {
                        ssButtonState = SSButtonState.IDLE
                    }
                },
                buttonState = ssButtonState,
                assetColor = Color.White,
                text = name,
                fontSize = if (isATabletDevice(isPortrait, windowSize))
                    MaterialTheme.typography.h2.fontSize
                else
                    MaterialTheme.typography.h4.fontSize,
                colors = ButtonDefaults.buttonColors(backgroundColor = VoteButton),
                customLoadingIconPainter = painterResource(R.drawable.loading_wine_cup),
                customLoadingEffect = SSCustomLoadingEffect(
                    rotation = true,
                    zoomInOut = false,
                    colorChanger = false
                )
            )

            Text(
                text = description,
                style = if (isATabletDevice(isPortrait, windowSize))
                    MaterialTheme.typography.h4
                else
                    MaterialTheme.typography.h5,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 4.dp)
            )

        }
    }

    private fun isATabletDevice(isPortrait: Boolean, windowSize: WindowSize): Boolean {
        /*
        * function used to set the size values of the elements according
        * to the type of device (cell phone, tablet)
        */
        val isATabletDevice: Boolean
        if (isPortrait) {
            isATabletDevice = windowSize.width != WindowType.Compact
        } else {
            isATabletDevice = windowSize.height != WindowType.Compact
        }
        return isATabletDevice
    }
}