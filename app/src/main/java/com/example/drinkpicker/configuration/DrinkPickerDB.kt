package com.example.drinkpicker.configuration

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.drinkpicker.data.database.DrinkTable
import com.example.drinkpicker.domain.persistence.DrinkDao

@Database(
    version = 1,
    entities = [DrinkTable::class]
)

abstract class DrinkPickerDB : RoomDatabase(){

    abstract fun drinkDao() : DrinkDao

    companion object{
        const val DATABASE_NAME = "drink_picker_db"
    }
}
