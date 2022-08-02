package com.example.drinkpicker.drinksServer.drinks

import com.example.drinkpicker.R
import com.example.drinkpicker.data.models.Drink

object DrinksMocks {

    fun getDrinksMocks() : List<Drink>{

        val vino1 = Drink.Builder()
            .id(111111L)
            .name("Different")
            .imageId(R.drawable.drink_different)
            .imageDescription("Imagen del trago Different")
            .description("Fuerte, fresco y con actitud frutal")
            .build()

        val vino2 = Drink.Builder()
            .id(222222L)
            .name("182 Legend")
            .imageId(R.drawable.drink_legend_182)
            .imageDescription("Imagen del trago 182 Legend")
            .description("Dulce, fresco y sofisticado")
            .build()

        val vino3 = Drink.Builder()
            .id(333333L)
            .name("John Joseph Foos")
            .imageId(R.drawable.drink_john_joseph_foos)
            .imageDescription("Imagen del trago John Joseph Foos")
            .description("Impronta, cítrico, intenso y fresco")
            .build()

        return listOf(vino1, vino2, vino3)
    }

}