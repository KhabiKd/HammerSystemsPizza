package com.example.hammersystemspizza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hammersystemspizza.domain.model.Pizza

@Entity(tableName = "pizzas")
data class PizzaEntity(
    @PrimaryKey val name: String,
    val description: String,
    val price: Int,
    val img_src: String
)

fun Pizza.toEntity() = PizzaEntity(
    name = name,
    description = description,
    price = price,
    img_src = img_src
)

fun PizzaEntity.asPizza() = Pizza(
    name = name,
    description = description,
    price = price,
    img_src = img_src
)