package com.AG_AP.electroshop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.AG_AP.electroshop.uiState.MenuUiState
import com.AG_AP.electroshop.viewModels.MenuViewModel
import java.lang.reflect.Modifier

@Composable
fun DialogListDraw(actionItemList:()->Unit ,infoDialog:String){
        AlertDialogExample(
            onDismissRequest = {  },
            onConfirmation = {
                actionItemList()
            },
            dialogTitle = infoDialog,
            dialogText = "Puede tardar en función de la carga.",
            icon = Icons.Default.Info
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
                verticalArrangement= Arrangement.Center,
                horizontalAlignment= Alignment.CenterHorizontally
            ){
                Text(text = dialogText)
                CircularIndicator(35.dp)
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
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