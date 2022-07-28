package com.example.drinkpicker.drinksServer.drinks

import com.example.drinkpicker.R
import com.example.drinkpicker.data.models.Drink

object DrinksMocks {

    fun getDrinksMocks() : List<Drink>{

        val vino1 = Drink.Builder()
            .id(111111L)
            .name("Different")
            .description("Este vino está compuesto por...")
            .imageId(R.drawable.vino)
            .imageDescription("Imagen del vino 1")
            .build()

        val vino2 = Drink.Builder()
            .id(222222L)
            .name("182 Legend")
            .description("Este vino está compuesto por...")
            .imageId(R.drawable.vino)
            .imageDescription("Imagen del vino 2")
            .build()

        val vino3 = Drink.Builder()
            .id(333333L)
            .name("John Joseph Foos")
            .description("Este vino está compuesto por...")
            .imageId(R.drawable.vino)
            .imageDescription("Imagen del vino 3")
            .build()

        return listOf(vino1, vino2, vino3)
    }

}