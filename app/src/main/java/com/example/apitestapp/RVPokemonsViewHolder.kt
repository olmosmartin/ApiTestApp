package com.example.apitestapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.model.Pokemon
import com.example.apitestapp.databinding.ItemPokemonBinding
import com.squareup.picasso.Picasso

class RVPokemonsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemPokemonBinding.bind(view)

    fun render(pokemon: Pokemon) {
        binding.tvPokemonNombre.text = pokemon.name
        //pongo la imagen a partir de una url q me devuelve Constantes.getPokemonImageUrl creado en constantes
        Picasso.get().load(Constantes.getPokemonImageUrl(pokemon.name)).into(binding.imPokemon)
    }

}