package com.example.apitestapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitPokeApi: Retrofit? = null

    fun getRetrofitPokeapi(): Retrofit {
        if (retrofitPokeApi == null) {
            retrofitPokeApi = Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitPokeApi!!
    }

}