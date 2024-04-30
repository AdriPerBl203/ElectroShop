package com.AG_AP.electroshop.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.uiState.MenuUiState
import com.AG_AP.electroshop.viewModels.MenuViewModel
import com.AG_AP.electroshop.viewModels.Routes


/**
 * Method that contains the view of the Screen Menu
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuFrontView(
    viewModel: MenuViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val dataUiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    if(SessionObj.checkLogin()){
        viewModel.viewEnd(navController)
    }

    /* Top border */
    Scaffold(
        topBar = {
            TopBar(
                onMenuButtonClick = { /*TODO*/ },
                menuUiState = dataUiState
            )
        },
        /* Boton flotante */
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.closeSession(navController)
                }
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "Crear")
            }


        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Seidor S.A",
                )
            }
        }


    ) {
        /* Principal content */

            innerPadding ->
        MenuBody(
            innerPadding = innerPadding,
            navController,
            dataUiState
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onMenuButtonClick: () -> Unit, menuUiState: MenuUiState) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bienvenido ${menuUiState.username}"
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),

        /* Barra de navegacion */
        navigationIcon = {
            IconButton(onClick = { /*TODO*/}) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Abrir menu")
            }
        }

    )
}

/**
 * Method that contains most of the important usages of the App
 */
@Composable
fun MenuBody(
    innerPadding: PaddingValues,
    navController: NavHostController,
    dataUiState: MenuUiState
) {
    Box(
        modifier = Modifier.padding(innerPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp),
            contentAlignment= Alignment.TopCenter
        ){
            Column (
                modifier = Modifier.padding(5.dp).background(MaterialTheme.colorScheme.primaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (

                ){
                    if(dataUiState.articulo == "S"){
                        ElevatedButton(
                            modifier= Modifier.padding(30.dp).width(200.dp),
                            onClick = { navController.navigate(route = Routes.ScreenActivity.route) }
                        ) {
                            Text("Actividades")
                        }
                        ElevatedButton(
                            modifier= Modifier.padding(30.dp).width(200.dp),
                            onClick = { navController.navigate(route = Routes.ListActivity.route) }
                        ) {
                            Text("Historial de actividades")
                        }
                    }
                }
                Row (

                ){
                    ElevatedButton(
                        modifier= Modifier.padding(30.dp).width(200.dp),
                        onClick = { navController.navigate(route = Routes.BusinessPartner.route) }
                    ) {
                        Text("Clientes")
                    }

                    ElevatedButton(
                        modifier= Modifier.padding(30.dp).width(200.dp),
                        onClick = { navController.navigate(route = Routes.ScreenBusinessPartnerList.route) }
                    ) {
                        Text("Historial de clientes")
                    }
                }

                Row (

                ){
                    if(dataUiState.pedidoCL == "S"){
                        ElevatedButton(
                            modifier= Modifier.padding(30.dp).width(200.dp),
                            onClick = { /* TODO*/ }
                        ) {
                            Text("Pedido de cliente")
                        }

                        ElevatedButton(
                            modifier= Modifier.padding(30.dp).width(200.dp),
                            onClick = { /* TODO*/ }
                        ) {
                            Text("Historial pedido cliente")
                        }
                    }
                }

                Row (

                ){
                    if(dataUiState.pedidoCL == "S"){
                        ElevatedButton(
                            modifier= Modifier.padding(30.dp).width(200.dp),
                            onClick = { /* TODO*/ }
                        ) {
                            Text("Pedido de compra")
                        }

                        ElevatedButton(
                            modifier= Modifier.padding(30.dp).width(200.dp),
                            onClick = { /* TODO*/ }
                        ) {
                            Text("Historial pedido compra")
                        }
                    }
                }
            }
        }
    }
}


/**
 * Method that contains drawer content
 */
@Composable
private fun DrawerContent(closeDrawer: () -> Unit) {
    Column {
        sections.forEach { section ->
            TextButton(
                onClick = closeDrawer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = section
                    )
                }
            }
        }
    }
}

//TODO Sacar a otra clase
val sections = listOf(
    "Interlocutores",
    "Pedidos",
    "Articulos",
    "Pedidos",
    "Compra",
    "Actividades"
)


// TODO funcion que muestre la cantidad de acciones disponibles con un array

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun MenuFrontViewPreview() {
    //MenuFrontView(navController = navController)
}