package com.example.apitestapp.vista.detalle

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.databinding.ActivityDetalleBinding
import com.example.apitestapp.model.PokemonStats
import com.example.apitestapp.services.ApiService
import com.example.apitestapp.services.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding //nombre de la clase con activity al inicio y binding al final

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pokemonName = intent.extras?.getString(Constantes.POKEMON_NAME) ?: ""
        searchByName(pokemonName)
    }

    private fun createUI(pokemonName: PokemonStats) {
        binding.tvPokemonName.text = pokemonName.name
        Picasso.get().load(Constantes.getPokemonImageUrl(pokemonName.name)).into(binding.ivPokemonDetalle)

        binding.hp.layoutParams = renderParams(binding.hp.layoutParams, pokemonName, 0)
        binding.attack.layoutParams = renderParams(binding.attack.layoutParams, pokemonName, 1)
        binding.defense.layoutParams = renderParams(binding.defense.layoutParams, pokemonName, 2)
        binding.specialattack.layoutParams = renderParams(binding.specialattack.layoutParams, pokemonName, 3)
        binding.specialdefense.layoutParams = renderParams(binding.specialdefense.layoutParams, pokemonName, 4)
        binding.speed.layoutParams = renderParams(binding.speed.layoutParams, pokemonName, 5)

    }

    private fun renderParams(layoutParams: ViewGroup.LayoutParams, pokemonName: PokemonStats, position: Int): ViewGroup.LayoutParams {
        layoutParams.height = pokemonName.stats[position].baseStat.toInt()
        return layoutParams
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.getRetrofitPokeapi().create(ApiService::class.java).getPokemonByName(query)

            if (call.isSuccessful) {
                Log.i("POKEMON", "funciona")
                Log.i("POKEMON", call.body().toString())
                if(call.body() != null){
                    runOnUiThread{createUI(call.body()!!)}
                } else {
                    Log.i("POKEMON", "null")
                }

            } else {
                Log.i("POKEMON", "error")
                val errorBody = call.errorBody()?.string()
                Log.e("POKEMON", "Error: ${call.code()} - $errorBody")
            }
        }
    }
}