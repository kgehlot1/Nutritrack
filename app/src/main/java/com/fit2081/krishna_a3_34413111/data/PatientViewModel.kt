package com.fit2081.krishna_a3_34413111.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.krishna_a3_34413111.data.Patient
import com.fit2081.krishna_a3_34413111.data.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch

class PatientViewModel(context: Context) : ViewModel() {


    //creates a repo object that will be used to interact with the database
    private val patientRepo = PatientRepository(context)
    //gets all the patients stored in the repo
    val allPatients: Flow<List<Patient>> = patientRepo.getAllPatients()

    //inserts a patient into the repo
    fun insert(patient: Patient) = viewModelScope.launch {
        patientRepo.insert(patient)
    }
    //inserts a list/group patients into the repo
    fun insertAll(patients: List<Patient>) = viewModelScope.launch {
        patientRepo.insertAll(patients)
    }
    //deletes a patient from the repo
    fun delete(patient: Patient) = viewModelScope.launch {
        patientRepo.delete(patient)
    }
    //updates a patient in the repo
    fun update(patient: Patient) = viewModelScope.launch {
        patientRepo.update(patient)
    }

    //deletes a patient by its id
    fun deletePatientById(patientId: Int) = viewModelScope.launch {
        patientRepo.deletePatientById(patientId)
    }
    //deletes all the patients in the repo
    fun deleteAllPatients() = viewModelScope.launch {
        patientRepo.deleteAllPatients()
    }

    fun isFirstLaunch(context: Context): Boolean {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return prefs.getBoolean("first_launch", true)
    }

    fun markFirstLaunchDone(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("first_launch", false).apply()
    }

    fun validatePatient(userId: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val patient = patientRepo.getPatientByUserId(userId)
            onResult(patient?.password == password)
        }
    }

    private val _patientName = mutableStateOf<String?>(null)
    val patientName: State<String?> = _patientName

    fun fetchPatientName(userId: String) {
        viewModelScope.launch {
            try {
                val patient = patientRepo.getPatientByUserId(userId)
                _patientName.value = patient?.name
            } catch (e: Exception) {
                // Handle error (e.g., log or show fallback)
                _patientName.value = null
            }
        }
    }

    private val _totalHeifaScore = mutableStateOf<Double?>(null)
    val totalHeifaScore: State<Double?> = _totalHeifaScore

    // Fetch the total HEIFA score based on sex
    fun fetchTotalHeifaScore(userId: String) {
        viewModelScope.launch {
            try {
                val patient = patientRepo.getPatientByUserId(userId)
                _totalHeifaScore.value = when (patient?.sex?.lowercase()) {
                    "male" -> patient.HEIFAtotalscoreMale
                    "female" -> patient.HEIFAtotalscoreFemale
                    else -> null
                }
            } catch (e: Exception) {
                _totalHeifaScore.value = null
            }
        }
    }

    private val _patientPhoneNo = mutableStateOf<String?>(null)
    val patientPhoneNo: State<String?> = _patientPhoneNo

    // Fetch the patient's phone number
    fun fetchPatientPhoneNo(userId: String) {
        viewModelScope.launch {
            try {
                val patient = patientRepo.getPatientByUserId(userId)
                _patientPhoneNo.value = patient?.phoneNumber
            } catch (e: Exception) {
                // Handle error (e.g., log or show fallback)
                _patientPhoneNo.value = null
            }
        }
    }

    private val _averageMaleScore = mutableStateOf<Double?>(null)
    val averageMaleScore: State<Double?> = _averageMaleScore

    private val _averageFemaleScore = mutableStateOf<Double?>(null)
    val averageFemaleScore: State<Double?> = _averageFemaleScore

    fun observeAverageHeifaScores() {
        viewModelScope.launch {
            patientRepo.getAllPatients().collect { patients ->
                val maleScores = patients
                    .filter { it.sex.equals("male", ignoreCase = true) }
                    .mapNotNull { it.HEIFAtotalscoreMale }

                val femaleScores = patients
                    .filter { it.sex.equals("female", ignoreCase = true) }
                    .mapNotNull { it.HEIFAtotalscoreFemale }

                _averageMaleScore.value = if (maleScores.isNotEmpty()) maleScores.average() else null
                _averageFemaleScore.value = if (femaleScores.isNotEmpty()) femaleScores.average() else null
            }
        }
    }

    private val _selectedPatient = mutableStateOf<Patient?>(null)
    val selectedPatient: State<Patient?> = _selectedPatient

    fun loadPatient(userId: String) {
        viewModelScope.launch {
            val patient = patientRepo.getPatientByUserId(userId)
            _selectedPatient.value = patient
        }
    }

    suspend fun getPatientByUserId(userId: String): Patient? {
        return patientRepo.getPatientByUserId(userId)
    }

    //a view model factory that sets the context for the viewmodel
    //The ViewModelProvider.Factory interface is used to create view models.
    class PatientViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            PatientViewModel(context) as T
    }
}