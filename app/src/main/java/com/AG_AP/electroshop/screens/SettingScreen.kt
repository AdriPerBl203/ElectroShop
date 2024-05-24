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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentPasteSearch
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
            Column(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp,top=20.dp, bottom = 20.dp)
            ) {
                Text(
                    text = "Conexión con SAP",
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = dataUiState.urlExt,
                    onValueChange = { viewModel.changeUrlExt(it) },
                    label = { Text("URL externa") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = dataUiState.urlInt,
                    onValueChange = { viewModel.changeUrlInt(it) },
                    label = { Text("URL interna") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

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
                    modifier = Modifier.padding(start=50.dp,top=16.dp).align(alignment = Alignment.CenterVertically),
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

