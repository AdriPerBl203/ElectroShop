package com.AG_AP.electroshop.viewModels

import android.content.Context
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
import com.AG_AP.electroshop.screens.ScaffoldListBusinessPartner
import com.AG_AP.electroshop.screens.ScaffoldOrder
import com.AG_AP.electroshop.screens.ScaffoldPurchaseOrder
import com.AG_AP.electroshop.screens.SettingScreen

/**
 * Method called at the beginning of the initialization
 */
@Composable
fun AppNav(navController: NavHostController = rememberNavController(),context: Context) {
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
            SettingScreen(navController = navController,context=context)
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
            route = Routes.ScreenActivityAux.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ScaffoldActivity(navController = navController, id = id)
        }

        composable(
            route = Routes.BusinessPartner.route
        ) {
            ScaffoldBusinessPartner(navController = navController)
        }

        composable(
            route = Routes.ListActivity.route
        ) {
            ScaffoldListActivity(navController = navController)
        }

        composable(
            route = Routes.ScreenBusinessPartnerList.route
        ) {
            ScaffoldListBusinessPartner(navController = navController)
        }
        composable(
            route = Routes.ScreenOrder.route
        ) {
            ScaffoldOrder(navController = navController)
        }

        composable(
            route = Routes.BusinessPartnerAux.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ScaffoldBusinessPartner(navController = navController, id = id)
        }
        composable(
            route = Routes.PurchaseOrderScreen.route
        ) {
            ScaffoldPurchaseOrder(navController = navController)
        }
    }
}