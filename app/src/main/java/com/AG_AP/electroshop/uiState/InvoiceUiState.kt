package com.AG_AP.electroshop.uiState

import android.graphics.pdf.PdfRenderer
import com.AG_AP.electroshop.realm.models.InvoiceData
import java.io.File

data class InvoiceUiState(
    var BusinessPartnerWithInvoiceList: MutableList<InvoiceData?> = mutableListOf(),
    val BusinessPartnerWithInvoiceListBackud: MutableList<InvoiceData?> = mutableListOf(),
    val Base64String: String = "",
    val CardName: String = "",
    val ActualPdf: PdfRenderer? = null,
    var pdfFile: File? = null,
    val ActualCardCode: String = "",
    var itemActual: InvoiceData? = null
)
