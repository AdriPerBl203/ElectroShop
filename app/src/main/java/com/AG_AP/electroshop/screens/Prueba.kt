package com.AG_AP.electroshop.screens

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PdfViewer(base64String: String) {
    val context = LocalContext.current
    val pdfFile = remember {
        decodeBase64ToPdfFile(context.cacheDir, base64String)
    }
    val pdfRenderer = remember {
        PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY))
    }
    val pageCount = pdfRenderer.pageCount
    var currentPage by remember { mutableStateOf(0) }

    Column (
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        // Render PDF pages
        pdfRenderer.openPage(currentPage).use { page ->
            val bitmap = Bitmap.createBitmap(2800, 4000, Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "PDF Page",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        // Navigation buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            if (currentPage > 0) {
                Button(onClick = { currentPage-- }) {
                    Text("Previous")
                }
            }
            if (currentPage < pageCount - 1) {
                Button(onClick = { currentPage++ }) {
                    Text("Next")
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun decodeBase64ToPdfFile(cacheDir: File, base64String: String): File {
    val pdfBytes = Base64.getDecoder().decode(base64String)
    val pdfFile = File(cacheDir, "temp.pdf")
    try {
        FileOutputStream(pdfFile).use { outputStream ->
            outputStream.write(pdfBytes)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return pdfFile
}

