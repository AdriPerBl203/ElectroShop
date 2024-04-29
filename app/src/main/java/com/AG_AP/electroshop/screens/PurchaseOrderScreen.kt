package com.AG_AP.electroshop.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.viewModels.ActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchaseOrderView(innerPadding: PaddingValues/*, viewModel: ActivityViewModel, id: String?*/) {
    /*val dataUiState by viewModel.uiState.collectAsState()
    if(!id.isNullOrEmpty()){
        viewModel.changeClgCode(id)
    }*/
    Column(
        modifier= Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            /*modifier= Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalArrangement= Arrangement.Center*/
        ){
            Column(
            ) {
                val coffeeDrinks = arrayOf("Llamada telefónica", "Reunión", "Tarea", "Nota", "Campaña","Otros")
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
                    value = "",
                    onValueChange = { /*viewModel.changenota(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nota") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*viewModel.changeActivityDate(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Fecha") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*viewModel.changeActivityTime(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora inicio") }
                )
            }

            Column(
            ) {
                val coffeeDrinks = arrayOf("Llamada telefónica", "Reunión", "Tarea", "Nota", "Campaña","Otros")
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
                    value = "",
                    onValueChange = { /*viewModel.changenota(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nota") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*viewModel.changeActivityDate(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Fecha") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*viewModel.changeActivityTime(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora inicio") }
                )
            }
            Column(
            ) {
                val coffeeDrinks = arrayOf("Llamada telefónica", "Reunión", "Tarea", "Nota", "Campaña","Otros")
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
                    value = "",
                    onValueChange = { /*viewModel.changenota(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nota") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*viewModel.changeActivityDate(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Fecha") }
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = { /*viewModel.changeActivityTime(it)*/ },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora inicio") }
                )
            }
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldPurchaseOrder(/*viewModel: ActivityViewModel = viewModel(), navController: NavHostController, id:String? =null*/) {

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
                    modifier= Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { /*viewModel.guardar(false)*/ }
                ) {
                    Text(text = "Añadir y nuevo")
                }
                Button(
                    modifier= Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { /*viewModel.guardar(true)*/ }
                ) {
                    Text(text = "Añadir y ver")
                }
                Button(
                    modifier= Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { /*viewModel.update()*/ }
                ) {
                    Text(text = "Actualizar")
                }
                Button(
                    modifier= Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { /*viewModel.borrar()*/ }
                ) {
                    Text(text = "Borrar")
                }
                Button(
                    modifier= Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { /*navController.navigateUp()*/ }
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
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)){
            PurchaseOrderView(innerPadding/*,viewModel,id*/)
        }
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun PurchaseOrderPreview() {
    ScaffoldPurchaseOrder()
}