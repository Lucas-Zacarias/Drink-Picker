package com.example.drinkpicker.domain.repositories

import com.example.drinkpicker.data.models.Drink
import com.example.drinkpicker.domain.persistence.DrinkDao
import com.example.drinkpicker.drinksServer.DrinksServer
import javax.inject.Inject

class DrinkRepository @Inject constructor(
    private val drinksServer: DrinksServer,
    private val drinkDao: DrinkDao)
{

    fun getAllDrinks() : List<Drink>{
        return drinksServer.getAllDrinksMocks()
    }

    suspend fun addVoteToDrink(drinkId: Long){
        //db addVoteToDrink
        drinkDao.addVoteToDrink(drinkId)
    }

    suspend fun insertDrinkIntoDB(drinkId: Long){
        //db insertDrink
        drinkDao.insertDrink(drinkId)
    }

    private suspend fun checkExistenceOfDrinkInDB(drinkId: Long) : Boolean{
        //db isDrinkInsertIntoDB
        return drinkDao.isDrinkInsertIntoDB(drinkId)
    }

    suspend fun getMapOfVotesByDrink() : Map<Long, Int>{
        val drinksMock = drinksServer.getAllDrinksMocks()
        val drinksDB = drinkDao.getAllDrinks()
        val mapOfDrinkVotes: MutableMap<Long, Int> = mutableMapOf()
        drinksMock.forEach{ drink ->
            if(checkExistenceOfDrinkInDB(drink.id!!)){
                mapOfDrinkVotes[drink.id] = drinksDB.first{it.drinkId == drink.id}.votes
            }
        }
        return mapOfDrinkVotes
    }
}