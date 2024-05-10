package com.AG_AP.electroshop.screens.PurchaseOrders

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.DatePicker
import com.AG_AP.electroshop.uiState.PurchaseOrders.PurchaseOrderUiState
import com.AG_AP.electroshop.viewModels.PurchaseOrders.PurchaseOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseOrderView(
    innerPadding: PaddingValues,
    viewModel: PurchaseOrderViewModel,
    id: String?
) {
    val dataUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
        //.verticalScroll(rememberScrollState())
    ) {
        Row {
            Column {
                val coffeeDrinks =
                    arrayOf("Llamada telefónica", "Reunión", "Tarea", "Nota", "Campaña", "Otros")
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        coffeeDrinks.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    //viewModel.changeAction(item)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dataUiState.CardName,
                    onValueChange = { viewModel.changeName(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nombre") }
                )

                OutlinedTextField(
                    value = dataUiState.DiscountPercent.toString(),
                    onValueChange = { viewModel.changeDiscount(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    suffix = { Text(text = "%") },
                    label = { Text("Descuento %") }
                )


            }

            Column {
                DatePicker("Fecha contabilizacion ", dataUiState.TaxDate) { fechaDocumento ->
                    viewModel.changeTaxDate(fechaDocumento)
                }

                DatePicker("Fecha entrega ", dataUiState.DocDueDate) { fechaDocumento ->
                    viewModel.changeDocDueDate(fechaDocumento)
                }
                DatePicker("Fecha documento ", dataUiState.DocDate) { fechaDocumento ->
                    viewModel.changeDocDate(fechaDocumento)
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, end = 30.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, end = 30.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (id != null && id.toInt() != -1) {
                    Row {
                        IconButton(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                                .border(
                                    BorderStroke(0.5.dp, Color.Black)
                                )
                                .padding(end = 0.5.dp),
                            onClick = { viewModel.deleteLine() }
                        ) {
                            Text(text = "-")
                        }
                        IconButton(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primary)
                                .border(
                                    BorderStroke(0.5.dp, Color.Black)
                                )
                                .padding(start = 0.5.dp),
                            onClick = { viewModel.addLine() }
                        ) {
                            Text(text = "+")
                        }
                    }
                }
            }
            TableDocumentLinePurchase(dataUiState, viewModel)

        }
        Column {
            /*if (dataUiState.message) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        Button(
                            onClick = {
                                viewModel.menssageFunFalse()
                            }
                        ) {
                            Text("Cerrar")
                        }
                    },
                    content = {
                        Text(dataUiState.text)
                    }
                )
            }*/
        }
    }
}


@Composable
fun TableDocumentLinePurchase(
    dataUiState: PurchaseOrderUiState,
    viewModel: PurchaseOrderViewModel
) {

    val numCols = 6

    // Datos de ejemplo para las cabeceras
    val headers = listOf(
        "Nº",
        "Código Articulo",
        "Descripción articulo",
        "Cantidad",
        "Precio",
        "% de descuento"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(numCols)
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


    LazyVerticalGrid(columns = GridCells.Fixed(numCols)) {
        dataUiState.DocumentLineList.forEach { index, value ->
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        index.toString(),
                        Modifier
                            .height(50.dp)
                            .wrapContentSize()
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[1].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[2].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[3].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[4].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp)
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[5].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldPurchaseOrder(
    viewModel: PurchaseOrderViewModel = viewModel(),
    navController: NavHostController,
    id: String? = null
) {
    if (id != null) {
        viewModel.changeDocNum(id.toInt())
        viewModel.refresh()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Pedido de compra")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.guardar(false) }
                ) {
                    Text(text = "Añadir y nuevo")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.guardar(true) }
                ) {
                    Text(text = "Añadir y ver")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.update() }
                ) {
                    Text(text = "Actualizar")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.borrar() }
                ) {
                    Text(text = "Borrar")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "Volver")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*viewModel.find()*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            PurchaseOrderView(innerPadding, viewModel, id)
        }
    }
}
