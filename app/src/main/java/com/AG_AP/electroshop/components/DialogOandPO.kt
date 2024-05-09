package com.AG_AP.electroshop.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.AG_AP.electroshop.viewModels.DialogArticleViewModel

@Composable
fun DialogOandPO(viewModel: DialogArticleViewModel = viewModel(),closeDialog:()->Unit,retunrData: (List<String>) -> Unit){
    val dataUiState by viewModel.uiState.collectAsState()
    Dialog(onDismissRequest = { closeDialog() }){
        if(dataUiState.showDialogSelectCodeArticle){
            DialogActivity(
                data ={ dataUiState.ListItems },
                "Seleccione Código articulo",
                { viewModel.closeDialogSelectCodeArticle() },
                {data -> viewModel.changeCodeArticle(data) }
            )
        }
        Card (
            shape = RoundedCornerShape(16.dp)
        ){
            Row{
                Column {
                    OutlinedTextField(
                        value = dataUiState.codeArticle,
                        onValueChange = { viewModel.changeCodeArticle(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        label = { Text("Código cliente") },
                        readOnly = true,
                        trailingIcon={
                            IconButton(
                                onClick = {
                                    viewModel.showDialogSelectCode()
                                }
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = dataUiState.description,
                        onValueChange = { viewModel.changeDescription(it) },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(8.dp),
                        suffix = { Text(text = "%") },
                        label = { Text("Descripción") }
                    )

                    OutlinedTextField(
                        value = dataUiState.count,
                        onValueChange = { viewModel.changeCount(it) },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Cantidad") }
                    )
                }

                Column {
                    OutlinedTextField(
                        value = dataUiState.price,
                        onValueChange = { viewModel.changePrice(it) },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        suffix = { Text(text = "€") },
                        label = { Text("Precio") }
                    )

                    OutlinedTextField(
                        value = dataUiState.discount,
                        onValueChange = { viewModel.changeDiscount(it) },
                        modifier = Modifier
                            .width(100.dp)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        suffix = { Text(text = "%") },
                        label = { Text("Descuento %") }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment= Alignment.CenterVertically
            ) {
                ElevatedButton(
                    onClick = {
                        val listData = listOf(
                            dataUiState.codeArticle,
                            dataUiState.description,
                            dataUiState.count,
                            dataUiState.price,
                            dataUiState.discount
                        )
                        retunrData(listData)
                        closeDialog()
                    }
                ) {
                    Text(text = "Añadir")
                    Icon(imageVector = Icons.Filled.AddBox, contentDescription = "Añadir articulo")
                }
            }
        }
    }
}