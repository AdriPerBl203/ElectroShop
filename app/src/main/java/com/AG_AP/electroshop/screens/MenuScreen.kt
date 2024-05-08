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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import com.AG_AP.electroshop.components.DialogListDraw
import com.AG_AP.electroshop.components.ListActionDraw
import com.AG_AP.electroshop.functions.SessionObj
import com.AG_AP.electroshop.uiState.MenuUiState
import com.AG_AP.electroshop.viewModels.MenuViewModel
import com.AG_AP.electroshop.viewModels.Routes
import kotlinx.coroutines.launch


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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    if(dataUiState.dialog){
        DialogListDraw(
            infoDialog= dataUiState.InfoDialog,
            actionItemList = {viewModel.closedDialog()},
            showIndicator = dataUiState.checkProgresCircular,
            TextOrList= dataUiState.TextOrList,
            exit = {viewModel.changeCheckProgresCircular()}
        )
    }

    if (SessionObj.checkLogin()) {
        viewModel.viewEnd(navController)
    }

    /* Top border */
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ListDraw(viewModel,navController)
        },
    ) {
    Scaffold(
        topBar = {
            TopBar(
                menuUiState = dataUiState
            )
        },
        /* Boton flotante */
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //viewModel.closeSession(navController)
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.ContentPasteSearch, contentDescription = "opciones")
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

}

@Composable
fun ListDraw(viewModel: MenuViewModel, navController: NavHostController) {
    val list : List<ListActionDraw> = listOf(
        ListActionDraw("Subir actividades",Icons.Filled.LocalActivity,"Sincronizando actividades"){
            viewModel.upActivities(true)
        },
        ListActionDraw("Subir articulos",Icons.Filled.Article,"Sincronizando Articulos"){
            viewModel.upItems()
        },
        ListActionDraw("Subir clientes",Icons.Filled.AccountCircle,"Sincronizando clientes"){
            viewModel.upBusinessPartners(true)
        },
        ListActionDraw("Subir pedido de compra",Icons.Filled.Reorder,"Sincronizando pedido de compra"){
            viewModel.upPurchaseOrders(true)
        },
        ListActionDraw("Subir pedido de cliente",Icons.Filled.AddBusiness,"Sincronizando pedido de cliente"){
            viewModel.upOrder(true)
        },
        ListActionDraw("Subir todo",Icons.Filled.AccountTree,"Sincronizando todo"){
            viewModel.upTotal()
        },
        ListActionDraw("Cerrar sesión",Icons.Filled.KeyboardReturn,"Cerrar sesión") {
            viewModel.closeSession(navController)
        }
    )
    ModalDrawerSheet {
        Column(
            modifier = Modifier.padding(top=10.dp)
        ) {
            for (x in list){
                ListItem(
                    headlineContent = { Text(x.text) },
                    leadingContent = {
                        Icon(
                            x.icon,
                            contentDescription = "Localized description",
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = {
                            viewModel.showDialog(x.textDialog)
                            x.action()
                        }) {
                            Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Settings", tint = MaterialTheme.colorScheme.primaryContainer)
                        }
                    }
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar( menuUiState: MenuUiState) {
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
        )

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
                modifier = Modifier
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (

                ){
                    if(dataUiState.articulo == "S"){
                        ElevatedButton(
                            modifier= Modifier
                                .padding(30.dp)
                                .width(200.dp),
                            onClick = { navController.navigate(route = Routes.ScreenActivity.route) }
                        ) {
                            Text("Actividades")
                        }
                        ElevatedButton(
                            modifier= Modifier
                                .padding(30.dp)
                                .width(200.dp),
                            onClick = { navController.navigate(route = Routes.ListActivity.route) }
                        ) {
                            Text("Historial de actividades")
                        }
                    }
                }
                Row (

                ){
                    ElevatedButton(
                        modifier= Modifier
                            .padding(30.dp)
                            .width(200.dp),
                        onClick = { navController.navigate(route = Routes.BusinessPartner.route) }
                    ) {
                        Text("Clientes")
                    }

                    ElevatedButton(
                        modifier= Modifier
                            .padding(30.dp)
                            .width(200.dp),
                        onClick = { navController.navigate(route = Routes.ScreenBusinessPartnerList.route) }
                    ) {
                        Text("Historial de clientes")
                    }
                }

                Row (

                ){
                    if(dataUiState.pedidoCL == "S"){
                        ElevatedButton(
                            modifier= Modifier
                                .padding(30.dp)
                                .width(200.dp),
                            onClick = { navController.navigate(route = Routes.ScreenOrder.route) }
                        ) {
                            Text("Pedido de cliente")
                        }

                        ElevatedButton(
                            modifier= Modifier
                                .padding(30.dp)
                                .width(200.dp),
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
                            modifier= Modifier
                                .padding(30.dp)
                                .width(200.dp),
                            onClick = { navController.navigate(route = Routes.PurchaseOrderScreen.route) }
                        ) {
                            Text("Pedido de compra")
                        }

                        ElevatedButton(
                            modifier= Modifier
                                .padding(30.dp)
                                .width(200.dp),
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