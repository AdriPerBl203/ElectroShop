package com.AG_AP.electroshop.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.OrderFireBase

@Composable
fun DialogActivity(
    data: () -> List<Any>,
    infoDialog: String,
    clasedDialog: () -> Unit,
    returnData: (Any) -> Unit,
    returnDataSearch:(Any) -> Unit,
    valueSearch: String
) {

    Dialog(onDismissRequest = { clasedDialog() }) {
        Card(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp), text = infoDialog
                )
                OutlinedTextField(
                    modifier=Modifier.padding(vertical = 10.dp),
                    value = valueSearch,
                    trailingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "emailIcon") },
                    onValueChange = {
                        returnDataSearch(it)
                    },
                    label = { Text(text = "Buscar") },
                )
                LazyColumn {

                    if(data().size == 0){
                        item {
                            ListItem(
                                headlineContent = { Text("Sin resultados con la busqueda.") }
                            )
                        }
                    }else{
                        items(data()) { x ->
                            if (x is OrderFireBase) {
                                ListItem(
                                    headlineContent = { Text("${x.DocNum}") },
                                    trailingContent = {
                                        IconButton(onClick = {
                                            returnData(x.DocNum.toString())
                                            clasedDialog()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.AddCircle,
                                                contentDescription = "Settings",
                                                tint = MaterialTheme.colorScheme.primaryContainer
                                            )
                                        }
                                    }
                                )
                            }

                            if (x is BusinessPartner) {
                                ListItem(
                                    headlineContent = { Text("${x.CardCode} -- ${x.CardName}") },
                                    trailingContent = {
                                        IconButton(onClick = {
                                            returnData(x)
                                            clasedDialog()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.AddCircle,
                                                contentDescription = "Settings",
                                                tint = MaterialTheme.colorScheme.primaryContainer
                                            )
                                        }
                                    }
                                )
                            }

                            if (x is Item) {
                                ListItem(
                                    headlineContent = { Text("${x.ItemCode} -- ${x.itemName}") },
                                    trailingContent = {
                                        IconButton(onClick = {
                                            returnData(x)
                                            clasedDialog()
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.AddCircle,
                                                contentDescription = "Settings",
                                                tint = MaterialTheme.colorScheme.primaryContainer
                                            )
                                        }
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