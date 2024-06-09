package com.example.apitestapp.model

import com.google.gson.annotations.SerializedName

data class RegionPokemon (
    @SerializedName("main_region") val mainRegion: MainRegion,
    @SerializedName("pokemon_species") val pokemons: List<Pokemon>
){
    override fun toString(): String {
        return "RegionPokemon(mainRegion=$mainRegion)(pokemons=$pokemons)"
    }
}