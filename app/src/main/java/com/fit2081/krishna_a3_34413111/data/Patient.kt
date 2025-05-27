package com.fit2081.krishna_a3_34413111.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a patient entity in the database.
 *
 * This data class is annotated with `@Entity` to
 * indicate that it represents a table in the database.
 * The `tableName` property specifies the name
 * of the table as "PATIENT".
 */

@Entity(tableName = "PATIENT")
data class Patient(
        @PrimaryKey val userId: String,
        val phoneNumber: String,
        var name: String, // not in CSV
        var password: String, // not in CSV
        val sex: String,
        val HEIFAtotalscoreMale: Double?,
        val HEIFAtotalscoreFemale: Double?,
        val DiscretionaryHEIFAscoreMale: Double?,
        val DiscretionaryHEIFAscoreFemale: Double?,
        val Discretionaryservesize: Double?,
        val VegetablesHEIFAscoreMale: Double?,
        val VegetablesHEIFAscoreFemale: Double?,
        val Vegetableswithlegumesallocatedservesize: Double?,
        val LegumesallocatedVegetables: Double?,
        val Vegetablesvariationsscore: Double?,
        val VegetablesCruciferous: Double?,
        val VegetablesTuberandbulb: Double?,
        val VegetablesOther: Double?,
        val Legumes: Double?,
        val VegetablesGreen: Double?,
        val VegetablesRedandorange: Double?,
        val FruitHEIFAscoreMale: Double?,
        val FruitHEIFAscoreFemale: Double?,
        val Fruitservesize: Double?,
        val Fruitvariationsscore: Double?,
        val FruitPome: Double?,
        val FruitTropicalandsubtropical: Double?,
        val FruitBerry: Double?,
        val FruitStone: Double?,
        val FruitCitrus: Double?,
        val FruitOther: Double?,
        val GrainsandcerealsHEIFAscoreMale: Double?,
        val GrainsandcerealsHEIFAscoreFemale: Double?,
        val Grainsandcerealsservesize: Double?,
        val GrainsandcerealsNonwholegrains: Double?,
        val WholegrainsHEIFAscoreMale: Double?,
        val WholegrainsHEIFAscoreFemale: Double?,
        val Wholegrainsservesize: Double?,
        val MeatandalternativesHEIFAscoreMale: Double?,
        val MeatandalternativesHEIFAscoreFemale: Double?,
        val Meatandalternativeswithlegumesallocatedservesize: Double?,
        val LegumesallocatedMeatandalternatives: Double?,
        val DairyandalternativesHEIFAscoreMale: Double?,
        val DairyandalternativesHEIFAscoreFemale: Double?,
        val Dairyandalternativesservesize: Double?,
        val SodiumHEIFAscoreMale: Double?,
        val SodiumHEIFAscoreFemale: Double?,
        val Sodiummgmilligrams: Double?,
        val AlcoholHEIFAscoreMale: Double?,
        val AlcoholHEIFAscoreFemale: Double?,
        val Alcoholstandarddrinks: Double?,
        val WaterHEIFAscoreMale: Double?,
        val WaterHEIFAscoreFemale: Double?,
        val Water: Double?,
        val WaterTotalmL: Double?,
        val BeverageTotalmL: Double?,
        val SugarHEIFAscoreMale: Double?,
        val SugarHEIFAscoreFemale: Double?,
        val Sugar: Double?,
        val SaturatedFatHEIFAscoreMale: Double?,
        val SaturatedFatHEIFAscoreFemale: Double?,
        val SaturatedFat: Double?,
        val UnsaturatedFatHEIFAscoreMale: Double?,
        val UnsaturatedFatHEIFAscoreFemale: Double?,
        val UnsaturatedFatservesize: Double?
)
