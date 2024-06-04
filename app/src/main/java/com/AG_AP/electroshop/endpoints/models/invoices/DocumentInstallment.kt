package com.AG_AP.electroshop.endpoints.models.invoices

data class DocumentInstallment(
    val DueDate: String,
    val DunningLevel: Int,
    val InstallmentId: Int,
    val LastDunningDate: Any,
    val PaymentOrdered: String,
    val Percentage: Double,
    val Total: Double,
    val TotalFC: Any
)