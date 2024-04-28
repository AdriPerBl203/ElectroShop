package com.AG_AP.electroshop.screens

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.firebase.models.Activity
import com.AG_AP.electroshop.uiState.ListActivityUiState
import com.AG_AP.electroshop.viewModels.ListActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListActivityView(innerPadding: PaddingValues, viewModel: ListActivityViewModel) {
    val dataUiState by viewModel.uiState.collectAsState()
    Row (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalArrangement= Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .width(300.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)

        ) {
            FilterActivity(viewModel,dataUiState)
        }
        Column(
            modifier = Modifier
                .width(400.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalAlignment= Alignment.CenterHorizontally
        ) {
            ListActivityColumn(viewModel,dataUiState)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldListActivity(viewModel: ListActivityViewModel = viewModel(), navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Listado de actividades")
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
                    onClick = { navController.navigateUp() }
                ) {
                    Text(text = "Volver")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* tODO */ }) {
                Icon(Icons.Default.Add, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding( top = 20.dp)){
            ListActivityView(innerPadding,viewModel)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun ListActivityViewPreview() {
    //ListActivityViewPre()
}

@Composable
fun ListActivityColumn(viewModel: ListActivityViewModel, dataUiState: ListActivityUiState) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign= TextAlign.Center,
        text = "Listado de actividades"
    )
    Spacer(modifier = Modifier.height(40.dp))
    //val dataList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    LazyColumnExample(dataUiState.ListActivity)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterActivity(viewModel: ListActivityViewModel, dataUiState: ListActivityUiState) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign= TextAlign.Center,
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

@Composable
fun LazyColumnExample(data: List<Activity?>) {
    LazyColumn {
        items(data) { item ->
            // Aquí defines cómo se muestra cada elemento de la lista
            ElevatedCardExample(item)
        }
    }
}

@Composable
fun ElevatedCardExample(x: Activity?) {
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
            horizontalArrangement= Arrangement.SpaceAround
        ){
            Column {
                if (x != null) {
                    Text(
                        text = x.ClgCode,
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Ver")
            }
        }
    }
}


