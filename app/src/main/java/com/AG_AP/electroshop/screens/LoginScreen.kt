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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.AG_AP.electroshop.R

/**
 * Method that shows the front view of the Login Screen
 */
@Composable
fun FrontView(modifier: Modifier = Modifier) {
    val painter = painterResource(id = R.drawable.emoticono_2)

    // TODO sacar
    var user by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    /* Content */
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
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
                            fontSize = 40.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(5.dp)
                        )

                        /* Text Fields */
                        Box {
                            Column {
                                OutlinedTextField(
                                    value = user,
                                    onValueChange = { user = it },
                                    label = { Text("Usuario") }
                                )

                                Spacer(
                                    modifier = Modifier
                                        .padding(6.dp)
                                )

                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text("Contraseña") },
                                    visualTransformation = PasswordVisualTransformation()
                                )
                            }
                        }

                    }
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
                        // TODO hacer cambio a la ventana de ajuste
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
fun FrontViewPreview() {
    FrontView()
}