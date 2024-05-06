package com.AG_AP.electroshop.viewModels
/*
import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.uiState.ItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ItemViewModel : ViewModel(), ActionViewModel {

    private val _uiState = MutableStateFlow(ItemUiState())
    val uiState: StateFlow<ItemUiState> = _uiState.asStateFlow()

    fun changeItemCode(itemCode: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ItemCode = itemCode
            )
        }
    }

    fun changeItemName(itemName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                itemName = itemName
            )
        }
    }

    fun changeQuantity(quantity: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                Quantity = quantity
            )
        }
    }

    fun changePrice(price: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                Price = price
            )
        }
    }

    fun changeDiscountPercent(discountPercent: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                DiscountPercent = discountPercent
            )
        }
    }

    override fun guardar(data: Boolean) {
        TODO("Not yet implemented")
    }

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun borrar() {
        TODO("Not yet implemented")
    }

    override fun find() {
        TODO("Not yet implemented")
    }

    override fun menssageFunFalse() {
        TODO("Not yet implemented")
    }
}

 */