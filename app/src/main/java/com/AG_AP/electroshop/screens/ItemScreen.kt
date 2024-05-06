package com.AG_AP.electroshop.screens
/*

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.viewModels.ItemViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleView(innerPadding: PaddingValues, viewModel: ItemViewModel, id: String?) {
    val dataUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            /*modifier= Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalArrangement= Arrangement.Center*/
        ) {
            Column {
                OutlinedTextField(
                    value = dataUiState.ItemCode,
                    onValueChange = { viewModel.changeItemCode(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Codigo de item") },
                    readOnly = true
                )
                OutlinedTextField(
                    value = dataUiState.ItemDescription,
                    onValueChange = { viewModel.changeItemName(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Descripción de item") }
                )
                OutlinedTextField(
                    value = dataUiState.Quantity,
                    onValueChange = { viewModel.changeQuantity(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Cantidad") },

                )

                OutlinedTextField(
                    value = dataUiState.U_SEIPEDIDOCLIENTE,
                    onValueChange = { viewModel.changePedidoCliente(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Asociar pedido de cliente") },
                    readOnly = true,
                    trailingIcon={
                        IconButton(
                            onClick = {
                                viewModel.showDialogOrder()
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )
            }

            Column {
                val priority = arrayOf("Bajo", "Normal", "Alto")
                var expandedTwo by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = dataUiState.EndTime,
                    onValueChange = { viewModel.changeEndTime(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora fin") },
                    readOnly = true,
                    trailingIcon={
                        IconButton(
                            onClick = {
                                DialogStartEnd.show()
                            }
                        ) {
                            Icon(Icons.Filled.AccessTime, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )
                OutlinedTextField(
                    value = dataUiState.CardCode,
                    onValueChange = { viewModel.changeCardCode(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Código cliente") }
                )
                OutlinedTextField(
                    value = dataUiState.Tel,
                    onValueChange = { viewModel.changeTel(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Teléfono") }
                )
                ExposedDropdownMenuBox(
                    expanded = expandedTwo,
                    onExpandedChange = {
                        expandedTwo = !expandedTwo
                    }
                ) {
                    TextField(
                        value = dataUiState.Priority,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTwo) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedTwo,
                        onDismissRequest = { expandedTwo = false }
                    ) {
                        priority.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    viewModel.changePriority(item)
                                    expandedTwo = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dataUiState.U_SEIPEDIDOCOMPRAS,
                    onValueChange = { viewModel.changePedidoCompra(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Asociar pedido de compra") },
                    readOnly = true,
                    trailingIcon={
                        IconButton(
                            onClick = {
                                viewModel.showDialogPurchaseOrder()
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

            }
            Column {
                OutlinedTextField(
                    value = dataUiState.ClgCode,
                    onValueChange = { viewModel.changeClgCode(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Id") }
                )
            }
        }
        Column {
            if (dataUiState.message) {
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
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldArticle(
    viewModel: ItemViewModel = viewModel(),
    navController: NavHostController,
    id: String? = null
) {
    if (!id.isNullOrEmpty()) {
        viewModel.changeItemCode(id)
        viewModel.refreshScreen()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Gestión de actividad")
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
                    onClick = { navController.navigateUp() }
                ) {
                    Text(text = "Volver")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.find() }) {
                Icon(Icons.Default.Add, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            ArticleView(innerPadding, viewModel, id)
        }
    }
}

 */