package com.example.apitestapp.model

import com.google.gson.annotations.SerializedName

class StatDetailed (@SerializedName("name") val name: String) {
    override fun toString(): String {
        return "Pokemon(name=$name)"
    }
}