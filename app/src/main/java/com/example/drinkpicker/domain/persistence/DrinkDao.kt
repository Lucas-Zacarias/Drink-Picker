package com.example.drinkpicker.domain.persistence

import androidx.room.Dao
import androidx.room.Query
import com.example.drinkpicker.data.database.DrinkTable

@Dao
interface DrinkDao{

    @Query("SELECT * FROM DRINK_TABLE")
    suspend fun getAllDrinks() : List<DrinkTable>

    @Query("UPDATE  DRINK_TABLE SET votes = votes + 1 WHERE drinkId = :drinkId")
    suspend fun addVoteToDrink(drinkId: Long)

    @Query("INSERT INTO DRINK_TABLE VALUES(:drinkId, :votes)")
    suspend fun insertDrink(drinkId: Long, votes:Int = 1)

    @Query("SELECT EXISTS (SELECT * FROM DRINK_TABLE WHERE drinkId = :drinkId) ")
    suspend fun isDrinkInsertIntoDB(drinkId: Long) : Boolean
    /*
    * Return 1 means true and 0 means false
    */
}