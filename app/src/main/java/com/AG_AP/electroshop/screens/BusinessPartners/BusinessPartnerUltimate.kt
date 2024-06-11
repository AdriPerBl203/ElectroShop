package com.AG_AP.electroshop.screens.BusinessPartners

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.TopBarButton
import com.AG_AP.electroshop.realm.models.BusinessPartner
import com.AG_AP.electroshop.nav.Routes
import com.AG_AP.electroshop.viewModels.BusinessPartners.BusinessPartnerViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessPartnerUltimate(innerPadding: PaddingValues, viewModel: BusinessPartnerViewModel) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        val dataUiState by viewModel.uiState.collectAsState()


        if (dataUiState.FilterByName == "") {
            viewModel.refresh()
        }

        if (dataUiState.showDialogFilter) {
            Dialog(onDismissRequest = { viewModel.closeDialogfilterItem() }) {
                // Draw a rectangle shape with rounded corners inside the dialog
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(375.dp)
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        /*verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,*/
                    ) {
                        for (item in dataUiState.ItemBPList) {
                            ListItem(
                                headlineContent = { Text(text = item) },
                                trailingContent = {
                                    IconButton(onClick = {/*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Filled.Camera,
                                            contentDescription = "Settings",
                                            tint = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    }
                                }
                            )

                        }
                    }
                }
            }
        }

        BoxWithConstraints {
            if (maxWidth > 800.dp) {
                Row(
                ) {
                    Column {//

                        val coffeeDrinks = arrayOf("Cliente", "Proveedor", "Lead")
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            }
                        ) {
                            TextField(
                                value = dataUiState.CardType,
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
                                            viewModel.changeCardType(item)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = dataUiState.CardName,
                            onValueChange = { viewModel.changeCardName(it) },
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp),
                            label = { Text("Nombre") }
                        )
                        OutlinedTextField(
                            value = dataUiState.Cellular,
                            onValueChange = { viewModel.changeCellular(it) },
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp),
                            label = { Text("Teléfono móvil") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = dataUiState.EmailAddress,
                            onValueChange = { viewModel.changeEmailAddress(it) },
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp),
                            label = { Text("Email") }
                        )
                    }  //

                    Row(
                        modifier = Modifier.width(500.dp)
                    ) {
                        OutlinedTextField(//
                            value = dataUiState.FilterByName,
                            onValueChange = { viewModel.changeFilter(it) },
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp),
                            label = { Text("Buscar por nombre") }
                        ) //

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            ElevatedButton(onClick = { viewModel.refresh() }) {
                                Text("Buscar")
                            }

                            ElevatedButton(onClick = { /*TODO*/ }) {
                                Text("Opciones avanzadas")
                            }

                            ElevatedButton(onClick = { viewModel.showDialogfilterItem() }) {
                                Text("Últimos articulos")
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val listaUno = dataUiState.BPSapList
                        val listaDos = dataUiState.BPDeviceList
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Clientes en SAP")
                                LazyColumnWithCards(listaUno, viewModel, true)
                            }
                        }
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Clientes en la tablet")
                                LazyColumnWithCards(listaDos, viewModel, true)
                            }
                        }
                    }
                }
            } else {
                Column {
                    Column(
                        modifier = Modifier.verticalScroll(state = rememberScrollState())
                    ) {
                        Column {//

                            val coffeeDrinks = arrayOf("Cliente", "Proveedor", "Lead")
                            var expanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                }
                            ) {
                                TextField(
                                    value = dataUiState.CardType,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expanded
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .width(250.dp)
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
                                                viewModel.changeCardType(item)
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = dataUiState.CardName,
                                onValueChange = { viewModel.changeCardName(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Nombre") }
                            )
                            OutlinedTextField(
                                value = dataUiState.Cellular,
                                onValueChange = { viewModel.changeCellular(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Teléfono móvil") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )

                            OutlinedTextField(
                                value = dataUiState.EmailAddress,
                                onValueChange = { viewModel.changeEmailAddress(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Email") }
                            )
                        }  //

                        Row(
                            modifier = Modifier.width(300.dp)
                        ) {
                            OutlinedTextField(//
                                value = dataUiState.FilterByName,
                                onValueChange = { viewModel.changeFilter(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Buscar por nombre") }
                            ) //

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                ElevatedButton(onClick = { viewModel.refresh() }) {
                                    Text("Buscar")
                                }

                                ElevatedButton(onClick = { /*TODO*/ }) {
                                    Text("Opciones avanzadas")
                                }

                                ElevatedButton(onClick = { viewModel.showDialogfilterItem() }) {
                                    Text("Últimos articulos")
                                }
                            }
                        }

                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val listaUno = dataUiState.BPSapList
                        val listaDos = dataUiState.BPDeviceList
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Clientes en SAP")
                                LazyColumnWithCards(listaUno, viewModel, false)
                            }
                        }
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Clientes en la tablet")
                                LazyColumnWithCards(listaDos, viewModel, false)
                            }
                        }
                    }
                }
            }
        }

        /* Column {
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
fun LazyColumnWithCards(data: List<BusinessPartner?>, viewModel: BusinessPartnerViewModel, isLazyColumn: Boolean) {
    if (isLazyColumn) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
        ) {
            if (data.isNotEmpty()) {
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
                            Column {
                                if (item != null) {
                                    Text(
                                        text = item.CardCode,
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                    Text(
                                        text = item.CardName,
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                    Text(
                                        text = item.Cellular,
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
            } else {
                item {
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
                                Text(
                                    text = "No hay datos",
                                    modifier = Modifier.padding(24.dp)
                                )

                            }
                        }
                    }
                }
            }

        }
    } else {
        LazyRow(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
        ) {
            if (data.isNotEmpty()) {
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
                            Column {
                                if (item != null) {
                                    Text(
                                        text = item.CardCode,
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                    Text(
                                        text = item.CardName,
                                        modifier = Modifier.padding(start = 16.dp, 5.dp)
                                    )
                                    Text(
                                        text = item.Cellular,
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
            } else {
                item {
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
                                Text(
                                    text = "No hay datos",
                                    modifier = Modifier.padding(24.dp)
                                )

                            }
                        }
                    }
                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldBusinessPartnerUltimate(
    navController: NavHostController,
    viewModel: BusinessPartnerViewModel = viewModel()
) {
    val dataUiState = viewModel.uiState.collectAsState()
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
                            "Activiades",
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
                        value = dataUiState.value.Option,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.checkOption(navController) }) {
                                Icon(
                                    Icons.Filled.CallMade,
                                    contentDescription = "Shopping Cart Icon"
                                )
                            }
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp)
                            .padding(8.dp),
                        leadingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        coffeeDrinks.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    viewModel.changeActionButton(item)
                                    expanded = false
                                }
                            )
                        }
                    }
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
            BusinessPartnerUltimate(innerPadding, viewModel)
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