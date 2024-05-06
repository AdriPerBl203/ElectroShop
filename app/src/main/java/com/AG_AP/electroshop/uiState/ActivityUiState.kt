package com.AG_AP.electroshop.uiState

import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.DocumentLineFireBase
import com.AG_AP.electroshop.firebase.models.OrderFireBase

data class ActivityUiState(
    val nota:String = "Esto es una nota",
    val ActivityDate:String = "2016-08-30",
    val ActivityTime:String = "",
    val CardCode:String = "",
    val EndTime:String = "",
    val Tel:String = "654354654",
    val ClgCode:String = "",
    val Priority:String = "Normal",
    val U_SEIPEDIDOCOMPRAS:String = "",
    val U_SEIPEDIDOCLIENTE:String = "",
    val message:Boolean = false,
    val progress:Boolean = false,
    val Action:String = "Llamada telefónica",
    val text:String = "",
    val showDialogOrder:Boolean = false,
    val showDialogPurchaseOrder:Boolean = false,
    val showDialogBussinesPartner:Boolean = false,
    val ListOrders:List<OrderFireBase> = listOf(),
    val ListPurchaseOrders:List<OrderFireBase> = listOf(),
    val ListBusinessPartner:List<BusinessPartner> = listOf()
)
/*val nota:String = "Esto es una nota",
val ActivityDate:String = "2016-08-30",
val ActivityTime:String = "08:13:00",
val CardCode:String = "C01",
val EndTime:String = "08:28:00",
val Tel:String = "654354654",
val ClgCode:String = "",
val Priority:String = "Normal",*/