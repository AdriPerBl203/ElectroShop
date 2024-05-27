package com.AG_AP.electroshop.components

import androidx.compose.foundation.layout.Arrangement
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
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.functions.InterconexionUpdateArticle
import com.AG_AP.electroshop.viewModels.Items.DialogArticleViewModel

@Composable
fun DialogOandPO(
    viewModel: DialogArticleViewModel = viewModel(),
    closeDialog: () -> Unit,
    returnData: (List<String>) -> Unit,
    index: Int =-1
) {
    val dataUiState by viewModel.uiState.collectAsState()

    if(InterconexionUpdateArticle.data != null){
        viewModel.showDateForUpdate(InterconexionUpdateArticle.data!!)
    }

    Dialog(onDismissRequest = { closeDialog() }) {
        if (dataUiState.showDialogSelectCodeArticle) {
            DialogActivity(
                data = { dataUiState.ListItems },
                "Seleccione Código articulo",
                { viewModel.closeDialogSelectCodeArticle() },
                { data ->
                    if (data is Item) {
                        viewModel.changeCodeArticle(data.ItemCode)
                        viewModel.changeDescription(data.itemName)
                        viewModel.changeCount(data.itemPrice?.get(1)?.quantity.toString())
                        viewModel.changePrice(data.itemPrice?.get(1)?.price.toString())
                        viewModel.changeDiscount(data.itemPrice?.get(1)?.discount.toString())
                    } else {
                        viewModel.changeCodeArticle(data.toString())
                    }

                }
            )
        }
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Column {
                    OutlinedTextField(
                        value = dataUiState.codeArticle,
                        onValueChange = { viewModel.changeCodeArticle(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        label = { Text("Código articulo") },
                        readOnly = true,
                        trailingIcon = {
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
                            .width(300.dp)
                            .padding(8.dp),
                        label = { Text("Descripción") }
                    )

                    OutlinedTextField(
                        value = dataUiState.count.toString(),
                        onValueChange = { viewModel.changeCount(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Cantidad") }
                    )
                }

                Column {
                    OutlinedTextField(
                        value = dataUiState.price.toString(),
                        onValueChange = { viewModel.changePrice(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        suffix = { Text(text = "€") },
                        label = { Text("Precio") }
                    )

                    OutlinedTextField(
                        value = dataUiState.discount.toString(),
                        onValueChange = { viewModel.changeDiscount(it) },
                        modifier = Modifier
                            .width(300.dp)
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        suffix = { Text(text = "%") },
                        label = { Text("Descuento %") }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ElevatedButton(
                    onClick = {
                        val listData = listOf(
                            dataUiState.codeArticle,
                            dataUiState.description,
                            dataUiState.count.toString(),
                            dataUiState.price.toString(),
                            dataUiState.discount.toString()
                        )
                        returnData(listData)
                        viewModel.resetData()
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