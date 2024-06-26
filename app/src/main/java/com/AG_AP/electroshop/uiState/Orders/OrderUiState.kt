package com.AG_AP.electroshop.uiState.Orders

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.uiState.Items.ArticleUiState
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import java.util.concurrent.ConcurrentHashMap

data class OrderUiState(
    val CardCode: String = "",
    val CardName: String = "",
    val DocNum: Int = -1,
    val DocDate: String = "",
    val DocDueDate: String = "",
    val TaxDate: String = "",
    val inputSearch: String = "",
    val SalesPersonCode: String = "",
    val DiscountPercent: Double = 0.0, //Descuento
    val DocumentLine: MutableList<ArticleUiState?> = mutableListOf(),
    //val DocumentLineList: MutableList<String> = mutableListOf(),
    val DocumentLineList: ConcurrentHashMap<Int, MutableList<String>> = ConcurrentHashMap(),
    val trash: Int = 0,
    val message: Boolean = false,
    val progress: Boolean = false,
    val text: String = "",
    val showDialogAddArticle: Boolean = false,
    val showDialogSelectCodeArticle: Boolean = false,
    val showToast: Boolean = false,
    val ListItems: List<OrderFireBase> = listOf(),
    var ListBusinessPartnerAux: List<BusinessPartner> = listOf(),
    var ListBusinessPartnerBackund: List<BusinessPartner> = listOf(),
    val showDialogBusinessPartner: Boolean = false,
    val ListBusinessPartner: List<BusinessPartner> = listOf(),
    val ListOrdersInSap: List<OrderFireBase> = listOf(),
    val ListOrdersInDevice: List<OrderFireBase> = listOf(),
    val dialogAddArticleUpdate: Boolean = false,
    val ActionButton: String = "Añadir y ver",
    val totalPrice: Double = 0.0,
    val actualDate: Boolean = false
)

/*
*
* val DocumentLine: MutableList<ArticleUiState?> = listOf(
        ArticleUiState(
            0, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            1, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            2, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            3, "", "", 0.0F, 0.0F, 0.0F
        ),
        ArticleUiState(
            4, "", "", 0.0F, 0.0F, 0.0F
        )
    ).toMutableList()
* */