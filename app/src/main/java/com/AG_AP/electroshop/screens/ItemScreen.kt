package com.AG_AP.electroshop.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import com.AG_AP.electroshop.components.DialogCustomBusinessPartner
import com.AG_AP.electroshop.firebase.models.ItemType
import com.AG_AP.electroshop.uiState.ItemUiState
import com.AG_AP.electroshop.viewModels.ItemViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleView(innerPadding: PaddingValues, viewModel: ItemViewModel, id: String?) {
    val dataUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(innerPadding)
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
                    value = dataUiState.itemName,
                    onValueChange = { viewModel.changeItemName(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Descripción de item") }
                )

                val coffeeDrinks =
                    ItemType.entries //FIXME arreglar el porque no sale el nombre completo
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = dataUiState.itemType.name,
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
                                text = { Text(text = item.name) },
                                onClick = {
                                    viewModel.changeItemType(item)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dataUiState.mainSupplier,
                    onValueChange = { viewModel.changeMainSupplier(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Proveedor principal") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.changeShowBusinessPartnerDialog(true)
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "+ Icon")
                        }
                    }
                )
                if (dataUiState.showBusinessPartnerDialog) {
                    DialogCustomBusinessPartner(onDismissRequest = {
                        viewModel.changeShowBusinessPartnerDialog(
                            false
                        )
                    }) {
                        if (it != null) {
                            viewModel.changeMainSupplier(it.CardCode)
                        }
                    }
                }
            }
            /* TODO lista de precios
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
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                DialogStartEnd.show()
                            }
                        ) {
                            Icon(Icons.Filled.AccessTime, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

             */

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "¿Controlar numeros de serie?"
                )
                Row(Modifier.selectableGroup()) {
                    RadioButton(
                        selected = dataUiState.manageSerialNumbers,
                        onClick = { viewModel.changeManageSerialNumbers(true) }
                    )
                    Text(
                        text = "Si",
                        modifier = Modifier
                            .padding(top = 13.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )

                    RadioButton(
                        selected = !dataUiState.manageSerialNumbers,
                        onClick = { viewModel.changeManageSerialNumbers(false) }
                    )
                    Text(
                        text = "No",
                        modifier = Modifier
                            .padding(top = 13.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "¿Autocrear numeros de serie?"
                )
                Row(Modifier.selectableGroup()) {
                    RadioButton(
                        selected = dataUiState.autoCreateSerialNumbersOnRelease,
                        onClick = { viewModel.changeAutoCreateSerialNumbersOnRelease(true) }
                    )
                    Text(
                        text = "Si",
                        modifier = Modifier
                            .padding(top = 13.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(top = 13.dp)
                    )

                    RadioButton(
                        selected = !dataUiState.autoCreateSerialNumbersOnRelease,
                        onClick = { viewModel.changeAutoCreateSerialNumbersOnRelease(false) }
                    )
                    Text(
                        text = "No",
                        modifier = Modifier
                            .padding(top = 13.dp)
                    )
                }
            }


            //TODO ARTICULO

        }
        PriceListList(dataUiState = dataUiState, viewModel = viewModel)
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

@Composable
fun PriceListList(dataUiState: ItemUiState, viewModel: ItemViewModel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 20.dp)
    ) {
        dataUiState.itemPrice?.let { price ->
            items(price) { individualPrice ->
                ListItem(
                    headlineContent = { Text(text = individualPrice.price.toString()) },
                    supportingContent = { Text(text = individualPrice.currency) },
                    trailingContent = {
                        Button(onClick = { viewModel.eraseIndividualPriceList(individualPrice) }) {
                            Text(
                                text = "Borrar"
                            )
                        }
                    },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                )
            }
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldItem(
    viewModel: ItemViewModel = viewModel(),
    navController: NavHostController,
    id: String? = null
) {
    if (!id.isNullOrEmpty()) {
        viewModel.changeItemCode(id)
        viewModel.refresh()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Gestión de articulos")
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
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            ArticleView(innerPadding, viewModel, id)
        }
    }
}