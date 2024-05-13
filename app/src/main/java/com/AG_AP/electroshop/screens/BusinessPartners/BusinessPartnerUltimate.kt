package com.AG_AP.electroshop.screens.BusinessPartners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessPartnerUltimate(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
        ) {
            Column {

                val coffeeDrinks = arrayOf("Cliente", "Proveedor", "Lead")
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
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp)
                            .padding(8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        coffeeDrinks.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    /*TODO*/
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nombre") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Teléfono móvil") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Email") }
                )
            }
            Row(
                modifier = Modifier.width(500.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Buscar") }
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text("Buscar")
                    }

                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text("Opciones avanzadas")
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val listaUno = listOf(
                    "ejemplo 1",
                    "ejemplo 2",
                    "ejemplo 3",
                    "ejemplo 4",
                    "ejemplo 5",
                    "ejemplo 6"
                )
                val listaDos = listOf(
                    "ejemplo 7",
                    "ejemplo 8",
                    "ejemplo 9",
                    "ejemplo 10",
                    "ejemplo 11",
                    "ejemplo 12"
                )
                Text("Clientes en SAP")
                LazyRowWithCards(listaUno)
                Text("Clientes en la tablet")
                LazyRowWithCards(listaDos)
            }
        }
        /*Column {
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
        }*/
    }
}

@Composable
fun LazyRowWithCards(data: List<String>) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
    ) {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .width(200.dp)
                    .height(150.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp)
                        )
                        IconButton(onClick = {
                            //TODO
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Settings",
                                tint = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBusinessPartnerUltimate(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Gestión de clientes")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                val coffeeDrinks = arrayOf(
                    "Añadir y ver",
                    "Añadir y nuevo",
                    "Añadir y salir",
                    "Actualizar",
                    "Borrar"
                )
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
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp)
                            .padding(8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        coffeeDrinks.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    /*TODO*/
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Acción")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "Volver")
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            BusinessPartnerUltimate(innerPadding)
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_TABLET,
    showBackground = true
)
fun BusinessPartnerUltimatePreview() {
    //ScaffoldBusinessPartnerUltimate(navController)
}