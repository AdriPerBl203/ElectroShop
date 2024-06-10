package com.AG_AP.electroshop.screens

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.TopBarButton
import com.AG_AP.electroshop.functions.ObjectContext
import com.AG_AP.electroshop.nav.Routes
import com.AG_AP.electroshop.viewModels.InvoiceViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceUltimate(innerPadding: PaddingValues, viewModel: InvoiceViewModel) {
    val dataUiState by viewModel.uiState.collectAsState()

    BoxWithConstraints {
        if (maxWidth > 360.dp) {
            Row(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                //Left column
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = dataUiState.CardName,
                        onValueChange = { viewModel.cardNameChange(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        label = { Text("Búsqueda") }
                    )
                    Spacer(
                        modifier = Modifier.padding(5.dp)
                    )
                    LazyColumn {

                        if (dataUiState.BusinessPartnerWithInvoiceList.size == 0) {
                            items(1) {
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
                                                text = "Sin datos con las búsqueda.",
                                                modifier = Modifier.padding(start = 16.dp, 5.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            items(dataUiState.BusinessPartnerWithInvoiceList) { item ->
                                if (item != null) {
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
                                                    text = item.CardCode,
                                                    modifier = Modifier.padding(start = 16.dp, 5.dp)
                                                )
                                                Text(
                                                    text = item.DocEntry.toString(),
                                                    modifier = Modifier.padding(start = 16.dp, 5.dp)
                                                )
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
                    }
                }

                //Right column
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (dataUiState.ActualPdf == null) {
                        item {
                            Text(
                                text = "No hay ningún pdf cargado",
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        item {
                            //TODO cargar pdf
                            dataUiState.ActualPdf!!.openPage(0).use { page ->
                                val bitmap =
                                    Bitmap.createBitmap(2800, 4000, Bitmap.Config.ARGB_8888)
                                page.render(
                                    bitmap,
                                    null,
                                    null,
                                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                                )
                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = "PDF Page",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(color = Color.White)
                                )
                            }
                        }

                        item {
                            Button(onClick = {
                                viewModel.savePDF()
                                Toast.makeText(
                                    ObjectContext.context,
                                    "Pdf descargado en \"Documentos\"",
                                    Toast.LENGTH_LONG
                                ).show()
                            }) {
                                Text(
                                    text = "Descargar pdf"
                                )
                            }
                            //Mostrar toast
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(state = rememberScrollState())
            ) {
                //Left column
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = dataUiState.CardName,
                        onValueChange = { viewModel.cardNameChange(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        label = { Text("Búsqueda") }
                    )
                    Spacer(
                        modifier = Modifier.padding(5.dp)
                    )
                    LazyColumn {

                        if (dataUiState.BusinessPartnerWithInvoiceList.size == 0) {
                            items(1) {
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
                                                text = "Sin datos con las búsqueda.",
                                                modifier = Modifier.padding(start = 16.dp, 5.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            items(dataUiState.BusinessPartnerWithInvoiceList) { item ->
                                if (item != null) {
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
                                                    text = item.CardCode,
                                                    modifier = Modifier.padding(start = 16.dp, 5.dp)
                                                )
                                                Text(
                                                    text = item.DocEntry.toString(),
                                                    modifier = Modifier.padding(start = 16.dp, 5.dp)
                                                )
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
                    }
                }

                //Right column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (dataUiState.ActualPdf == null) {
                        Text(
                            text = "No hay ningún pdf cargado",
                            textAlign = TextAlign.Center
                        )
                    } else {
                        dataUiState.ActualPdf!!.openPage(0).use { page ->
                            val bitmap = Bitmap.createBitmap(2800, 4000, Bitmap.Config.ARGB_8888)
                            page.render(
                                bitmap,
                                null,
                                null,
                                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                            )
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "PDF Page",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.White)
                            )
                        }

                        Button(onClick = {
                            viewModel.savePDF()
                            Toast.makeText(
                                ObjectContext.context,
                                "Pdf descargado en \"Documentos\"",
                                Toast.LENGTH_LONG
                            ).show()
                        }) {
                            Text(
                                text = "Descargar pdf"
                            )
                        }
                        //Mostrar toast

                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
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