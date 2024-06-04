package com.AG_AP.electroshop.uiState

import android.graphics.pdf.PdfRenderer
import com.AG_AP.electroshop.firebase.models.InvoiceData

data class InvoiceUiState(
    val BusinessPartnerWithInvoiceList: MutableList<InvoiceData?> = mutableListOf(),
    val Base64String: String = "",
    val ActualPdf: PdfRenderer? = null
)
