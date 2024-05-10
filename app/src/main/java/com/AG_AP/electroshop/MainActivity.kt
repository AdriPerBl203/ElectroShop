package com.AG_AP.electroshop

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ObjectContext
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
                    //
                    /*val documentLines = listOf(
                        DocumentLineFireBase("item1", 2.0, 0.1, 1, 50.0),
                        DocumentLineFireBase("item2", 1.0, 0.05, 2, 30.0)
                    )

                    // Crear una instancia de OrderFireBase
                    val order = OrderFireBase(
                        DocNum = 123,
                        CardCode = "C123",
                        CardName = "Cliente Ejemplo",
                        DocDate = "2024-05-07",
                        DocDueDate = "2024-05-10",
                        TaxDate = "2024-05-07",
                        DiscountPercent = 0.15,
                        DocumentLines = documentLines,
                        SAP = false
                    )
                    PurchaseOrderCRUD.insertForFireBase(order)
                    */

                    //Crear item de ejemplo:
                    /*val priceList = listOf(
                        Price(1, 10, "USD",true),
                        Price(2, 15, "EUR",true)
                    )
                    val item = Item(
                        idFireBase = "",
                        ItemCode = "",
                        itemName = "Example Item",
                        itemType = ItemType.I,
                        mainSupplier = "P00001",
                        itemPrice = priceList,
                        manageSerialNumbers = "Yes",
                        autoCreateSerialNumbersOnRelease = "No",
                        SAP = false
                    )
                    ItemCRUD.insertItemForFireBase(item)*/
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