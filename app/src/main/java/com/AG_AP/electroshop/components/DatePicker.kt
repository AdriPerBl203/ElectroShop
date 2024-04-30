package com.AG_AP.electroshop.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import java.util.*
import com.AG_AP.electroshop.viewModels.DatePickerViewModel

@Composable
fun DatePicker(viewModel: DatePickerViewModel) {
    var fecha: String by rememberSaveable {
        mutableStateOf(viewModel.selectedDate)
    }

    val anio: Int
    val mes: Int
    val dia: Int
    val mCalendar: Calendar = Calendar.getInstance()

    anio = mCalendar.get(Calendar.YEAR)
    mes = mCalendar.get(Calendar.MONTH)
    dia = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, anio: Int, mes: Int, dia: Int ->
            fecha = "$dia/${mes + 1}/$anio"

        }, anio, mes, dia
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                readOnly = true,
                label = { Text(text = "Selecciona fecha") },
                )
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp)
                    .clickable {
                        mDatePickerDialog.show()
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDatePickerDemo() {
    MaterialTheme {
        //MiDatePicker()
    }
}
