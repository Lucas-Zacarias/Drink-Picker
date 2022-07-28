package com.example.drinkpicker.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.domain.repositories.DrinkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val drinkRepository: DrinkRepository
) : ViewModel(){

    val drinkMockList: MutableState<List<Drink>> = mutableStateOf(listOf())
    val mapOfDrinksVotesInDB: MutableState<Map<Long, Int>> = mutableStateOf(mutableMapOf())

    init{
        CoroutineScope(Dispatchers.Main).launch {
            drinkMockList.value = drinkRepository.getAllDrinks()
            mapOfDrinksVotesInDB.value = drinkRepository.getMapOfVotesByDrink()
        }
    }

    fun updateVotesForDrink(drinkId: Long){
        CoroutineScope(Dispatchers.IO).launch {
            drinkRepository.addVoteToDrink(drinkId)
            updateMapOfDrink()
        }
    }

    fun insertDrinkIntoDB(drinkId: Long){
        CoroutineScope(Dispatchers.IO).launch {
            drinkRepository.insertDrinkIntoDB(drinkId)
            updateMapOfDrink()
        }
    }

    private fun updateMapOfDrink(){
        CoroutineScope(Dispatchers.IO).launch {
            mapOfDrinksVotesInDB.value = drinkRepository.getMapOfVotesByDrink()
        }
    }
}