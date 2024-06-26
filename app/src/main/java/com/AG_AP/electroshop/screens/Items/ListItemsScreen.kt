package com.AG_AP.electroshop.screens.Items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.uiState.Items.ListItemsUiState
import com.AG_AP.electroshop.viewModels.Items.ListItemsViewModel
import com.AG_AP.electroshop.nav.Routes

@Composable
fun ListItemsView(
    innerPadding: PaddingValues,
    viewModel: ListItemsViewModel,
    navController: NavHostController
) {
    val dataUiState by viewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .background(MaterialTheme.colorScheme.background)

        ) {
            //FilterBusinessPartner(viewModel, dataUiState)
        }
        Column(
            modifier = Modifier
                .width(400.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ListItemsColumn(viewModel, dataUiState, navController)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldListItems(
    viewModel: ListItemsViewModel = viewModel(),
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Listado de artículos")
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
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "Volver")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* tODO */ }) {
                Icon(Icons.Default.Search, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ListItemsView(innerPadding, viewModel, navController)
        }
    }
}

@Composable
fun ListItemsColumn(
    viewModel: ListItemsViewModel,
    dataUiState: ListItemsUiState,
    navController: NavHostController
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Listado de interlocutores"
    )
    Spacer(modifier = Modifier.height(40.dp))
    LazyColumnItems(dataUiState.ListItems, navController)

}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBusinessPartner(viewModel: ListBusinessPartnerViewModel, dataUiState: ListBusinessPartnerUiState) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Buscador"
    )
    Spacer(modifier = Modifier.height(40.dp))

    OutlinedTextField(
        modifier = Modifier
            .width(220.dp)
            .padding(start = 50.dp, bottom = 25.dp),
        value = dataUiState.DateInit,
        onValueChange = { viewModel.changeDateInit(it) },
        label = { Text("Fecha inicio") }
    )

    OutlinedTextField(
        modifier = Modifier
            .width(220.dp)
            .padding(start = 50.dp, bottom = 25.dp),
        value = dataUiState.DateEnd,
        onValueChange = { viewModel.changeDateEnd(it) },
        label = { Text("Fecha Fin") }
    )

    OutlinedTextField(
        modifier = Modifier
            .width(220.dp)
            .padding(start = 50.dp, bottom = 25.dp),
        value = dataUiState.Client,
        onValueChange = { viewModel.changeClient(it) },
        label = { Text("Cliente") }
    )
    val coffeeDrinks = arrayOf("Sin documento", "Pedido cliente", "Pedido compra")
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = Modifier
            .width(220.dp)
            .padding(start = 50.dp, bottom = 25.dp),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = dataUiState.Document,
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
                        viewModel.changeDocument(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

 */

@Composable
fun LazyColumnItems(data: List<Item?>, navController: NavHostController) {
    LazyColumn {
        items(data) { item ->
            // Aquí defines cómo se muestra cada elemento de la lista
            ElevatedCardItems(item, navController)
        }
    }
}

@Composable
fun ElevatedCardItems(x: Item?, navController: NavHostController) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp)
            .padding(top = 15.dp, bottom = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (x != null) {
                Column {
                    Text(
                        text = x.ItemCode,
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                }
                Button(onClick = { navController.navigate(route = Routes.ItemScreenAux.route + "/${x.ItemCode}") }) {
                    Text(text = "Ver")
                }
            }
        }
    }
}