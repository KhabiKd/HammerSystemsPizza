package com.example.hammersystemspizza.di

import android.content.Context
import androidx.room.Room
import com.example.hammersystemspizza.data.local.PizzaLocalRepository
import com.example.hammersystemspizza.data.local.PizzaOfflineRepository
import com.example.hammersystemspizza.data.local.dao.PizzaDao
import com.example.hammersystemspizza.data.local.database.AppDatabase
import com.example.hammersystemspizza.data.remote.PizzaApiRepository
import com.example.hammersystemspizza.data.remote.PizzaApiRepositoryImpl
import com.example.hammersystemspizza.data.remote.api.PizzaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "item_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase) : PizzaDao{
        return appDatabase.pizzaDao()
    }

    @Provides
    @Singleton
    fun provideBeerApi(): PizzaApiService {
        return Retrofit.Builder()
            .baseUrl(PizzaApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideApiRepository(pizzaApiService: PizzaApiService): PizzaApiRepository = PizzaApiRepositoryImpl(pizzaApiService)

    @Provides
    @Singleton
    fun provideLocalRepository(pizzaDao: PizzaDao): PizzaLocalRepository = PizzaOfflineRepository(pizzaDao)

}