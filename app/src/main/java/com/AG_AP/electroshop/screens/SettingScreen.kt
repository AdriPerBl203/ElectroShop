package com.AG_AP.electroshop.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.AG_AP.electroshop.viewModels.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.AG_AP.electroshop.components.CircularIndicator

@Composable
fun SettingScreen(
    viewModel: SettingsViewModel = viewModel(),
    navController: NavController,
    context: Context
) {
    val dataUiState by viewModel.uiState.collectAsState()
    if(dataUiState.init){
        viewModel.initData(context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier= Modifier
                .padding(horizontal = 100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(width = 1.dp, Color.Black, shape = RoundedCornerShape(25.dp))
        ){
            Row(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp,top=20.dp, bottom = 20.dp).fillMaxWidth()
            ) {
                /*Text(
                    text = "Conexión con SAP",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )*/
                Column(){
                OutlinedTextField(
                    value = dataUiState.urlExt,
                    onValueChange = { viewModel.changeUrlExt(it) },
                    label = { Text("URL externa") },
                    trailingIcon = { Icon(imageVector = dataUiState.iconExt, contentDescription = "emailIcon") },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(250.dp)
                )

                OutlinedTextField(
                    //TODO
                    value = dataUiState.puertoExterno,
                    onValueChange = { viewModel.changePuertoExterno(it) },
                    label = { Text("Puerto externo") },
                    trailingIcon = { Icon(imageVector = dataUiState.iconInt, contentDescription = "emailIcon") },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(250.dp)
                )

                OutlinedTextField(
                    value = dataUiState.urlExtPDF,
                    onValueChange = { viewModel.changeUrlExtPDF(it) },
                    label = { Text("URL externa PDF") },
                    trailingIcon = { Icon(imageVector = dataUiState.iconExt, contentDescription = "emailIcon") },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(250.dp)
                )

                OutlinedTextField(
                    //TODO
                    value = dataUiState.puertoExternoPDF,
                    onValueChange = { viewModel.changePuertoExternoPDF(it) },
                    label = { Text("Puerto externo PDF") },
                    trailingIcon = { Icon(imageVector = dataUiState.iconInt, contentDescription = "emailIcon") },
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(250.dp)
                )

                    OutlinedTextField(
                        //TODO
                        value = dataUiState.codePDF,
                        onValueChange = { viewModel.changeCodePDF(it) },
                        label = { Text("Código PDF") },
                        trailingIcon = { Icon(imageVector = Icons.Default.DocumentScanner, contentDescription = "emailIcon") },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(250.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column() {

                    OutlinedTextField(
                        value = dataUiState.urlInt,
                        onValueChange = { viewModel.changeUrlInt(it) },
                        label = { Text("URL interna") },
                        trailingIcon = {
                            Icon(
                                imageVector = dataUiState.iconInt,
                                contentDescription = "emailIcon"
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(250.dp)
                    )

                    OutlinedTextField(
                        //TODO
                        value = dataUiState.puertoInterno,
                        onValueChange = { viewModel.changePuertoInterno(it) },
                        label = { Text("Puerto interno PDF") },
                        trailingIcon = {
                            Icon(
                                imageVector = dataUiState.iconInt,
                                contentDescription = "emailIcon"
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(250.dp)
                    )

                    OutlinedTextField(
                        value = dataUiState.urlIntPDF,
                        onValueChange = { viewModel.changeUrlIntPDF(it) },
                        label = { Text("URL interna PDF") },
                        trailingIcon = {
                            Icon(
                                imageVector = dataUiState.iconInt,
                                contentDescription = "emailIcon"
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(250.dp)
                    )

                    OutlinedTextField(
                        //TODO
                        value = dataUiState.puertoInternoPDF,
                        onValueChange = { viewModel.changePuertoInternoPDF(it) },
                        label = { Text("Puerto interno") },
                        trailingIcon = {
                            Icon(
                                imageVector = dataUiState.iconInt,
                                contentDescription = "emailIcon"
                            )
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(250.dp)
                    )

                    Row(){
                        var selectedOption by remember { mutableStateOf("Option 1") }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption == "Option 1",
                                onClick = { selectedOption = "Option 1" }
                            )
                            Text(text = "Interna", modifier = Modifier.padding(start = 8.dp))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption == "Option 2",
                                onClick = { selectedOption = "Option 2" }
                            )
                            Text(text = "Externa", modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }

            }
        }
        
        Spacer(modifier = Modifier.height(25.dp))
        
        Box(
            modifier = Modifier
                .padding(horizontal = 100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(width = 1.dp, Color.Black, shape = RoundedCornerShape(25.dp))
        ){
            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp,top=20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "Credenciales",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(bottom = 16.dp),
                )
                OutlinedTextField(
                    value = dataUiState.login,
                    onValueChange = { viewModel.changeUrlUser(it) },
                    label = { Text("Usuario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = dataUiState.password,
                    onValueChange = { viewModel.changeUrlPass(it) },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = dataUiState.dataBase,
                    onValueChange = { viewModel.changeDataBase(it) },
                    label = { Text("Base de datos") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }

        //TODO añadir checkboxes para las configuraciones

        Spacer(modifier = Modifier.height(25.dp))

        Box(
            modifier= Modifier
                .padding(horizontal = 100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(width = 1.dp, Color.Black, shape = RoundedCornerShape(25.dp))
        ){
            Row(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp,top=20.dp, bottom = 20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Clientes"
                        )
                        Checkbox(
                            checked = dataUiState.checkBoxClients,
                            onCheckedChange = { viewModel.changecheckBoxClients() }
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Pedidos"
                        )
                        Checkbox(
                            checked = dataUiState.checkBoxOrders,
                            onCheckedChange = { viewModel.changecheckBoxOrders() }
                        )
                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Articulos"
                        )
                        Checkbox(
                            checked = dataUiState.checkBoxItems,
                            onCheckedChange = { viewModel.changecheckBoxItems() }
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Actividades"
                        )
                        Checkbox(
                            checked = dataUiState.checkBoxActivity,
                            onCheckedChange = { viewModel.changecheckBoxActivity() }
                        )
                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Usuarios"
                        )
                        Checkbox(
                            checked = dataUiState.checkBoxUDO,
                            onCheckedChange = { viewModel.changecheckBoxUDO() }
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Todo"
                        )
                        Checkbox(
                            checked = dataUiState.checkBoxTodo,
                            onCheckedChange = { viewModel.changecheckBoxTodo() }
                        )
                    }
                }

            }
        }

        //FIN TODO

        if(dataUiState.progress){
            CustomLinearProgressBar()
            //CircularIndicator(50.dp)
        }

        if (dataUiState.message) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Snackbar(
                    modifier = Modifier
                        .padding(start = 50.dp, top = 16.dp)
                        .align(alignment = Alignment.CenterVertically),
                    action = {
                        Button(
                            onClick = {
                                viewModel.menssageFunFalse()
                            },
                            enabled = dataUiState.btnEnable
                        ) {
                            Text("Cerrar")
                        }
                    },
                    content = {
                        if(dataUiState.textShow){
                            Text(dataUiState.text)
                        }
                        if(dataUiState.syncProgress){
                            Column(){
                                Row(){
                                    Column(){
                                        Row(){
                                            Text("Sincronizando clientes")
                                            if(dataUiState.checkBusinessPartner){
                                                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.surface)
                                            }else{
                                                CircularIndicator(25.dp)
                                            }
                                        }
                                        Row(){
                                            Text("Sincronizando usuarios")
                                            if(dataUiState.checkUserUdo){
                                                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.surface)
                                            }else{
                                                CircularIndicator(25.dp)
                                            }
                                        }
                                        Row(){
                                            Text("Sincronizando actividades")
                                            if(dataUiState.checkActivity){
                                                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.surface)
                                            }else{
                                                CircularIndicator(25.dp)
                                            }
                                        }
                                    }
                                    Column(
                                        modifier = Modifier.padding(start = 10.dp)
                                    ){
                                        Row(){
                                            Text("Sincronizando articulos")
                                            if(dataUiState.checkItem){
                                                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.surface)
                                            }else{
                                                CircularIndicator(25.dp)
                                            }
                                        }
                                        Row(){
                                            Text("Sincronizando precios especiales")
                                            if(dataUiState.checkPreciosEspeciales){
                                                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.surface)
                                            }else{
                                                CircularIndicator(25.dp)
                                            }
                                        }
                                        Row(){
                                            Text("Sincronizando lista de precios")
                                            if(dataUiState.checkPriceLists){
                                                Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.surface)
                                            }else{
                                                CircularIndicator(25.dp)
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center

        ){
            Button(

                modifier = Modifier.padding(10.dp),
                onClick = { viewModel.saveConfiguration(context) },
                enabled = dataUiState.ButtomEnable
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp))
                Text(text = "Guardar")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { viewModel.test() },
            ) {
                Icon(
                    imageVector = Icons.Filled.ContentPasteSearch,
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp))
                Text(text = "Test")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { viewModel.sync(context) },
                enabled = dataUiState.btnSyncEnable
            ) {
                Icon(
                    imageVector = Icons.Filled.Sync,
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp))
                Text(text = "Sincronizar")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { navController.navigateUp() },
                enabled = dataUiState.btnExitEnable
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardReturn,
                    contentDescription = "Cart button icon",
                    modifier = Modifier.size(20.dp))
                Text(text = "Volver")
            }

        }
    }
}

@Composable
fun CustomLinearProgressBar(){
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(5.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp),
            color = MaterialTheme.colorScheme.primaryContainer //progress color
        )
    }
}

@Preview(device = Devices.TABLET, showSystemUi = true)
@Composable
fun PreviewSettingScreen() {
    //SettingScreen()
}

