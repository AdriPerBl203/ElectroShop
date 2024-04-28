package com.AG_AP.electroshop.viewModels

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.AG_AP.electroshop.screens.LoginFrontView
import com.AG_AP.electroshop.screens.MenuFrontView
import com.AG_AP.electroshop.screens.ScaffoldActivity
import com.AG_AP.electroshop.screens.ScaffoldBusinessPartner
import com.AG_AP.electroshop.screens.ScaffoldListActivity
import com.AG_AP.electroshop.screens.SettingScreen

/**
 * Method called at the beginning of the initialization
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

        composable(
            route = Routes.ScreenConfig.route
        ) {
            SettingScreen(navController = navController)
        }

        composable(
            route = Routes.ScreenMenu.route
        ) {
            MenuFrontView(navController = navController)
        }

        composable(
            route = Routes.ScreenActivity.route,
            //arguments = listOf(navArgument("ID"){/*type = NavType.IntType*/})
        ) {
            /*backStackEntry ->
            val id = backStackEntry.arguments?.getString("ID")
            if(id.isNullOrEmpty()){
                ScaffoldActivity(navController = navController)
            }else{
                ScaffoldActivity(navController = navController, id = id)
            }*/
            ScaffoldActivity(navController = navController)
        }

        composable(
            route = Routes.BusinessPartnerActivity.route
        ) {
            ScaffoldBusinessPartner(navController = navController)
        }

        composable(
            route = Routes.ListActivity.route
        ) {
            ScaffoldListActivity(navController = navController)
        }

    }
}