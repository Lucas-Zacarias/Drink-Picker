package com.example.drinkpicker.ui

import android.content.res.Configuration
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
import androidx.compose.ui.unit.sp
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
                    color = Color.Black
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
                        DrinkItem(drink, isPortrait = true, windowSize)
                    }
                }
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
            val isTabletDevice = isATabletDevice(isPortrait, windowSize)
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
                            height = if (isTabletDevice) 400.dp else 300.dp,
                            width = if (isTabletDevice) 300.dp else 200.dp
                        )
                        .clip(RoundedCornerShape(10)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                SSJetPackComposeProgressButton(
                    type = SSButtonType.CUSTOM,
                    width = if (isTabletDevice) 250.dp else 200.dp,
                    height = if (isTabletDevice) 50.dp else 40.dp,
                    onClick = {
                        ssButtonState = SSButtonState.LOADING
                        Timer().schedule(1000) {
                            ssButtonState = SSButtonState.IDLE
                        }
                    },
                    buttonState = ssButtonState,
                    assetColor = Color.White,
                    text = name,
                        fontSize = if (isTabletDevice)
                    MaterialTheme.typography.h5.fontSize
                else
                    MaterialTheme.typography.h6.fontSize,
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
                    fontSize = if(isTabletDevice) 18.sp else 16.sp,
                    color = Color.White,
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
                /*
            * if the device is in portrait mode, windowSize.width is used to know
            * if it is a cell phone or a tablet.
            * If it is different of WindowType.Compact it means that it is a tablet,
            * otherwise it is a cell phone.
            */
            } else {
                isATabletDevice = windowSize.height != WindowType.Compact
                /*
            * if the device is in landscape mode, windowSize.height is used to know
            * if it is a cell phone or a tablet.
            * If it is different to WindowType.Compact it means that it is a tablet,
            * otherwise it is a cell phone.
            */
            }
            return isATabletDevice
        }
}