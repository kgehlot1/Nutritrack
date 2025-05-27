package com.fit2081.krishna_a3_34413111.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// This interface defines the data access object (DAO) for the Patient entity.
@Dao
interface PatientDao {
    // Inserts a new patient into the database.
    //suspend is a coroutine function that can be paused and resumed at a later time.
    //suspend is used to indicate that the function will be called from a coroutine.
    @Insert
    suspend fun insert(patient: Patient)

    // Inserts a list of patients at once into the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(patients: List<Patient>)

    // Updates an existing patient in the database.
    @Update
    suspend fun update(patient: Patient)

    // Deletes a specific patient from the database.
    @Delete
    suspend fun delete(patient: Patient)

    // Deletes all patients from the database.
    @Query("DELETE FROM PATIENT")
    suspend fun deleteAllPatients()

    // Deletes a patient from the database by ID
    @Query("DELETE FROM PATIENT WHERE userId = :patientId")
    suspend fun deletePatientById(patientId: Int)

    // Retrieves the patient's userId
    @Query("SELECT * FROM patient WHERE userId = :userId LIMIT 1")
    suspend fun getPatientByUserId(userId: String): Patient?

    // Retrieves all patients from the database, ordered by their ID in ascending order.
    //The return type is a Flow, which is a data stream that can be observed for changes.
    @Query("SELECT * FROM PATIENT ORDER BY userId ASC")
    fun getAllPatients(): Flow<List<Patient>>
}

