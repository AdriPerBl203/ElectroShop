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
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ObjectContext
import com.AG_AP.electroshop.nav.AppNav


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //ActivityCRUD.deleteAll()
            //ItemCRUD.deleteAll()
            //OrderCRUD.deleteAll()

            /*SEIConfigCRUD.getAllSEIConfig { it ->
                it?.forEach { bp ->
                    Log.i("Pruebas", "${bp.toString()} ")
                }
            }*/

            //BusinessPartnerCRUD.deleteAll()
            /*BusinessPartnerCRUD.getAllObject {
                it?.forEach { bp ->
                    Log.i("Pruebas", "${bp.toString()} ")
                }
            }

            ActivityCRUD.getAllActivity {
                it.forEach { act ->
                    Log.i("Pruebas", "${act.ClgCode} + ${act.toString()} ")
                }
            }*/

            BlueSkyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context: Context = application.applicationContext
                    ObjectContext.context = context
                    Config.initConfig(context)
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