package com.AG_AP.electroshop.screens.Orders

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.viewModels.Orders.OrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.AG_AP.electroshop.components.DatePicker
import com.AG_AP.electroshop.components.DialogActivity
import com.AG_AP.electroshop.uiState.Orders.OrderUiState
import com.AG_AP.electroshop.components.DialogOandPO
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.functions.InterconexionUpdateArticle
import com.AG_AP.electroshop.functions.ObjectContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderView(innerPadding: PaddingValues, viewModel: OrderViewModel, id: String?) {
    val dataUiState by viewModel.uiState.collectAsState()

    //Comprueba si hay algún articulo, si lo hay actualiza el precio total
    viewModel.changeTotalPrice()


    if (dataUiState.showDialogAddArticle) {
        DialogOandPO(
            closeDialog = { viewModel.closeDialogaddArticle() },
            returnData = { list ->
                Log.e("LisDataArticle", list.toString())
                viewModel.addArticle(list)
            },
            cardCode = dataUiState.CardCode,
        )
    }
    if (dataUiState.showDialogBusinessPartner) {
        DialogActivity(
            data = { dataUiState.ListBusinessPartner },
            "Seleccione cliente",
            { viewModel.closeDialogBusinessPartner() },
            { data ->
                if (data is BusinessPartner) {
                    viewModel.changeCardCode(data.CardCode)
                    viewModel.changeName(data.CardName)
                }

            }
        )
    }
    /*if(dataUiState.showDialogSelectCodeArticle){
        DialogActivity(
            data ={ dataUiState.ListItems },
            "Seleccione Código articulo",
            { viewModel.closeDialogSelectCodeArticleTwo() },
            {data -> /*viewModel.change*/ }
        )
    }*/

    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        Row {
            LazyColumn(
                modifier = Modifier
                    .width(250.dp)
                    //.height(400.dp)
                    .padding(horizontal = 10.dp)
            ) {
                items(dataUiState.ListOrders){order ->
                    Card(
                        modifier = Modifier.padding(5.dp).height(200.dp)
                    ){
                        Column(
                            modifier = Modifier.padding(3.dp)
                        ) {
                            Row(
                                modifier= Modifier.fillMaxWidth(),
                                horizontalArrangement= Arrangement.SpaceBetween,
                            ) {
                                Text(text = order.DocDate.split("T").firstOrNull() ?: "Sin fecha")
                                IconButton(onClick = {
                                    viewModel.showOrderComplete(order)
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Camera,
                                        contentDescription = "Settings",
                                        tint = MaterialTheme.colorScheme.primaryContainer
                                    )
                                }
                            }
                            Row(
                                modifier= Modifier.fillMaxWidth(),
                                horizontalArrangement= Arrangement.SpaceBetween,
                            ){
                                Text(text = order.CardCode)
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            order.DocumentLines.forEachIndexed { index, line ->
                                Row {
                                    Text(text = "${line.ItemCode} -- ${line.Quantity} -- ${line.Price}€")
                                }
                            }
                        }
                    }
                }
            }
            Column { //
                OutlinedTextField(
                    value = dataUiState.SalesPersonCode,
                    onValueChange = { viewModel.changeSalesPersonCode(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("SLPCode") }
                )

                OutlinedTextField(
                    value = dataUiState.CardCode,
                    onValueChange = { viewModel.changeCardCode(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Código cliente") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.showDialogBusinessPartner()
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

                OutlinedTextField(
                    value = dataUiState.CardName,
                    onValueChange = { viewModel.changeName(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nombre") },
                    readOnly = true
                )

                OutlinedTextField(
                    value = dataUiState.DiscountPercent.toString(),
                    onValueChange = { viewModel.changeDiscount(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    suffix = { Text(text = "%") },
                    label = { Text("Descuento %") }
                )


            }

            Column {
                DatePicker(
                    "Fecha contabilizacion ",
                    dataUiState.TaxDate,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(300.dp)
                ) { fechaDocumento ->
                    viewModel.changeTaxDate(fechaDocumento)
                }
                DatePicker(
                    "Fecha entrega ",
                    dataUiState.DocDueDate,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(300.dp)
                ) { fechaDocumento ->
                    viewModel.changeDocDueDate(fechaDocumento)
                }
                DatePicker(
                    "Fecha documento ",
                    dataUiState.DocDate,
                    modifier = Modifier
                        .padding(8.dp)
                        .width(300.dp)
                ) { fechaDocumento ->
                    viewModel.changeDocDate(fechaDocumento)
                }
                OutlinedTextField(
                    value = dataUiState.totalPrice.toString(),
                    onValueChange = { viewModel.changeTotalPrice() },
                    modifier = Modifier
                        .padding(8.dp)
                        .width(300.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    readOnly = true,
                    suffix = { Text(text = "€") },
                    label = { Text("Precio total") }
                )

            } //

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                ElevatedButton(
                    onClick = {
                        viewModel.showDialogaddArticle()
                    }
                ) {
                    Text(text = "Añadir articulo")
                    Icon(imageVector = Icons.Filled.AddBox, contentDescription = "Añadir articulo")
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, end = 30.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, end = 30.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                //TODO cuando sea inactivo el pedido no deja aumentar ni disminuir las lineas
                //if (id == null) {
                Row {
                    IconButton(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .border(
                                BorderStroke(0.5.dp, Color.Black)
                            )
                            .padding(end = 0.5.dp),
                        onClick = { viewModel.deleteLine() }
                    ) {
                        Text(text = "-")
                    }
                }
                //}

                TableDocumentLineOrder(dataUiState, viewModel)

                if (dataUiState.showToast) {
                    Toast.makeText(
                        ObjectContext.context,
                        dataUiState.text,
                        Toast.LENGTH_LONG
                    ).show()
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
}

@Composable
fun TableDocumentLineOrder(dataUiState: OrderUiState, viewModel: OrderViewModel) {

    val numCols = 7

    // Datos de ejemplo para las cabeceras
    val headers = listOf(
        "Editar",
        "Nº",
        "Código Articulo",
        "Descripción articulo",
        "Cantidad",
        "Precio",
        "% de descuento"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(numCols)
    ) {
        items(items = headers) {
            Box(
                modifier = Modifier
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    it,
                    Modifier
                        .height(50.dp)
                        .wrapContentSize()
                )
            }

        }
    }


    LazyVerticalGrid(columns = GridCells.Fixed(numCols)) {
        dataUiState.DocumentLineList.forEach { index, value ->
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        modifier = Modifier
                            .height(50.dp)
                            .wrapContentSize(),
                        onClick = {
                            viewModel.showDialogaddArticle()
                            InterconexionUpdateArticle.data = dataUiState.DocumentLine[index]
                            InterconexionUpdateArticle.index = index
                            // TODO obejct
                        }
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "+ Icon")
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        index.toString(),
                        Modifier
                            .height(50.dp)
                            .wrapContentSize()
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[1].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp),
                        readOnly = true
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[2].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp),
                        readOnly = true
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[3].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp),
                        readOnly = true
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[4].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp),
                        readOnly = true
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = value[5].toString(),
                        onValueChange = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .height(50.dp),
                        readOnly = true
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldOrder(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController,
    id: String? = null
) {
    val dataUiState by viewModel.uiState.collectAsState()
    if (id != null) {
        viewModel.changeDocNum(id.toInt())
        viewModel.refresh()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Pedido de cliente")
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
                        value = dataUiState.ActionButton,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(
                                onClick = { viewModel.ejecutarAction(navController) }) {
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
            OrderView(innerPadding, viewModel, id)
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.TABLET
)
@Composable
fun ScaffoldPurchaseOrderPreview() {
    //ScaffoldOrder()
}
