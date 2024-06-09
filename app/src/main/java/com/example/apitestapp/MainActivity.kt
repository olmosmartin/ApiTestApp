package com.example.apitestapp

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.apitestapp.constantes.EnumRegion
import com.example.apitestapp.databinding.ActivityMainBinding
import com.example.apitestapp.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //nombre de la clase con activity al inicio y binding al final
    private lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //pongo esto acá para que solo se ejecute una vez en el render de la pantalla
        retrofit = getRetrofit()

        initUI()
    }

    private fun initUI() {
        binding.svBuscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //cuando se da enter
            override fun onQueryTextSubmit(query: String?): Boolean {
                //searchByName(query.orEmpty())
                searchByGeneration(query.orEmpty())
                return false
            }

            //cambia cada vez que vamos escribiendo
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = retrofit.create(ApiService::class.java).getPokemonByName(query)

            if (call.isSuccessful) {
                Log.i("POKEMON", "funciona")
                Log.i("POKEMON", call.body().toString())
            } else {
                Log.i("POKEMON", "error")
                val errorBody = call.errorBody()?.string()
                Log.e("POKEMON", "Error: ${call.code()} - $errorBody")
            }
        }
    }

    private fun searchByGeneration(query: String) {
        binding.pbLoading.isVisible = true
        //obtengo el id de la region segun su nombre
        val regionId = EnumRegion.map[query.lowercase()]
        if(regionId != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val call = retrofit.create(ApiService::class.java).getPokemonsByGeneration(regionId.toString())

                if (call.isSuccessful) {
                    Log.i("POKEMON", "funciona")
                    Log.i("POKEMON", "" + call.body().toString())
                    runOnUiThread {
                        binding.pbLoading.isVisible = false
                    }
                } else {
                    Log.i("POKEMON", "error")
                    val errorBody = call.errorBody()?.string()
                    Log.e("POKEMON", "Error: ${call.code()} - $errorBody")
                }
            }
        } else {
            Log.i("POKEMON", "región no válida")
            runOnUiThread {
                binding.pbLoading.isVisible = false
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}