package com.AG_AP.electroshop.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.AG_AP.electroshop.R
import com.AG_AP.electroshop.components.CircularIndicator
import com.AG_AP.electroshop.viewModels.LoginViewModel
import com.AG_AP.electroshop.viewModels.Routes

/**
 * Method that shows the front view of the Login Screen TODO sacarlo todo a metodos extras
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginFrontView(
    viewModel: LoginViewModel = viewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val dataUiState by viewModel.uiState.collectAsState()


    /* Content */
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        /* GIF */
        /*
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
*/
        /* Content */

        Box {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                /* Middle screen */
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(Color(255, 255, 255, 216), shape = RoundedCornerShape(25.dp))
                        .padding(15.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Inicio de sesión",
                            fontSize = 32.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(5.dp)
                        )

                        /* Text Fields */
                        Box {
                            Column {
                                OutlinedTextField(
                                    value = dataUiState.username,
                                    trailingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "emailIcon") },
                                    onValueChange = {
                                        viewModel.usernameChange(it)
                                    },
                                    label = { Text(text = "Usuario") },
                                )

                                Spacer(
                                    modifier = Modifier
                                        .padding(6.dp)
                                )

                                OutlinedTextField(
                                    value = dataUiState.password,
                                    onValueChange = { viewModel.passwordChange(it) },
                                    label = { Text("Password") },
                                    singleLine = true,
                                    visualTransformation = if (dataUiState.seePass) VisualTransformation.None else PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    trailingIcon = {
                                        IconButton(onClick = {viewModel.showPass()}){
                                            Icon(imageVector  = dataUiState.iconPass, "")
                                        }
                                    }
                                )
                            }
                        }

                    }
                }

                /* Middle buttons */
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Row {
                        Button(
                            onClick = {
                                viewModel.saveConnection(navController)
                            }
                        ) {
                            Text(
                                text = "Enviar datos"
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .padding(5.dp)
                        )
                        Button(
                            onClick = {
                                viewModel.resetData()
                            }
                        ) {
                            Text(text = "Limpiar pantalla")
                        }

                    }
                }
                if (dataUiState.circularProgress) {
                    CircularIndicator(40.dp)
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

            }

            /* Footer */
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp, end = 10.dp, bottom = 10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier.clickable {
                        navController.navigate(route = Routes.ScreenConfig.route)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .background(
                                Color(255, 255, 255, 216),
                                shape = RoundedCornerShape(25.dp)
                            )
                            .padding(15.dp)
                    ) {
                        Text(
                            text = "Ajustes",
                            textAlign = TextAlign.Center
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(5.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.engranaje),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
            }
        }
    }

}


@Composable
@Preview(
    showSystemUi = true,
    device = Devices.PIXEL_TABLET,
    showBackground = true
)
fun LoginFrontViewPreview() {

}