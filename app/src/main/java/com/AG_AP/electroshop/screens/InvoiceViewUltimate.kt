package com.AG_AP.electroshop.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.TopBarButton
import com.AG_AP.electroshop.nav.Routes
import com.AG_AP.electroshop.viewModels.InvoiceViewModel


@Composable
fun InvoiceUltimate(innerPadding: PaddingValues, viewModel: InvoiceViewModel) {
    val dataUiState by viewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        //Left column
        Column {
            OutlinedTextField(
                value = "dataUiState.CardName",
                onValueChange = { /*TODO viewModel.changeSearch(it)*/ },
                modifier = Modifier
                    .width(300.dp)
                    .padding(8.dp),
                label = { Text("Búsqueda") }
            )
            Spacer(
                modifier = Modifier.padding(5.dp)
            )
            LazyColumn {
                items(dataUiState.BusinessPartnerWithInvoiceList) { item ->
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
                            Column {
                                if (item != null) {
                                    Text(
                                        text = item.CardCode,
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                    Text(
                                        text = item.CardCode,
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                } else {
                                    Text(
                                        text = "",
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                }
                                IconButton(onClick = {
                                    viewModel.replaceData(item)
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

        //Right column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (dataUiState.ActualPdf == null) {
                Text(
                    text = "No hay ningún pdf cargado"
                )
            } else {
                //TODO cargar pdf
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldInvoiceUltimate(
    navController: NavHostController,
    viewModel: InvoiceViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TopBarButton(
                            "Actividades",
                            { navController.navigate(route = Routes.ActivityUltimate.route) },
                            Icons.Default.LocalActivity
                        )
                        TopBarButton(
                            "Clientes",
                            { navController.navigate(route = Routes.BusinessPartnerUltimate.route) },
                            Icons.Default.AccountBox
                        )
                        TopBarButton(
                            "Articulos",
                            { navController.navigate(route = Routes.ItemScreen.route) },
                            Icons.Default.Inbox
                        )
                        TopBarButton(
                            "Pedidos",
                            { navController.navigate(route = Routes.ScreenOrder.route) },
                            Icons.Default.AddCard
                        )
                    }
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
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            InvoiceUltimate(innerPadding, viewModel)
        }
    }
}