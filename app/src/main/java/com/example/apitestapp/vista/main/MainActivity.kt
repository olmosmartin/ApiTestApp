package com.example.apitestapp.vista.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.databinding.ActivityMainBinding
import com.example.apitestapp.datastore.SetingsDataStoreSingleton
import com.example.apitestapp.services.ApiService
import com.example.apitestapp.services.RetrofitClient
import com.example.apitestapp.vista.ajustes.AjustesActivity
import com.example.apitestapp.vista.detalle.DetalleActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //nombre de la clase con activity al inicio y binding al final
    private lateinit var retrofit: Retrofit
    private lateinit var pokemonesAdapter: RVPokemonsAdapter
    private val settingsDataStore = SetingsDataStoreSingleton.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){//esta comprobacion es para que no se renderice multiples veces si cambio la orientacion del celular entre horizontal y vertical
            CoroutineScope(Dispatchers.IO).launch {
                settingsDataStore.getSettings().collect { settings ->
                    runOnUiThread {
                        if (settings.darkMode) {
                            enableDarkMode()
                        } else {
                            disableDarkMode()
                        }
                    }
                }
            }
        }

        //pongo esto acá para que solo se ejecute una vez en el render de la pantalla
        retrofit = RetrofitClient.getRetrofitPokeapi()

        initUI()
    }

    private fun initUI() {
        binding.svBuscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //cuando se da enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByGeneration(query.orEmpty())
                return false
            }

            //cambia cada vez que vamos escribiendo
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        pokemonesAdapter = RVPokemonsAdapter(emptyList(), ::onPokemonSelected)
        binding.rvPokemonsList.adapter = pokemonesAdapter

        binding.ivSettings.setOnClickListener {
            navegarAjustes()
        }
    }

    private fun searchByGeneration(query: String) {
        binding.pbLoading.isVisible = true
        //obtengo el id de la region segun su nombre
        val regionId = Constantes.map[query.lowercase()]
        if(regionId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(ApiService::class.java).getPokemonsByGeneration(regionId.toString())

                if (call.isSuccessful) {
                    runOnUiThread {
                        pokemonesAdapter.updateList(call.body()?.pokemons ?: emptyList())
                        binding.pbLoading.isVisible = false
                    }
                } else {
                    val errorBody = call.errorBody()?.string()
                    Log.e("POKEMON", "Error: ${call.code()} - $errorBody")
                }
            }
        } else {
            runOnUiThread {
                binding.pbLoading.isVisible = false
            }
        }
    }

    private fun onPokemonSelected(name: String) {
        navegarPrimerBoton(name)
    }

    private fun navegarPrimerBoton(pokemonName: String) {
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra(Constantes.POKEMON_NAME, pokemonName)
        startActivity(intent)
    }

    private fun navegarAjustes() {
        val intent = Intent(this, AjustesActivity::class.java)
        startActivity(intent)
    }
    fun enableDarkMode () {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    fun disableDarkMode () {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}