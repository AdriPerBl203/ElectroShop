package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class DialogArticleUiState(
    val codeArticle: String = "",
    val description: String = "",
    val count: Double = 1.0,
    val price: Double = 0.0,
    val discount: Double = 0.0,
    val showMessageSpecialPrices:Boolean = false,
    //val ListItems:List<OrderFireBase> = listOf(),
    val ListItems: List<Item> = listOf(),
    val showDialogSelectCodeArticle: Boolean = false
)
