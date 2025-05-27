package com.fit2081.krishna_a3_34413111.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This is the database class for the application. It is a Room database.
 * It contains two entity: [Patient] and [FoodIntake].
 * The version is 5 and exportSchema is false.
 */
@Database(entities = [Patient::class, FoodIntake::class], version = 5, exportSchema = false)
// this is a room database

abstract class NutritrackDatabase: RoomDatabase() {

    /**
     * Returns the [PatientDao] object.
     * This is an abstract function that is implemented by Room.
     */
    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao

    companion object {
        /**
         * This is a volatile variable that holds the database instance.
         * It is volatile so that it is immediately visible to all threads.
         */
        @Volatile
        private var Instance: NutritrackDatabase? = null

        /**
         * Returns the database instance.
         * If the instance is null, it creates a new database instance.
         * @param context The context of the application.
         */
        fun getDatabase(context: Context): NutritrackDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            //synchronized means that only one thread can access this code at a time.
            //nutritrack_database is the name of the database.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NutritrackDatabase::class.java, "nutritrack_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build().also { Instance = it }
            }
        }
    }
}
