package com.AG_AP.electroshop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.PriceListCRUD
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.ui.theme.ElectroShopTheme
import com.AG_AP.electroshop.viewModels.AppNav
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        PriceListCRUD.insertPrecio(6, 2, "ARON")
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

        val precio = Price(1, 5, "JSON")
        ItemCRUD.insertItem(Item("Ordenador HP - Json", ItemType.I, "Pedro", listOf(precio)))
        //ItemCRUD.updateItemById("OjwrKOFcSMSlfaxKrVSj", Item("Prueba prueba", ItemType.I, "Ricardo", listOf(precio)))
        //ItemCRUD.deleteItemById("OjwrKOFcSMSlfaxKrVSj")



        setContent {
            ElectroShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNav()
                }
            }
        }


    }
}
