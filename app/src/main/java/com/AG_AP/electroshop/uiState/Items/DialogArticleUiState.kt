package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class DialogArticleUiState(
    val codeArticle: String = "",
    val description:String ="",
    val count:String ="0",
    val price:String ="0",
    val discount:String ="0",
    val ListItems:List<OrderFireBase> = listOf(),
    val showDialogSelectCodeArticle:Boolean =false
)
