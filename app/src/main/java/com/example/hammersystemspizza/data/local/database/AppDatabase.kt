package com.example.hammersystemspizza.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hammersystemspizza.data.local.dao.PizzaDao
import com.example.hammersystemspizza.data.local.entity.PizzaEntity

@Database(entities = [PizzaEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pizzaDao(): PizzaDao
}