package com.AG_AP.electroshop.screens.Activities

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.DatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityUltimate(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
        ) {
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
                    label = { Text("Nota") }
                )
                DatePicker(
                    label = "Fecha", "", modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp)
                ) {
                    /*TODO*/
                }
                OutlinedTextField(
                    value =  "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora inicio") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                /*TODO*/
                            }
                        ) {
                            Icon(Icons.Filled.AccessTime, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

                OutlinedTextField(
                    value =  "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Asociar pedido de cliente") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                /*TODO*/
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Id") },
                    enabled = false
                )
            }

            Column {
                val priority = arrayOf("Bajo", "Normal", "Alto")
                var expandedTwo by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value =  "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora fin") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                /*TODO*/
                            }
                        ) {
                            Icon(Icons.Filled.AccessTime, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )
                OutlinedTextField(
                    value =  "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Código cliente") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                /*TODO*/
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
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
                        value = "",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTwo) },
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp)
                            .padding(8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = expandedTwo,
                        onDismissRequest = { expandedTwo = false }
                    ) {
                        priority.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    //TODO
                                    expandedTwo = false
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
                    label = { Text("Asociar pedido de compra") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                /*TODO*/
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

            }
            Column(
                modifier= Modifier.width(250.dp)
            ){
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Buscar") }
                )

                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text("Buscar")
                    }

                    ElevatedButton(onClick = { /*TODO*/ }) {
                        Text("Opciones avanzadas")
                    }
            }

            Column(
                modifier= Modifier.fillMaxHeight(),
                horizontalAlignment= Alignment.CenterHorizontally,
                verticalArrangement= Arrangement.Center
            ){
                val listaUno = listOf("ejemplo 1","ejemplo 2","ejemplo 3","ejemplo 4","ejemplo 5","ejemplo 6")
                val listaDos = listOf("ejemplo 7","ejemplo 8","ejemplo 9","ejemplo 10","ejemplo 11","ejemplo 12")
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
    LazyRow (
        modifier= Modifier.padding(horizontal=10.dp,vertical=15.dp)
    ){
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .width(200.dp)
                    .height(150.dp)
            ) {
                Column(
                    horizontalAlignment= Alignment.Start,
                    verticalArrangement= Arrangement.SpaceBetween
                ){
                    Column(){
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp)
                        )
                        IconButton(onClick = {
                            //TODO
                        }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Settings", tint = MaterialTheme.colorScheme.primaryContainer)
                        }
                    }

                    Row(){

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldActivityUltimate(navController: NavHostController) {
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Add, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            ActivityUltimate(innerPadding)
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
    //ScaffoldActivityUltimate(navController)
}