package com.example.apitestapp.model

import com.google.gson.annotations.SerializedName

data class MainRegion (@SerializedName("name") val name: String){
    override fun toString(): String {
        return "Region(name=$name)"
    }
}