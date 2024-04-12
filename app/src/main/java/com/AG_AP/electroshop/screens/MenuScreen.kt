package com.AG_AP.electroshop.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

/**
 * Method that contains the view of the Screen Menu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuFrontView(modifier: Modifier = Modifier /*TODO rellenar con el nombre de usuario accediendo al uiState */) {
    /* Top border */
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Prueba"
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Cyan
                )
            )
        }
    ) {
        /* Principal content */

            innerPadding ->
        MenuBody(
            innerPadding = innerPadding
        )

    }

}


/**
 * Method that contains most of the important usages of the App
 */
@Composable
fun MenuBody(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier.padding(innerPadding)
    ) {

    }
}


// TODO funcion que muestre la cantidad de acciones disponibles con un array

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun MenuFrontViewPreview() {
    MenuFrontView()
}