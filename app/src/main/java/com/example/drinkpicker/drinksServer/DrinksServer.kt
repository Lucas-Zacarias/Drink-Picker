package com.example.drinkpicker.drinksServer

import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.drinksServer.drinks.DrinksMocks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DrinksServer @Inject constructor() {

    fun getAllDrinksMocks() : List<Drink>{
        return DrinksMocks.getDrinksMocks()
    }

    fun getDrinkById(id: Long) : Drink {
        val drinkList = getAllDrinksMocks()
        return drinkList.first{ drink ->  drink.id == id}
    }

}