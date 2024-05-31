package com.AG_AP.electroshop.viewModels.Items

import android.util.Log
import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.ItemCRUD
import com.AG_AP.electroshop.firebase.SpecialPricesCRUD
import com.AG_AP.electroshop.firebase.models.Item
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
        ItemCRUD.getAllItems { list ->
            val mutableList = list as? MutableList<Item>

            mutableList?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        ListItems = it.toList(),
                        ListItemsAux=it.toList(),
                        ListItemsBackund=it.toList()
                    )
                }
            }
        }
    }

    fun changeCodeArticle(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                codeArticle = it
            )
        }
    }

    fun changeDescription(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                description = it
            )
        }
    }

    fun changeCount(it: String) {
        val count = it.toDouble()
        if (count > 0) {
            _uiState.update { currentState ->
                currentState.copy(
                    count = count
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    count = 1.0
                )
            }
        }

    }

    fun changeInputSearch(it: String) {
        _uiState.update { currentState ->
            currentState.copy(
                inputSearch = it
            )
        }
    }

    fun changePrice(it: String) {
        val price = it.toDouble()
        if (price > 0) {
            _uiState.update { currentState ->
                currentState.copy(
                    price = price
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    price = 0.0
                )
            }
        }

    }

    fun SpecialPrices(itenCode: String, cardCode: String){
        SpecialPricesCRUD.getSpecialPrice(cardCode,itenCode){x->
            if(x != null){
                changeDiscount(x.DiscountPercent.toString())
                _uiState.update { currentState ->
                    currentState.copy(
                        showMessageSpecialPrices=true
                    )
                }
            }
        }

    }

    fun changeDiscount(it: String) {
        var disc = 0.0
        try {
            var decimal = it.substringAfter(".", "")
            var num = it.substringBefore(".", "")

            if (decimal.length > 2) {
                decimal = decimal.substring(2)
            }

            if (num.length > 3) {
                num = num.substring(3)
            }


            val numTotal = "$num.$decimal"
            disc = numTotal.toDouble()

            if (disc <= 0.0) {
                disc = 0.0
            } else if (disc >= 100.0) {
                disc = 100.0
            }
        } catch (e: Exception) {
            Log.e("Errores", e.stackTraceToString())
        }

        _uiState.update { currentState ->
            currentState.copy(
                discount = disc
            )
        }
    }

    fun closeDialogSelectCodeArticle() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogSelectCodeArticle = false,
            )
        }
    }

    fun showDialogSelectCode() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialogSelectCodeArticle = true,
                discount = 0.0,
                price = 0.0,
                count = 1.0,
                description = "",
                codeArticle = "",
            )
        }
    }

    fun resetData() {
        _uiState.update { currentState ->
            currentState.copy(
                discount = 0.0,
                price = 0.0,
                count = 1.0,
                description = "",
                codeArticle = "",
                showMessageSpecialPrices = false
            )
        }
    }

    fun showDateForUpdate(data: ArticleUiState) {
        val d = InterconexionUpdateArticle.data?.copy()
        InterconexionUpdateArticle.data = null
        if (d != null) {
            _uiState.update { currentState ->
                currentState.copy(
                    discount = d.DiscountPercent.toDouble(),
                    price = d.Price.toDouble(),
                    count = d.Quantity.toDouble(),
                    description = d.ItemDescription.toString(),
                    codeArticle = d.ItemCode.toString(),
                )
            }
        }
    }

    fun searchItemSinceInput() {
        val data = _uiState.value.inputSearch
        _uiState.value.ListItemsAux = _uiState.value.ListItems
        if(data.length >3){
            _uiState.value.ListItemsAux = mutableListOf()
            _uiState.value.ListItems.forEach {item ->
                if(item.ItemCode.contains(data) || item.itemName.contains(data)){
                    _uiState.value.ListItemsAux+= item
                }
            }
            _uiState.update { currentState ->
                currentState.copy(
                    ListItems = _uiState.value.ListItemsAux
                )
            }
        }else{
            _uiState.update { currentState ->
                currentState.copy(
                    ListItems = _uiState.value.ListItemsBackund
                )
            }
        }
    }
}