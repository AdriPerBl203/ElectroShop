package com.AG_AP.electroshop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.screens.ScaffoldActivity
import com.AG_AP.electroshop.ui.theme.ElectroShopTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //PriceListCRUD.insertPrecio(5, 3, "Joselito")
        /*
        PriceListCRUD.getPrecioById("6") { price ->
            Log.e("Pruebas", "Prueba precio ${price.toString()}")
        }
         */
        /*
        PriceListCRUD.getAllPrecios { price ->
            Log.e("Pruebas", "Prueba ${price.toString()}")
        }
         */
        // PriceListCRUD.updatePrecioById(6, Price(1, 33, "EUR"))
        //PriceListCRUD.deletePrecioById("uun5BNGcHfUOymScIJoY")

        /*
        ItemCRUD.getItemById("Pruebita") { item ->
            Log.e("Pruebas", "Prueba ${item.toString()}")
        }

         */

        //val precio = Price(1, 5, "JSON")
        //ItemCRUD.insertItem(Item("Ordenador HP - Json", ItemType.I, "Pedro", listOf(precio)))
        //ItemCRUD.updateItemById("OjwrKOFcSMSlfaxKrVSj", Item("Prueba prueba", ItemType.I, "Ricardo", listOf(precio)))
        //ItemCRUD.deleteItemById("OjwrKOFcSMSlfaxKrVSj")
        /*
        ItemCRUD.getAllItems {
            Log.e("Pruebas", "$it")
        }
         */

        /*
        ActivityCRUD.getAllActivity() {lista ->
            Log.e("lista","${lista}")
            Log.e("lista","${lista}")
        }
        */

        setContent {
            ElectroShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //AppNav()
                    ScaffoldActivity()
                }
            }
        }


    }
}
