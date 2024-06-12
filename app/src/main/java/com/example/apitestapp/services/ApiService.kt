package com.example.apitestapp.services

import com.example.apitestapp.model.Pokemon
import com.example.apitestapp.model.PokemonStats
import com.example.apitestapp.model.RegionPokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<PokemonStats>

    @GET("generation/{id}")
    suspend fun getPokemonsByGeneration(@Path("id") id: String): Response<RegionPokemon>

}