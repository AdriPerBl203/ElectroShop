package com.AG_AP.electroshop.viewModels.Items

import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.realm.ItemCRUD
import com.AG_AP.electroshop.realm.models.Item
import com.AG_AP.electroshop.uiState.Items.ListItemsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ListItemsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ListItemsUiState())
    val uiState: StateFlow<ListItemsUiState> = _uiState.asStateFlow()

    init {
        ItemCRUD.getAllItems { lista ->
            _uiState.update {
                currentState -> currentState.copy(
                    ListItems = lista as List<Item?>
                )
            }

        }
    }

}