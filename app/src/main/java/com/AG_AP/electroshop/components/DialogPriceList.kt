package com.AG_AP.electroshop.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.AG_AP.electroshop.firebase.models.Price
import com.AG_AP.electroshop.viewModels.DialogPLViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogCustomPriceList(
    onDismissRequest: () -> Unit,
    viewModel: DialogPLViewModel = viewModel(),
    callback: (Price?) -> Unit
) {
    val dataUiState by viewModel.uiState.collectAsState()
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .height(500.dp)
                .width(900.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                //Hacer un dropview
                val coffeeDrinks = dataUiState.AvailablePriceList

                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    dataUiState.ChoosenCurrency?.let {
                        TextField(
                            value = it,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.menuAnchor(),
                            label = { Text(text = "Moneda") }
                        )
                    }

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        coffeeDrinks.forEach { price ->
                            DropdownMenuItem(
                                text = {
                                    if (price != null) {
                                        Text(text = price.currency)
                                    }
                                },
                                onClick = {
                                    if (price != null) {
                                        viewModel.changeChoosenCurrency(price.currency)
                                        viewModel.changePriceList(price.priceList)
                                    }
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(5.dp)
                )

                TextField(
                    value = dataUiState.PriceWritten.toString(),
                    onValueChange = {
                        if (it.isNotEmpty()) {
                            var newValue = 0.0
                            try {
                                newValue = it.toDoubleOrNull() ?: 0.0
                            } catch (e: Exception) {
                                Log.e("Errores", e.stackTraceToString())
                            }
                            viewModel.changeWrittenPrice(newValue)
                        } else {
                            viewModel.changeWrittenPrice(0.0)
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    trailingIcon = { Text(text = "€") },
                    label = { Text(text = "Precio") }
                )


                Spacer(
                    modifier = Modifier
                        .padding(5.dp)
                )

                Button(onClick = {
                    viewModel.changePrice()
                    callback(dataUiState.ItemPrice)
                    onDismissRequest()
                }) {
                    Text(text = "Aceptar")
                }

            }
        }
    }
}

@Composable
fun PantallaPriceList() {
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
        if (showDialog) {
            DialogCustomPriceList(
                onDismissRequest = { showDialog = false }
            ) { it ->
                Log.e("Pruebas", it.toString())
            }
        }

    }


}