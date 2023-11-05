package com.example.hammersystemspizza.data.local

import com.example.hammersystemspizza.data.local.dao.PizzaDao
import com.example.hammersystemspizza.data.local.entity.PizzaEntity
import com.example.hammersystemspizza.domain.model.Pizza
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PizzaLocalRepository {

    fun getPizzas(): Flow<List<PizzaEntity>>

    suspend fun insertPizzas(pizzas: List<PizzaEntity>)

}

class PizzaOfflineRepository @Inject constructor(
    private val pizzaDao: PizzaDao
): PizzaLocalRepository {
    override fun getPizzas(): Flow<List<PizzaEntity>> = pizzaDao.getAllPizzas()

    override suspend fun insertPizzas(pizzas: List<PizzaEntity>) = pizzaDao.insertPizzas(pizzas)
}