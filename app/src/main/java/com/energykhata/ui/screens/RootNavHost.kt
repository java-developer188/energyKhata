package com.energykhata.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.UserRepository

@Composable
fun RootNavHost(db: EnergyKhataDatabase) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CONFIG.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(MeterRepository(db))
        }
        composable(Screen.CONFIG.route) {
            ConfigurationScreen(UserRepository(db),MeterRepository(db))
        }
    }
}

private object Route {
    const val MAIN = "main"
    const val CONFIG = "config"
}

sealed class Screen(val route: String) {
    object MAIN : Screen(Route.MAIN)
    object CONFIG : Screen(Route.CONFIG)
}