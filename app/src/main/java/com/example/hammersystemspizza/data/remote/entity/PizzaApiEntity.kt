package com.example.hammersystemspizza.data.remote.entity

import com.example.hammersystemspizza.domain.model.Pizza
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PizzaApiEntity(
    val name: String,
    val description: String,
    val price: Int,
//    @SerialName("img_src")
    val img_src: String
)

fun PizzaApiEntity.asPizza() = Pizza(
    name = name,
    description = description,
    price = price,
    img_src = img_src
)
