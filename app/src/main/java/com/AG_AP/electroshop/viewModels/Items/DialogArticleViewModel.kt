package com.AG_AP.electroshop.viewModels.Items

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.BusinessPartnerCRUD
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.OrderCRUD
import com.AG_AP.electroshop.firebase.models.BusinessPartner
import com.AG_AP.electroshop.firebase.models.Item
import com.AG_AP.electroshop.firebase.models.OrderFireBase
import com.AG_AP.electroshop.functions.InterconexionUpdateArticle
import com.AG_AP.electroshop.uiState.Items.ArticleUiState
import com.AG_AP.electroshop.uiState.Items.DialogArticleUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogArticleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DialogArticleUiState())
    val uiState: StateFlow<DialogArticleUiState> = _uiState.asStateFlow()

    init {
        ItemCRUD.getAllItems {
            list ->
            val mutableList = list as? MutableList<Item>

            mutableList?.let {
                _uiState.update { currentState -> currentState.copy(
                    ListItems = it.toList()
                ) }
            }
        }
    }

    fun changeCodeArticle(it: String) {
        _uiState.update { currentState -> currentState.copy(
            codeArticle = it
        ) }
    }

    fun changeDescription(it: String) {
        _uiState.update { currentState -> currentState.copy(
            description = it
        ) }
    }

    fun changeCount(it: String) {
        _uiState.update { currentState -> currentState.copy(
            count = it
        ) }
    }

    fun changePrice(it: String) {
        _uiState.update { currentState -> currentState.copy(
            price = it
        ) }
    }

    fun changeDiscount(it: String) {
        _uiState.update { currentState -> currentState.copy(
            discount = it
        ) }
    }

    fun closeDialogSelectCodeArticle() {
        _uiState.update { currentState -> currentState.copy(
            showDialogSelectCodeArticle = false,
        ) }
    }

    fun showDialogSelectCode() {
        _uiState.update { currentState -> currentState.copy(
            showDialogSelectCodeArticle = true,
            discount ="",
            price ="",
            count ="",
            description ="",
            codeArticle ="",
        ) }
    }

    fun resetData() {
        _uiState.update { currentState -> currentState.copy(
            discount ="0",
            price ="0",
            count ="0",
            description ="",
            codeArticle ="",
        ) }
    }

    fun showDateForUpdate(data: ArticleUiState) {
        val d = InterconexionUpdateArticle.data?.copy()
        InterconexionUpdateArticle.data=null
        _uiState.update { currentState -> currentState.copy(
            discount = d?.DiscountPercent.toString(),
            price = d?.Price.toString(),
            count = d?.Quantity.toString(),
            description = d?.ItemDescription.toString(),
            codeArticle = d?.ItemCode.toString(),
        ) }
    }
}