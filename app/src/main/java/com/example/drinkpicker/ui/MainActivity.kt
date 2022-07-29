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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.drinkpicker.R
import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.ui.theme.DrinkPickerTheme
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
                Surface(modifier = Modifier.fillMaxSize().background(color = Color.White)){
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
        var orientation by remember { mutableStateOf(resources.configuration.orientation) }


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
                horizontalArrangement = Arrangement.SpaceAround,
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
            id = drink.id,
            name = drink.name ?: "",
            description = drink.description ?: "",
            imageId = drink.imageId!!,
            imageDescription = drink.imageDescription ?: ""
        )
    }


    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun DrinkView(
        id: Long?,
        name: String = "",
        description: String = "",
        imageId: Int,
        imageDescription: String = ""
    ) {
        val mapOfDrink = viewModel.mapOfDrinksVotesInDB.value
        var ssButtonState by remember { mutableStateOf(SSButtonState.IDLE) }
        var expandable by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()
        var modifier =
            if(resources.configuration.orientation == ORIENTATION_LANDSCAPE)
                Modifier.fillMaxSize().verticalScroll(scrollState)
            else
                Modifier.fillMaxSize()

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

           /* Box(contentAlignment = Alignment.TopStart, modifier = Modifier.padding(bottom = 8.dp)) {
                Image(
                    painter = painterResource(imageId),
                    contentDescription = imageDescription,
                    modifier = Modifier.size(height = 300.dp, width = 200.dp)
                        .clip(RoundedCornerShape(20)),
                    contentScale = ContentScale.Crop
                )

                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(height = 60.dp, width = 70.dp)
                        .padding(start = 12.dp, top = 12.dp),
                    border = BorderStroke(width = 1.dp, color = Color.Black)
                ) {
                    Row(
                        modifier = Modifier.background(color = Color.White),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp)
                                .clickable { expandable = !expandable },
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
            }*/

            Image(
                painter = painterResource(imageId),
                contentDescription = imageDescription,
                modifier = Modifier.size(height = 300.dp, width = 200.dp)
                    .clip(RoundedCornerShape(20)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            SSJetPackComposeProgressButton(
                type = SSButtonType.CUSTOM,
                width = 200.dp,
                height = 40.dp,
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
                assetColor = Color.Black,
                text = name,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                customLoadingIconPainter = painterResource(R.drawable.loading_wine_cup),
                customLoadingEffect = SSCustomLoadingEffect(
                    rotation = true,
                    zoomInOut = false,
                    colorChanger = false
                )
            )

            Text(text = description, modifier = Modifier.padding(top = 4.dp))

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
                    var level = 0
                    if (votes != null) {
                        level = 200 * votes
                    }
                    it.background.level = level
                }
            )
        }
}