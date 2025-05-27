package com.fit2081.krishna_a3_34413111.fruitData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

object FruitDataInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.fruityvice.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: FruitDataInterface by lazy {
        retrofit.create(FruitDataInterface::class.java)
    }
}

