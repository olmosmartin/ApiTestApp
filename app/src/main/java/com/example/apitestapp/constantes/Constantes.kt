package com.example.apitestapp.constantes

class Constantes {
    companion object {
        val map = mapOf(
            "kanto" to 1,
            "johto" to 2,
            "hoenn" to 3,
            "sinnoh" to 4,
            "unova" to 5,
            "kalos" to 6,
            "alola" to 7,
            "galar" to 8
        )
        fun getPokemonImageUrl(pokemonName: String): String {
            return "https://img.pokemondb.net/artwork/$pokemonName.jpg"
        }
        const val POKEMON_NAME = "POKEMON_NAME"
    }
}