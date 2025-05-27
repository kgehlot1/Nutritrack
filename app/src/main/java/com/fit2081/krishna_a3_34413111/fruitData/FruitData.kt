package com.fit2081.krishna_a3_34413111.fruitData

data class Fruit(
    val name: String,
    val genus: String,
    val family: String,
    val order: String,
    val nutritions: Nutrition
)

data class Nutrition(
    val carbohydrates: Double,
    val protein: Double,
    val fat: Double,
    val calories: Int,
    val sugar: Double
)