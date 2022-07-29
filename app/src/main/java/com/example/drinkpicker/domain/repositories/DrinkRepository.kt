package com.example.drinkpicker.domain.repositories

import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.drinksServer.DrinksServer
import javax.inject.Inject

class DrinkRepository @Inject constructor(
    private val drinksServer: DrinksServer
) {

    fun getAllDrinks(): List<Drink> {
        return drinksServer.getAllDrinksMocks()
    }

}