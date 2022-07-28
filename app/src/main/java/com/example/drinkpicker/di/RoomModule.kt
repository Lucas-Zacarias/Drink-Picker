package com.example.drinkpicker.di
//di is for Dependency Injection (DI)

import android.content.Context
import androidx.room.Room
import com.example.drinkpicker.configuration.DrinkPickerDB
import com.example.drinkpicker.domain.persistence.DrinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideDrinkPickerDB(@ApplicationContext context: Context): DrinkPickerDB {
        return Room.databaseBuilder(
            context,
            DrinkPickerDB::class.java,
            DrinkPickerDB.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideDrinkDao(drinkPickerDB: DrinkPickerDB) : DrinkDao{
        return drinkPickerDB.drinkDao()
    }
}

