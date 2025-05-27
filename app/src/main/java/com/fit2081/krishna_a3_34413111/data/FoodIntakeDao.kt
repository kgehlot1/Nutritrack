package com.fit2081.krishna_a3_34413111.data

import androidx.room.*

@Dao
interface FoodIntakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodIntake: FoodIntake)

    @Update
    suspend fun update(foodIntake: FoodIntake)

    @Delete
    suspend fun delete(foodIntake: FoodIntake)

    @Query("SELECT * FROM `FOOD_INTAKE` WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: String): List<FoodIntake>

    @Query("SELECT * FROM `FOOD_INTAKE`")
    suspend fun getAll(): List<FoodIntake>


}
