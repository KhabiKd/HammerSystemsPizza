package com.example.hammersystemspizza.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hammersystemspizza.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Menu : BottomNavItem(
        route = "menu",
        title = "Меню",
        icon = Icons.Outlined.Home
    )

    object Profile : BottomNavItem(
        route = "profile",
        title = "Профиль",
        icon = Icons.Outlined.Person
    )

    object Cart : BottomNavItem(
        route = "cart",
        title = "Корзина",
        icon = Icons.Outlined.ShoppingCart
    )
}