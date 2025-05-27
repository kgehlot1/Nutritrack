package com.fit2081.krishna_a3_34413111.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class PatientRepository {

    // Property to hold the PatientDao instance.
    var patientDao: PatientDao
    // Constructor to initialize the PatientDao.
    constructor(context: Context) {
        // Get the PatientDao instance from the NutritrackDatabase.
        patientDao = NutritrackDatabase.getDatabase(context).patientDao()
    }
    // Function to insert a patient into the database.
    suspend fun insert(patient: Patient) {
        // Call the insert function from the PatientDao.
        patientDao.insert(patient)
    }
    // Function to insert a list/group of patient into the database.
    suspend fun insertAll(patients: List<Patient>) {
        // Call the insert function from the PatientDao.
        patientDao.insertAll(patients)
    }
    // Function to delete a patient from the database.
    suspend fun delete(patient: Patient) {
        // Call the delete function from the PatientDao.
        patientDao.delete(patient)
    }
    // Function to update a patient in the database.
    suspend fun update(patient: Patient) {
        // Call the update function from the PatientDao.
        patientDao.update(patient)
    }
    // Function to delete a patient by ID from the database.
    suspend fun deletePatientById(id: Int) {
        patientDao.deletePatientById(id)
    }

    // Function to delete all patients from the database.
    suspend fun deleteAllPatients() {
        // Call the deleteAllPatients function from the PatientDao.
        patientDao.deleteAllPatients()
    }

    // Function to get all patients from the database as a Flow.
    fun getAllPatients():   Flow<List<Patient>> = patientDao.getAllPatients()

    // Function to validate a patient by userId
    suspend fun getPatientByUserId(userId: String): Patient? {
        return patientDao.getPatientByUserId(userId)
    }
}

