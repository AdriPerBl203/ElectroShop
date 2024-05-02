package com.AG_AP.electroshop.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.AG_AP.electroshop.firebase.models.BusinessPartner


@Composable
fun DialogCustom(
    businessPartner: BusinessPartner?,
    onDismissRequest: () -> Unit,
    callback: (Any?) -> Unit
) {
    val numCols = 3
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .width(700.dp)
        ) {

            //TODO controlar tipo
            // Datos de ejemplo para las cabeceras
            val headers = listOf("#", "Codigo interlocutor", "Nombre")

            LazyVerticalGrid(
                columns = GridCells.Fixed(numCols),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                items(items = headers) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colorScheme.primary)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            it,
                            Modifier
                                .height(50.dp)
                                .wrapContentSize()
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick = { onDismissRequest() }) {
                Text(text = "crerra")
            }
            Button(onClick = { onDismissRequest() }) {
                Text(text = "Abrir")
            }
        }
    }

}

@Composable
fun Pantalla() {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)

    ) {
        Button(
            onClick = { showDialog = true }
        ) {
            Text(text = "Mostrar diálogo")
        }
        if (showDialog == true) {
            DialogCustom(
                businessPartner = null,
                onDismissRequest = { showDialog = false }
            ) { it ->
                Log.e("Errores", it.toString())
            }
        }

    }


}

@Preview(
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun DialogPreview() {

    Pantalla()
}
