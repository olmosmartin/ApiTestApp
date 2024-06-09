package com.example.apitestapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apitestapp.model.Pokemon

class RVPokemonsAdapter(
    var pokemones: List<Pokemon> = emptyList(),
    private val onPokemonSelected: (Int) -> Unit
) :
    RecyclerView.Adapter<RVPokemonsViewHolder>() {

    fun updateList(pokemones: List<Pokemon>) {
        this.pokemones = pokemones
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVPokemonsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return RVPokemonsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVPokemonsViewHolder, position: Int) {
        holder.render(pokemones[position])
        //uso itemview para acceder a la celda que se renderiza
        holder.itemView.setOnClickListener {
            onPokemonSelected(position)
        }
    }

    override fun getItemCount() = pokemones.size
}