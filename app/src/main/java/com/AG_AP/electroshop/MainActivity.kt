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
import androidx.lifecycle.lifecycleScope
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.components.Pantalla
import com.AG_AP.electroshop.endpoints.models.orders.post.DocumentLine
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import com.AG_AP.electroshop.endpoints.objects.ActivityObj
import com.AG_AP.electroshop.endpoints.objects.OrdersObj
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.screens.ScaffoldActivity
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.PriceListCRUD
import com.AG_AP.electroshop.firebase.PurchaseOrderCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.firebase.models.SEIConfig
import com.AG_AP.electroshop.screens.SettingScreen
import com.AG_AP.electroshop.ui.theme.ElectroShopTheme
import com.AG_AP.electroshop.viewModels.AppNav
import kotlinx.coroutines.launch


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