package com.AG_AP.electroshop

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.AG_AP.electroshop.firebase.ActivityCRUD
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.SEIConfigCRUD
import com.AG_AP.electroshop.functions.ActivityObj
import com.AG_AP.electroshop.functions.Config
import com.AG_AP.electroshop.functions.ObjectContext
import com.AG_AP.electroshop.nav.AppNav
import com.AG_AP.electroshop.screens.PdfViewer


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlueSkyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context: Context = application.applicationContext
                    val activity: ComponentActivity = this
                    ObjectContext.context = context
                    ActivityObj.ObjectActivity = activity
                    //CODE DE PRUEBA PAR PDF: INV20015
                    Config.initConfig(context)
                    AppNav(context = context)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1234) {
            //Nuestros permisos
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permiso aceptado
            } else {
                //El permiso no ha sido aceptado
                Toast.makeText(this, "Permisos rechazados, por favor aceptelos", Toast.LENGTH_LONG).show()
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



