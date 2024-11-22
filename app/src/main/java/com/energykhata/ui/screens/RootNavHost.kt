package com.energykhata.ui.screens

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

@Composable
fun RootNavHost(db: EnergyKhataDatabase) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MAIN.route
    ) {
        composable(Screen.MAIN.route) {
            MainScreen(navController,MeterRepository(db), UserRepository(db))
        }
        composable(
            route=Screen.METERSCREEN.route+"/{meterId}",
            arguments = listOf(
                navArgument(name = "meterId"){
                    type= NavType.IntType
                }
            )
        )
        {backstackEntry ->
            MeterScreen(navController, backstackEntry.arguments?.getInt("meterId"),
                MeterRepository(db), ReadingRepository(db)
            )
        }
        composable(
            route=Screen.READINGSCREEN.route+"/{meterId}",
            arguments = listOf(
                navArgument(name = "meterId"){
                    type= NavType.IntType
                }
            )
        )
        {backstackEntry ->
            ReadingScreen(navController, backstackEntry.arguments?.getInt("meterId"),
                MeterRepository(db), ReadingRepository(db)
            )
        }
    }
}

private object Route {
    const val MAIN = "main"
    const val METER_SCREEN = "meter"
    const val READING_SCREEN = "reading"
    const val CONFIG = "config"
}

sealed class Screen(val route: String) {
    object MAIN : Screen(Route.MAIN)
    object METERSCREEN : Screen(Route.METER_SCREEN)
    object READINGSCREEN : Screen(Route.READING_SCREEN)
    object CONFIG : Screen(Route.CONFIG)
}