package com.example.hammersystemspizza.data.remote

import com.example.hammersystemspizza.data.remote.api.PizzaApiService
import com.example.hammersystemspizza.data.remote.entity.PizzaApiEntity
import com.example.hammersystemspizza.data.remote.entity.asPizza
import com.example.hammersystemspizza.domain.model.Pizza
import javax.inject.Inject

interface PizzaApiRepository {
    suspend fun getPizzas(): List<Pizza>
}

class PizzaApiRepositoryImpl @Inject constructor(
    private val pizzaApiService: PizzaApiService
) : PizzaApiRepository {
    override suspend fun getPizzas(): List<Pizza> = pizzaApiService.getPizzas().map { it.asPizza() }
}