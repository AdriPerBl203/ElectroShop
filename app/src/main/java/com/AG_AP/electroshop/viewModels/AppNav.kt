package com.AG_AP.electroshop.viewModels

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.AG_AP.electroshop.screens.LoginFrontView
import com.AG_AP.electroshop.screens.SettingScreen

/**
 * Method called at the begining of the initialization
 */
@Composable
fun AppNav(navController: NavHostController = rememberNavController()) {
    /* Starts with Login Screen */
    NavHost(
        navController = navController,
        startDestination = Routes.ScreenLogin.route
    ) {
        /* Login Screen */
        composable(
            route = Routes.ScreenLogin.route
        ) {
           LoginFrontView(navController = navController)
        }


        /* Menu Screen */
        composable(
            route = Routes.ScreenConfig.route
        ) {
            SettingScreen(/*navController = navController*/)
        }

        /* Menu Screen */
    }
}