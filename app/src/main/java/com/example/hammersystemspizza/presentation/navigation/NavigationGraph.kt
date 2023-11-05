package com.example.hammersystemspizza.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hammersystemspizza.presentation.screens.CartScreen
import com.example.hammersystemspizza.presentation.screens.MenuScreen
import com.example.hammersystemspizza.presentation.screens.PizzaViewModel
import com.example.hammersystemspizza.presentation.screens.ProfileScreen

@Composable
fun NavigationGraph(navController: NavHostController, pizzaViewModel: PizzaViewModel) {
    NavHost(navController, startDestination = BottomNavItem.Menu.route) {
        composable(
            BottomNavItem.Menu.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(0)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(0)
                )
            },
        ) {
            MenuScreen(pizzaViewModel.pizzaUiState)
        }
        composable(
            BottomNavItem.Profile.route,
            enterTransition = {
                val direction = if (navController.previousBackStackEntry?.destination?.route == BottomNavItem.Menu.route) {
                    AnimatedContentTransitionScope.SlideDirection.Left
                } else {
                    AnimatedContentTransitionScope.SlideDirection.Left
                }
                slideIntoContainer(
                    towards = direction,
                    animationSpec = tween(0)
                )
            },
            exitTransition = {
                val direction = if (navController.currentBackStackEntry?.destination?.route == BottomNavItem.Menu.route) {
                    AnimatedContentTransitionScope.SlideDirection.Right
                } else {
                    AnimatedContentTransitionScope.SlideDirection.Left
                }
                slideOutOfContainer(
                    towards = direction,
                    animationSpec = tween(0)
                )
            },
        ) {
            ProfileScreen()
        }
        composable(
            BottomNavItem.Cart.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(0)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(0)
                )
            },
        ) {
            CartScreen()
        }
    }
}
