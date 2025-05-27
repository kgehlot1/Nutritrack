package com.fit2081.krishna_a3_34413111.fruitData
import retrofit2.http.GET
import retrofit2.http.Path

interface FruitDataInterface {
    @GET("fruit/{name}")
    suspend fun getFruitByName(@Path("name") name: String): Fruit
}

