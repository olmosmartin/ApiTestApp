package com.example.apitestapp.model

import com.google.gson.annotations.SerializedName

data class Pokemon (@SerializedName("name") val name: String){
    override fun toString(): String {
        return "Pokemon(name=$name)"
    }
}