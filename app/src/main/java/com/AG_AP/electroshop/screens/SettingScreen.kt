package com.AG_AP.electroshop.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.AG_AP.electroshop.viewModels.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SettingScreen(viewModel: SettingsViewModel = viewModel()) {
    val dataUiState by viewModel.uiState.collectAsState()

    //var urlExt by remember {mutableStateOf("")}
    val customColor = Color(android.graphics.Color.parseColor("#00c9ff"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(horizontal = 200.dp)
            //.background(color = customColor)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
                .padding(horizontal = 150.dp)
        )
        OutlinedTextField(
            value = dataUiState.urlInt,
            onValueChange = { viewModel.changeUrlInt(it) },
            label = { Text("URL interna") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 150.dp)
        )

        Text(
            text = "Credenciales",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier.padding( horizontal = 100.dp )
        ){
            Column {
                OutlinedTextField(
                    value = dataUiState.login,
                    onValueChange = { viewModel.changeUrlUser(it) },
                    label = { Text("Usuario") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
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
        Row(){
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { },
            ) {
                Text(text = "Guardar")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { viewModel.menssageFun() },
            ) {
                Text(text = "Test")
            }
            Button(
                modifier = Modifier.padding(10.dp),
                onClick = { },
            ) {
                Text(text = "Volver")
            }

        }
    }
}

