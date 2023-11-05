package com.example.hammersystemspizza.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hammersystemspizza.data.local.entity.PizzaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PizzaDao {
    @Query("SELECT * FROM pizzas")
    fun getAllPizzas(): Flow<List<PizzaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPizzas(pizzas: List<PizzaEntity>)
}