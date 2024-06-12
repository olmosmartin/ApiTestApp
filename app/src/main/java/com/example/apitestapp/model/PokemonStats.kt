package com.example.apitestapp.model

import com.google.gson.annotations.SerializedName

data class PokemonStats (@SerializedName("name") val name: String, @SerializedName("stats") val stats: List<Stat>){
    override fun toString(): String {
        return "Pokemon(name=$name)(stats=$stats)"
    }
}