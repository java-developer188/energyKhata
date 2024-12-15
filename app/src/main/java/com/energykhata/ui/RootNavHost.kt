package com.energykhata.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.energykhata.roomdb.EnergyKhataDatabase
import com.energykhata.roomdb.repositories.MeterRepository
import com.energykhata.roomdb.repositories.ReadingRepository
import com.energykhata.roomdb.repositories.UserRepository
import com.energykhata.ui.screens.calculation.CalculationScreen
import com.energykhata.ui.screens.help.HelpScreen
import com.energykhata.ui.screens.history.HistoryScreen
import com.energykhata.ui.screens.main.MainScreen
import com.energykhata.ui.screens.splash.SplashScreen

@Composable
fun RootNavHost(db: EnergyKhataDatabase) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH.route
    ) {
        composable(Screen.SPLASH.route) {
            SplashScreen(
                navController
            )
        }
        composable(Screen.MAIN.route) {
            MainScreen(
                navController,
                MeterRepository(db),
                UserRepository(db)
            )
        }
        composable(Screen.HELP.route) {
            HelpScreen(navController)
        }
        composable(
            route = Screen.CALCULATION.route + "/{meterId}",
            arguments = listOf(
                navArgument(name = "meterId") {
                    type = NavType.IntType
                }
            )
        )
        { backstackEntry ->
            CalculationScreen(
                navController, backstackEntry.arguments?.getInt("meterId"),
                MeterRepository(db), ReadingRepository(db)
            )
        }
        composable(
            route = Screen.HISTORY.route + "/{meterId}",
            arguments = listOf(
                navArgument(name = "meterId") {
                    type = NavType.IntType
                }
            )
        )
        { backstackEntry ->
            HistoryScreen(
                navController, backstackEntry.arguments?.getInt("meterId"),
                MeterRepository(db), ReadingRepository(db)
            )
        }
    }
}

private object Route {
    const val SPLASH = "splash"
    const val MAIN = "main"
    const val CALCULATION = "calculation"
    const val HISTORY = "history"
    const val HELP = "help"
    const val CONFIG = "config"
}

sealed class Screen(val route: String) {
    object SPLASH : Screen(Route.SPLASH)
    object MAIN : Screen(Route.MAIN)
    object CALCULATION : Screen(Route.CALCULATION)
    object HISTORY : Screen(Route.HISTORY)
    object HELP : Screen(Route.HELP)
    object CONFIG : Screen(Route.CONFIG)
}