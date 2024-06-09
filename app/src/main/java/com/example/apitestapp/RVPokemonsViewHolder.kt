package com.example.apitestapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apitestapp.model.Pokemon
import com.example.apitestapp.databinding.ItemPokemonBinding

class RVPokemonsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemPokemonBinding.bind(view)

    fun render(pokemon: Pokemon) {
        binding.tvPokemonNombre.text = pokemon.name
    }

}