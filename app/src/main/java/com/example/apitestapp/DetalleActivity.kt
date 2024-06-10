package com.example.apitestapp

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.databinding.ActivityDetalleBinding
import com.example.apitestapp.databinding.ActivityMainBinding

class DetalleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleBinding //nombre de la clase con activity al inicio y binding al final

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pokemonName = intent.extras?.getString(Constantes.POKEMON_NAME) ?: ""
        initUI(pokemonName)
    }

    private fun initUI(pokemonName: String) {
        binding.tvPokemonName.text = pokemonName
    }
}