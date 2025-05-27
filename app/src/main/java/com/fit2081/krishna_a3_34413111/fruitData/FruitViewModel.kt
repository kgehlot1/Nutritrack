package com.fit2081.krishna_a3_34413111.fruitData

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FruitViewModel : ViewModel() {

    private val _fruit = MutableStateFlow<Fruit?>(null)
    val fruit: StateFlow<Fruit?> = _fruit

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchFruit(name: String) {
        viewModelScope.launch {
            try {
                val result = FruitDataInstance.api.getFruitByName(name)

                _fruit.value = result
                _error.value = null

            } catch (e: Exception) {
                Log.e("FruitViewModel", "Error fetching fruit: ${e.localizedMessage}")
                _fruit.value = null
                _error.value = "Error fetching fruit: ${e.localizedMessage}"
            }
        }
    }
}