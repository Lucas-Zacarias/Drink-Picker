package com.example.drinkpicker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DRINK_TABLE")
data class DrinkTable(
    @PrimaryKey
    val drinkId: Long,
    var votes: Int
)