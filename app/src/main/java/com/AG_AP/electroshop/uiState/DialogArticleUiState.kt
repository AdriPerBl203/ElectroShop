package com.AG_AP.electroshop.uiState

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class DialogArticleUiState(
    val codeArticle: String = "",
    val description:String ="",
    val count:String ="",
    val price:String ="",
    val discount:String ="",
    val ListItems:List<OrderFireBase> = listOf(),
    val showDialogSelectCodeArticle:Boolean =false
)
