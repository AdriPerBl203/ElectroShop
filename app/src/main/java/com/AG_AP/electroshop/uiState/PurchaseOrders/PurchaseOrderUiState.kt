package com.AG_AP.electroshop.uiState.PurchaseOrders

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.uiState.Items.ArticleUiState
import java.util.concurrent.ConcurrentHashMap

data class PurchaseOrderUiState(
    val CardCode: String = "",
    val CardName: String = "",
    val DocNum: Int = -1,
    val DocDate: String = "",
    val DocDueDate: String = "",
    val TaxDate: String = "",
    val SalesPersonCode: String = "",
    val DiscountPercent: Double = 0.0, //Descuento
    val DocumentLine: MutableList<ArticleUiState?> = listOf(null).toMutableList(),
    val DocumentLineList: ConcurrentHashMap<Int, MutableList<String>> = ConcurrentHashMap(),
    val trash: Int = 0,
    val message:Boolean = false,
    val progress:Boolean = false,
    val text:String = "",
    val showDialogAddArticle: Boolean = false,
    val showToast: Boolean = false,
    val ListBusinessPartner:List<BusinessPartner> = listOf(),
    val showDialogBusinessPartner:Boolean =false
)
