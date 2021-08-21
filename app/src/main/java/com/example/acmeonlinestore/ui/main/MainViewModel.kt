package com.example.acmeonlinestore.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acmeonlinestore.model.Product
import com.example.acmeonlinestore.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = MainRepository()

    private var _products = MutableStateFlow(repository.products)

    val products: StateFlow<List<Product>?> = _products

    var currentSortRule: MutableStateFlow<Int> = MutableStateFlow(0)


    init {
        viewModelScope.launch {
            currentSortRule.collect {

                val rule = when (it) {
                    0 -> SortRule.Name
                    else -> SortRule.Price
                }

                _sortBy(rule, products.value)

            }
        }
    }

    private fun _sortBy(rule: SortRule, list: List<Product>?) {
        when (rule) {
            is SortRule.Name -> {
                _products.value = list?.sortedBy {
                    it.name
                }
            }
            is SortRule.Price -> {
                _products.value = list?.sortedBy {
                    it.price
                }
            }
        }
    }

    fun filterProduct(searchArg: String) {
        if (searchArg.isEmpty()) {
            _products.value = repository.products
        } else {
            _products.value = repository.products?.filter {
                it.name?.lowercase()?.startsWith(searchArg.lowercase()) == true
            }
        }
    }
}

sealed class SortRule {
    object Name : SortRule()
    object Price : SortRule()

}