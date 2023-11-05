package com.example.hammersystemspizza.data.remote.api

import com.example.hammersystemspizza.data.remote.entity.PizzaApiEntity
import retrofit2.http.GET

interface PizzaApiService {
    @GET("pizzas")
    suspend fun getPizzas(): List<PizzaApiEntity>

    companion object {
        const val BASE_URL = "https://hammersystemspizza-backend.onrender.com/"
    }
}