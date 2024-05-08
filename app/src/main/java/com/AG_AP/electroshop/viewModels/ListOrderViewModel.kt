package com.AG_AP.electroshop.viewModels

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.uiState.ListOrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListOrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListOrderUiState())
    val uiState: StateFlow<ListOrderUiState> = _uiState.asStateFlow()

    init {
        OrderCRUD.getAllObject { list ->
            _uiState.update {
                currentState -> currentState.copy(
                    ListOrder = list as List<OrderFireBase?>
                )
            }
        }
    }
}