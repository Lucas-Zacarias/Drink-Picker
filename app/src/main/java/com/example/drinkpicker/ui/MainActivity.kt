package com.example.drinkpicker.ui

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
                    modifier = Modifier
                    .fillMaxSize(),
                    color = Color.White
                    ){
                    Components(windowSize = windowSize)
                }
            }
        }
    }


    @Composable
    fun Components(windowSize: WindowSize) {
        DrinkList(viewModel.drinkMockList.value, windowSize = windowSize)
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
                    DrinkItem(drink, isPortrait = true, windowSize = windowSize)
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
                        DrinkItem(drink, isPortrait = false, windowSize = windowSize)
                }
            }
        }
    }

    @Composable
    fun DrinkItem(drink: Drink, isPortrait: Boolean, windowSize: WindowSize) {
        DrinkView(
            id = drink.id,
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
        id: Long?,
        name: String = "",
        description: String = "",
        imageId: Int,
        imageDescription: String = "",
        isPortrait: Boolean,
        windowSize: WindowSize
    ) {
        val mapOfDrink = viewModel.mapOfDrinksVotesInDB.value
        var ssButtonState by remember { mutableStateOf(SSButtonState.IDLE) }
        var expandable by remember { mutableStateOf(false) }
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

            Box(contentAlignment = Alignment.TopStart, modifier = Modifier.padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(imageId),
                    contentDescription = imageDescription,
                    modifier = Modifier
                        .size(
                            height = if (isTabletDevice) 400.dp else 300.dp,
                            width = if (isTabletDevice) 300.dp else 200.dp
                        )
                        .clip(RoundedCornerShape(15)),
                    contentScale = ContentScale.Crop
                )

                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(
                            height = if(isTabletDevice) 75.dp else 55.dp,
                            width = if(isTabletDevice) 85.dp else 65.dp)
                        .padding(
                            start = if(isTabletDevice) 12.dp else 6.dp,
                            top = if(isTabletDevice) 12.dp else 6.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Black)
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.White)
                            .clickable { expandable = !expandable },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(if(isTabletDevice) 50.dp else 35.dp),
                            color = Color.White
                        ) {
                            WineCupProgress(mapOfDrink[id])
                        }

                        AnimatedVisibility(expandable) {
                            Text(
                                modifier = Modifier.padding(end = 2.dp),
                                text = "${mapOfDrink[id] ?: 0}",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            SSJetPackComposeProgressButton(
                type = SSButtonType.CUSTOM,
                width = if (isTabletDevice) 250.dp else 200.dp,
                height = if (isTabletDevice) 50.dp else 40.dp,
                onClick = {
                    ssButtonState = SSButtonState.LOADING
                    if (mapOfDrink[id!!] == null) {
                        viewModel.insertDrinkIntoDB(id)
                    } else {
                        viewModel.updateVotesForDrink(id)
                    }
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
                style = if (isTabletDevice)
                    MaterialTheme.typography.h6
                else
                    MaterialTheme.typography.subtitle1,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 4.dp)
            )

        }
    }

        @Composable
        private fun WineCupProgress(votes: Int?) {
            AndroidView(
                factory = { ctx ->
                    View(ctx).apply {
                        layoutParams = LinearLayout.LayoutParams(100, 100)
                        background = getDrawable(R.drawable.progress_wine_cup)
                    }
                },
                update = {
                    it.background.level = if(votes != null) 200 * votes else 1
                }
            )
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