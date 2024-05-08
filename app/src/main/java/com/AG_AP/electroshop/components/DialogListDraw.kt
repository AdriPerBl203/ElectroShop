package com.AG_AP.electroshop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.AG_AP.electroshop.functions.ListCheckTotal

@Composable
fun DialogListDraw(actionItemList:()->Unit ,infoDialog:String,TextOrList:Boolean,showIndicator:Boolean,exit:()->Unit){
        AlertDialogExample(
            onDismissRequest = {  },
            onConfirmation = {
                actionItemList()
            },
            dialogTitle = infoDialog,
            dialogText = "Puede tardar en función de la carga.",
            icon = Icons.Default.Info,
            showIndicator,
            TextOrList,
            exit
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    showIndicator: Boolean,
    TextOrList: Boolean,
    exit: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement= Arrangement.Center,
                horizontalAlignment= Alignment.CenterHorizontally
            ){
                if(TextOrList){
                    Text(text = dialogText)
                }else{
                    for(x in ListCheckTotal.getList()){
                        ListItem(
                            headlineContent = { Text(x) },
                            leadingContent = {
                                Icon(
                                    Icons.Filled.CheckCircle,
                                    contentDescription = "Icon",
                                )
                            }
                        )
                    }
                }
                if(showIndicator){
                    CircularIndicator(35.dp)
                }else{
                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "", tint= MaterialTheme.colorScheme.primaryContainer)
                }
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    exit()
                    onConfirmation()
                }
            ) {
                Text("Aceptar")
            }
        },
        /*dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }*/
    )
}