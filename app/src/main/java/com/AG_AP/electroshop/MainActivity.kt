package com.AG_AP.electroshop

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.screens.ScaffoldActivity
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.PriceListCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.screens.SettingScreen
import com.AG_AP.electroshop.ui.theme.ElectroShopTheme
import com.AG_AP.electroshop.viewModels.AppNav


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

        //SEIConfigCRUD.insertSEIConfig(SEIConfig(1, 3, "JSF", "usuario", "N", "N", "N", "N"))

        /*
        SEIConfigCRUD.getAllSEIConfig() { lista ->
            Log.e("Pruebas", "$lista")
        }
         */

        //SEIConfigCRUD.updateSEIConfigById("JSF", SEIConfig(1, 3, "AGA", "usuario", "N", "N", "N", "N"))
        //SEIConfigCRUD.deleteSEIConfigById("JSF")

        /*
        BusinessPartnerCRUD.getAllObject { lista ->
            Log.e("Pruebas", "$lista")
        }

         */

        setContent {
            BlueSkyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context: Context = application.applicationContext
                    AppNav(context=context)
                }
            }
        }


    }
}

// Define los colores de tu tema
private val azureBlue = Color(0xFF50A7FF)
private val skyBlue = Color(0xFF87CEEB)
private val deepBlue = Color(0xFF2E2EFD)
private val boneWhite = Color(0xFFF8EFFA)

// Define el tema personalizado "BlueSkyTheme"
@Composable
fun BlueSkyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = azureBlue,
            primaryContainer = skyBlue,
            secondary = deepBlue,
            background = boneWhite
        ),
        content = content
    )
}