package com.AG_AP.electroshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.AG_AP.electroshop.screens.ScaffoldPurchaseOrder
import com.AG_AP.electroshop.viewModels.AppNav


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueSkyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNav()
                    //ScaffoldPurchaseOrder()
                }
            }
        }


    }
}

// Define los colores de tu tema
private val azureBlue = Color(0xFF50A7FF)
private val skyBlue = Color(0xFF87CEEB)
private val deepBlue = Color(0xFF2E2EFD)
private val cyanBlue = Color(0xFF6CBEF5)
private val boneWhite = Color(0xFFF8EFFA)

// Define el tema personalizado "BlueSkyTheme"
@Composable
fun BlueSkyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(
            primary = azureBlue,
            primaryContainer = skyBlue,
            secondaryContainer = cyanBlue,
            secondary = deepBlue,
            background = boneWhite
        ),
        content = content
    )
}