package com.fit2081.krishna_a3_34413111.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FoodIntakeViewModel(private val repository: FoodIntakeRepository) : ViewModel() {

    fun insert(foodIntake: FoodIntake) = viewModelScope.launch {
        repository.insert(foodIntake)
    }

    suspend fun getByPatientId(patientId: String): List<FoodIntake> {
        return repository.getByPatientId(patientId)
    }

    fun saveFoodIntake(
        patientId: String,
        selectedFoods: Map<String, Boolean>,
        persona: String,
        bigMealTime: String,
        sleepTime: String,
        wakeTime: String
    ) {
        viewModelScope.launch {
            val saveFoodPrefs = FoodIntake(
                patientId = patientId,
                fruits = selectedFoods["Fruits"] ?: false,
                vegetables = selectedFoods["Vegetables"] ?: false,
                grains = selectedFoods["Grains"] ?: false,
                redMeat = selectedFoods["Red Meat"] ?: false,
                seafood = selectedFoods["Seafood"] ?: false,
                poultry = selectedFoods["Poultry"] ?: false,
                fish = selectedFoods["Fish"] ?: false,
                eggs = selectedFoods["Eggs"] ?: false,
                nutsSeeds = selectedFoods["Nuts/Seeds"] ?: false,
                persona = persona,
                bigMealTime = bigMealTime,
                sleepTime = sleepTime,
                wakeTime = wakeTime
            )

            repository.insert(saveFoodPrefs)
        }
    }

    private val _latestFoodIntake = MutableStateFlow<FoodIntake?>(null)
    val latestFoodIntake: StateFlow<FoodIntake?> = _latestFoodIntake.asStateFlow()

    fun loadSavedFoodIntake(patientId: String) {
        viewModelScope.launch {
            val savedIntakes = repository.getByPatientId(patientId)
            if (savedIntakes.isNotEmpty()) {
                // Assuming you want the latest one:
                _latestFoodIntake.value = savedIntakes.last()
            }
        }
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val db = NutritrackDatabase.getDatabase(context)
            val repository = FoodIntakeRepository(db.foodIntakeDao())
            @Suppress("UNCHECKED_CAST")
            return FoodIntakeViewModel(repository) as T
        }
    }
}
