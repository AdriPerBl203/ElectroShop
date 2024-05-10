package com.AG_AP.electroshop.viewModels.PurchaseOrders

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.PurchaseOrderCRUD
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.PurchaseOrders.ListPurchaseOrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListPurchaseOrderViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ListPurchaseOrderUiState())
    val uiState: StateFlow<ListPurchaseOrderUiState> = _uiState.asStateFlow()


    init {
        PurchaseOrderCRUD.getAllObject { list ->
            _uiState.update {
                currentState -> currentState.copy(
                    ListPurchaseOrder = list as List<OrderFireBase?>
                )
            }
        }
    }

}