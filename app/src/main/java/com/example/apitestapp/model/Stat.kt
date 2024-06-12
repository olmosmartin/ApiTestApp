package com.example.apitestapp.model

import com.google.gson.annotations.SerializedName

data class Stat (@SerializedName("base_stat") val baseStat: String, @SerializedName("stat") val stat: StatDetailed){
    override fun toString(): String {
        return "Pokemon(base_stat=$baseStat)(stat=$stat)"
    }
}