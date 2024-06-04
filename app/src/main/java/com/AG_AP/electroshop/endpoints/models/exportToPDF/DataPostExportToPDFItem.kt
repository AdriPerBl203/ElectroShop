package com.AG_AP.electroshop.endpoints.models.exportToPDF

data class DataPostExportToPDFItem(
    val name: String,
    val type: String,
    val value: List<List<String>>
)