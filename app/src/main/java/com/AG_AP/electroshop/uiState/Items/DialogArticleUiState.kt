package com.AG_AP.electroshop.uiState.Items

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class DialogArticleUiState(
    val codeArticle: String = "23",
    val description:String ="we",
    val count:String ="0",
    val price:String ="0",
    val discount:String ="0",
    //val ListItems:List<OrderFireBase> = listOf(),
    val ListItems:List<Item> = listOf(),
    val showDialogSelectCodeArticle:Boolean =false
)
