package com.AG_AP.electroshop.screens.Activities

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.components.DatePicker
import com.AG_AP.electroshop.components.DialogActivity
import com.AG_AP.electroshop.components.TopBarButton
import com.AG_AP.electroshop.realm.models.Activity
import com.AG_AP.electroshop.nav.Routes
import com.AG_AP.electroshop.uiState.Activities.ActivityUiState
import com.AG_AP.electroshop.viewModels.Activities.ActivityViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityUltimate(
    innerPadding: PaddingValues,
    viewModel: ActivityViewModel,
    id: String?,
    dataUiState: ActivityUiState
) {


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
            { data -> viewModel.changePedidoCompra(data) }
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
            { data -> viewModel.changeCardCode(data) }
        )
    }

    BoxWithConstraints {
        if (maxWidth > 800.dp) {
            Row(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                //Contiene los datos principales
                Row {
                    Column { //
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
                            onValueChange = { /*dataUiState.U_SEIPEDIDOCLIENTE*/ },
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

                    }
                }

                //Contiene los filtros de búsqueda
                Column(
                    modifier = Modifier.width(200.dp)
                ) {
                    OutlinedTextField(
                        value = dataUiState.dataFilter,
                        onValueChange = { viewModel.changeDataFilter(it) },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(8.dp),
                        label = { Text("Buscar") }
                    )

                    ElevatedButton(onClick = { viewModel.findFilter() }) {
                        Text("Buscar")
                    }

                }

                //Contiene la lista de las actividades almacenadas
                Column {
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Total de resultados: ${dataUiState.totalSearch}")
                    }

                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Clientes en SAP")
                            LazyColumnWithCards(dataUiState.ListActivityTheSAP, viewModel)

                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Clientes en la tablet")
                            LazyColumnWithCards(dataUiState.ListActivityTheTablet, viewModel)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.
                            verticalScroll(state = rememberScrollState())
                ) {
                    //Contiene los datos principales
                    Column (
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    ) {
                        Column { //
                            val coffeeDrinks =
                                arrayOf(
                                    "Llamada telefónica",
                                    "Reunión",
                                    "Tarea",
                                    "Nota",
                                    "Campaña",
                                    "Otros"
                                )
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
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Nota") }
                            )
                            DatePicker(
                                label = "Fecha", dataUiState.ActivityDate, modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp)
                            ) {
                                viewModel.changeActivityDate(it)
                            }
                            OutlinedTextField(
                                value = dataUiState.ActivityTime,
                                onValueChange = { viewModel.changeActivityTime(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Hora inicio") },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            DialogStartTime.show()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.AccessTime,
                                            contentDescription = "Shopping Cart Icon"
                                        )
                                    }
                                }
                            )

                            OutlinedTextField(
                                value = dataUiState.U_SEIPEDIDOCLIENTE,
                                onValueChange = { /*dataUiState.U_SEIPEDIDOCLIENTE*/ },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Asociar pedido de cliente") },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.showDialogOrder()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "Shopping Cart Icon"
                                        )
                                    }
                                }
                            )
                            OutlinedTextField(
                                value = dataUiState.ClgCode,
                                onValueChange = { viewModel.changeClgCode(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Id") },
                                enabled = false
                            )
                        }

                        Column {
                            val priority = arrayOf("Bajo", "Normal", "Alto")
                            var expandedTwo by remember { mutableStateOf(false) }
                            OutlinedTextField(
                                value = dataUiState.EndTime,
                                onValueChange = { viewModel.changeEndTime(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Hora fin") },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            DialogStartEnd.show()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.AccessTime,
                                            contentDescription = "Shopping Cart Icon"
                                        )
                                    }
                                }
                            )
                            OutlinedTextField(
                                value = dataUiState.CardCode,
                                onValueChange = { viewModel.changeCardCode(it) },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(8.dp),
                                label = { Text("Código cliente") },
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            viewModel.showDialogBusinessPartner()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "Shopping Cart Icon"
                                        )
                                    }
                                }
                            )
                            OutlinedTextField(
                                value = dataUiState.Tel,
                                onValueChange = { viewModel.changeTel(it) },
                                modifier = Modifier
                                    .width(250.dp)
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
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = expandedTwo
                                        )
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .width(250.dp)
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

                        }
                    }

                    //Contiene los filtros de búsqueda
                    Column(
                        modifier = Modifier.width(200.dp)
                    ) {
                        OutlinedTextField(
                            value = dataUiState.dataFilter,
                            onValueChange = { viewModel.changeDataFilter(it) },
                            modifier = Modifier
                                .width(250.dp)
                                .padding(8.dp),
                            label = { Text("Buscar") }
                        )

                        ElevatedButton(onClick = { viewModel.findFilter() }) {
                            Text("Buscar")
                        }

                    }

                }
                //Contiene la lista de las actividades almacenadas
                Column {
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(text = "Total de resultados: ${dataUiState.totalSearch}")
                    }

                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Clientes en SAP")
                            LazyColumnWithCards(dataUiState.ListActivityTheSAP, viewModel)

                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Clientes en la tablet")
                            LazyColumnWithCards(dataUiState.ListActivityTheTablet, viewModel)
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun LazyColumnWithCards(data: List<Activity>, viewModel: ActivityViewModel) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
    ) {

        if (data.size == 0) {
            item() {
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(200.dp)
                        .height(190.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Sin datos")
                    }
                }
            }
        } else {
            items(data) { item ->
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(200.dp)
                        .height(190.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Column() {
                            Row {
                                Text(
                                    text = item.ActivityDate.split("T")[0],
                                    modifier = Modifier.padding(16.dp)
                                )
                                IconButton(onClick = {
                                    viewModel.showDataPlus(item)
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "Settings",
                                        tint = MaterialTheme.colorScheme.primaryContainer
                                    )
                                }
                            }

                            Row() {
                                Text(
                                    text = item.ActivityTime,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Text(
                                    text = item.EndTime,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            Row() {
                                Text(
                                    text = item.CardCode,
                                    modifier = Modifier.padding(16.dp)
                                )

                                Text(
                                    text = when (item.Priority) {
                                        "pr_Low" -> "Bajo"
                                        "pr_Normal" -> "Normal"
                                        "pr_High" -> "Alto"
                                        else -> ""
                                    },
                                    modifier = Modifier.padding(16.dp),
                                    color = when (item.Priority) {
                                        "pr_Low" -> Color.Green
                                        "pr_Normal" -> Color.Magenta
                                        "pr_High" -> Color.Red
                                        else -> Color.Black
                                    }
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
fun ScaffoldActivityUltimate(
    viewModel: ActivityViewModel = viewModel(),
    navController: NavHostController,
    id: String? = null
) {
    val dataUiState by viewModel.uiState.collectAsState()
    if (!id.isNullOrEmpty()) {
        viewModel.changeClgCode(id)
        viewModel.refreshScreen()
    }
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
                /*Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { viewModel.ejecutarAction(navController) }
                ) {
                    Text(text = "Acción")
                }*/
                Button(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    onClick = { navController.popBackStack() }
                ) {
                    Text(text = "Volver")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.findFilter()
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Buscar")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(start = 50.dp, top = 20.dp)) {
            ActivityUltimate(innerPadding, viewModel, id, dataUiState)
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
    //ScaffoldActivityUltimate(navController)
}