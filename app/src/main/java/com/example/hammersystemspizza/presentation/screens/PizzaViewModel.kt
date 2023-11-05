package com.example.hammersystemspizza.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.hammersystemspizza.PizzaApplication
import com.example.hammersystemspizza.data.local.PizzaLocalRepository
import com.example.hammersystemspizza.data.local.database.AppDatabase
import com.example.hammersystemspizza.data.local.entity.asPizza
import com.example.hammersystemspizza.data.local.entity.toEntity
import com.example.hammersystemspizza.data.remote.PizzaApiRepository
import com.example.hammersystemspizza.data.remote.PizzaApiRepositoryImpl
import com.example.hammersystemspizza.domain.model.Pizza
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

sealed interface PizzaUiState {
    data class Success(val pizzas: List<Pizza>) : PizzaUiState
    object Error : PizzaUiState
    object Loading : PizzaUiState
}

@HiltViewModel
class PizzaViewModel @Inject constructor(
    private val pizzaRepository: PizzaApiRepository,
    private val dataBase: PizzaLocalRepository
) : ViewModel() {

    var pizzaUiState: PizzaUiState by mutableStateOf(PizzaUiState.Loading)
        private set

    init {
        getPizzas()
    }

    fun getPizzas() {
        viewModelScope.launch {
            pizzaUiState = PizzaUiState.Loading
            pizzaUiState = try {
                val pizzas = pizzaRepository.getPizzas()
                dataBase.insertPizzas(pizzas.map { it.toEntity() })
                PizzaUiState.Success(pizzas)
            } catch (e: IOException) {
                val cachedPizzasFlow = dataBase.getPizzas()
                val cachedPizzas = cachedPizzasFlow.firstOrNull()
                if (cachedPizzas != null) {
                    PizzaUiState.Success(cachedPizzas.map { it.asPizza() })
                } else {
                    PizzaUiState.Error
                }
            } catch (e: HttpException) {
                PizzaUiState.Error
            }
        }
    }
}
