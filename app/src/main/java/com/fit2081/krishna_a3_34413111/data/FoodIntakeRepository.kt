package com.fit2081.krishna_a3_34413111.data

class FoodIntakeRepository(private val foodIntakeDao: FoodIntakeDao) {

    suspend fun insert(foodIntake: FoodIntake) {
        foodIntakeDao.insert(foodIntake)
    }

    suspend fun update(foodIntake: FoodIntake) {
        foodIntakeDao.update(foodIntake)
    }

    suspend fun getByPatientId(patientId: String): List<FoodIntake> {
        return foodIntakeDao.getByPatientId(patientId)
    }

    suspend fun getAll(): List<FoodIntake> {
        return foodIntakeDao.getAll()
    }
}
