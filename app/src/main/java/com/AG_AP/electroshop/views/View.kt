package com.AG_AP.electroshop.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


class View {


    /**
     * Method that shows the first screen of login
     */
    @Composable
    fun MostrarPantallaPrincipal(modifier: Modifier = Modifier) {
        Row(
            modifier = Modifier.background(color = Color.Blue)
        ) {

        }

    }



    @Composable
    @Preview(
        showSystemUi = true
    )
    fun PantallaPrincipalPreview() {
        MostrarPantallaPrincipal(Modifier)
    }
}