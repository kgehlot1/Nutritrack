package com.fit2081.krishna_a3_34413111.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userId"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.CASCADE
    )],
    tableName = "FOOD_INTAKE"
)
data class FoodIntake(
    @PrimaryKey val patientId: String,
    var fruits: Boolean,
    var vegetables: Boolean,
    var grains: Boolean,
    var redMeat: Boolean,
    var seafood: Boolean,
    var poultry: Boolean,
    var fish: Boolean,
    var eggs: Boolean,
    var nutsSeeds: Boolean,
    var persona: String,
    var bigMealTime: String,
    var sleepTime: String,
    var wakeTime: String
)