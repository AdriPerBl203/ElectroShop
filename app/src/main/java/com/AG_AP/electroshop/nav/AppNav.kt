package com.AG_AP.electroshop.nav

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.AG_AP.electroshop.screens.Activities.MixedScaffoldActivityScreen
import com.AG_AP.electroshop.screens.Activities.MixedScaffoldBusinessPartnerScreen
import com.AG_AP.electroshop.screens.Activities.MixedScaffoldItemScreen
import com.AG_AP.electroshop.screens.Activities.MixedScaffoldOrderScreen
import com.AG_AP.electroshop.screens.Activities.ScaffoldActivity
import com.AG_AP.electroshop.screens.Activities.ScaffoldActivityUltimate
import com.AG_AP.electroshop.screens.Activities.ScaffoldListActivity
import com.AG_AP.electroshop.screens.BusinessPartners.ScaffoldBusinessPartner
import com.AG_AP.electroshop.screens.BusinessPartners.ScaffoldBusinessPartnerUltimate
import com.AG_AP.electroshop.screens.LoginFrontView
import com.AG_AP.electroshop.screens.MenuFrontView
import com.AG_AP.electroshop.screens.BusinessPartners.ScaffoldListBusinessPartner
import com.AG_AP.electroshop.screens.Items.ScaffoldItem
import com.AG_AP.electroshop.screens.Items.ScaffoldListItems
import com.AG_AP.electroshop.screens.Orders.ScaffoldListOrder
import com.AG_AP.electroshop.screens.Orders.ScaffoldOrder
import com.AG_AP.electroshop.screens.SettingScreen

/**
 * Method called at the beginning of the initialization
 */
@Composable
fun AppNav(navController: NavHostController = rememberNavController(), context: Context) {
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
            SettingScreen(navController = navController, context = context)
        }

        composable(
            route = Routes.ScreenMenu.route
        ) {
            MenuFrontView(navController = navController)
        }

        /* Mixed Screens */
        composable(
            route = Routes.MixedActivityScreen.route
        ) {
            MixedScaffoldActivityScreen(navController = navController)
        }

        composable(
            route = Routes.MixedBusinessPartnerScreen.route
        ) {
            MixedScaffoldBusinessPartnerScreen(navController = navController)
        }

        composable(
            route = Routes.MixedOrderScreen.route
        ) {
            MixedScaffoldOrderScreen(navController = navController)
        }

        composable(
            route = Routes.MixedItemScreen.route
        ) {
            MixedScaffoldItemScreen(navController = navController)
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
            route = Routes.ScreenOrderList.route
        ) {
            ScaffoldListOrder(navController = navController)
        }

        composable(
            route = Routes.ScreenOrderAux.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ScaffoldOrder(navController = navController, id = id)
        }


        composable(
            route = Routes.BusinessPartnerAux.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ScaffoldBusinessPartner(navController = navController, id = id)
        }

        composable(
            route = Routes.ItemScreen.route
        ) {
            ScaffoldItem(navController = navController)
        }

        composable(
            route = Routes.ItemScreenList.route
        ) {
            ScaffoldListItems(navController = navController)
        }

        composable(
            route = Routes.ItemScreenAux.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ScaffoldItem(navController = navController, id = id)
        }

        composable(
            route = Routes.BusinessPartnerUltimate.route
        ) {
            ScaffoldBusinessPartnerUltimate(navController)
        }

        composable(
            route = Routes.ActivityUltimate.route
        ) {
            ScaffoldActivityUltimate(navController =navController)
        }
    }
}