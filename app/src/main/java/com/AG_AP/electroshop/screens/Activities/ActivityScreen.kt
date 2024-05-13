package com.AG_AP.electroshop.screens.Activities


import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.DatePicker
import com.AG_AP.electroshop.components.DialogActivity
import com.AG_AP.electroshop.viewModels.Activities.ActivityViewModel
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityView(innerPadding: PaddingValues, viewModel: ActivityViewModel, id: String?) {
    val dataUiState by viewModel.uiState.collectAsState()

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val DialogStartTime = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            val formattedHour = if (mHour < 10) "0$mHour" else "$mHour"
            val formattedMinute = if (mMinute < 10) "0$mMinute" else "$mMinute"
            viewModel.changeStartTime("$formattedHour:$formattedMinute:00")
        },
        mHour,
        mMinute,
        false
    )
    val DialogStartEnd = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            val formattedHour = if (mHour < 10) "0$mHour" else "$mHour"
            val formattedMinute = if (mMinute < 10) "0$mMinute" else "$mMinute"
            viewModel.changeEndTime("$formattedHour:$formattedMinute:00")
        },
        mHour,
        mMinute,
        false
    )

    if (dataUiState.showDialogPurchaseOrder) {
        DialogActivity(
            data = { dataUiState.ListPurchaseOrders },
            "Seleccione pedido de compra",
            { viewModel.closerDialogPurchaseOrder() },
            { data -> viewModel.changePedidoCompra(data.toString()) }
        )
    }
    if (dataUiState.showDialogOrder) {
        DialogActivity(
            data = { dataUiState.ListOrders },
            "Seleccione pedido de cliente",
            { viewModel.closerDialogOrder() },
            { data -> viewModel.changePedidoCliente(data.toString()) }
        )
    }
    if (dataUiState.showDialogBussinesPartner) {
        DialogActivity(
            data = { dataUiState.ListBusinessPartner },
            "Seleccione cliente",
            { viewModel.closerDialogBusinessPartner() },
            { data -> viewModel.changeCardCode(data.toString()) }
        )
    }
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            /*modifier= Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalArrangement= Arrangement.Center*/
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
                        value = dataUiState.Action,
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
                                    viewModel.changeAction(item)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dataUiState.nota,
                    onValueChange = { viewModel.changenota(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Nota") }
                )
                DatePicker(
                    label = "Fecha", dataUiState.ActivityDate, modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp)
                ) {
                    viewModel.changeActivityDate(it)
                }
                OutlinedTextField(
                    value = dataUiState.ActivityTime,
                    onValueChange = { viewModel.changeActivityTime(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora inicio") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                DialogStartTime.show()
                            }
                        ) {
                            Icon(Icons.Filled.AccessTime, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

                OutlinedTextField(
                    value = dataUiState.U_SEIPEDIDOCLIENTE,
                    onValueChange = { viewModel.changePedidoCliente(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Asociar pedido de cliente") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.showDialogOrder()
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )
            }

            Column {
                val priority = arrayOf("Bajo", "Normal", "Alto")
                var expandedTwo by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = dataUiState.EndTime,
                    onValueChange = { viewModel.changeEndTime(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Hora fin") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                DialogStartEnd.show()
                            }
                        ) {
                            Icon(Icons.Filled.AccessTime, contentDescription = "Shopping Cart Icon")
                        }
                    }
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
                    value = dataUiState.Tel,
                    onValueChange = { viewModel.changeTel(it) },
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
                        value = dataUiState.Priority,
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
                                    viewModel.changePriority(item)
                                    expandedTwo = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = dataUiState.U_SEIPEDIDOCOMPRAS,
                    onValueChange = { viewModel.changePedidoCompra(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Asociar pedido de compra") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                viewModel.showDialogPurchaseOrder()
                            }
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "Shopping Cart Icon")
                        }
                    }
                )

            }
            Column {
                OutlinedTextField(
                    value = dataUiState.ClgCode,
                    onValueChange = { viewModel.changeClgCode(it) },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    label = { Text("Id") },
                    enabled = false
                )
            }
        }
        Column {
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
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldActivity(
    viewModel: ActivityViewModel = viewModel(),
    navController: NavHostController,
    id: String? = null
) {
    if (!id.isNullOrEmpty()) {
        viewModel.changeClgCode(id)
        viewModel.refreshScreen()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Gestión de actividad")
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
                    onClick = { viewModel.guardar(false) }
                ) {
                    Text(text = "Añadir y nuevo")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.guardar(true) }
                ) {
                    Text(text = "Añadir y ver")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.update() }
                ) {
                    Text(text = "Actualizar")
                }
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.borrar() }
                ) {
                    Text(text = "Borrar")
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
            FloatingActionButton(onClick = { viewModel.find() }) {
                Icon(Icons.Default.Add, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            ActivityView(innerPadding, viewModel, id)
        }
    }
}