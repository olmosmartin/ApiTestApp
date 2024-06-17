package com.example.apitestapp.vista.ajustes

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import com.example.apitestapp.R
import com.example.apitestapp.databinding.ActivityAjustesBinding
import androidx.fragment.app.commit
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.databinding.ActivityMainBinding
import com.example.apitestapp.vista.componentes.OptionFragment
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_ICONO
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_ID
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_OPTION
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_SUBTITULO
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_TITULO

class AjustesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAjustesBinding //nombre de la clase con activity al inicio y binding al final

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            renderFragments()
        }

    }

    override fun onStart() {
        super.onStart()
        initUI()
    }

    private fun renderFragments() {
        //creo bundle para el fragment de modo oscuro
        val bundleDarkMode = bundleOf(
            ARG_ID to Constantes.DARK_MODE,
            ARG_TITULO to getString(R.string.moTitulo),
            ARG_SUBTITULO to getString(R.string.moSubtitulo),
            ARG_ICONO to R.drawable.ic_dark_mode,
            ARG_OPTION to true
        )
        //agrego fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<OptionFragment>(R.id.fcOptionDarkMode, args = bundleDarkMode)
        }

        //creo bundle para el fragment de bluetooth
        val bundleBluetooth = bundleOf(
            ARG_ID to Constantes.BLUETOOTH,
            ARG_TITULO to getString(R.string.bTitulo),
            ARG_SUBTITULO to getString(R.string.bSubtitulo),
            ARG_ICONO to R.drawable.ic_bluetooth,
            ARG_OPTION to true
        )
        //agrego fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<OptionFragment>(R.id.fcOptionBluetooth, args = bundleBluetooth)
        }
    }

    private fun initUI() {
        Log.i("tag", "render")

    }

    fun onSwitchChanged(checked: Boolean, id: String) {
        when (id) {
            Constantes.DARK_MODE -> {
                Log.i("tag", "dark")
            }
            Constantes.BLUETOOTH -> {
                Log.i("tag", "bluetooth")
            }
            else -> {
                Log.i("tag", "error")
            }
        }
        Log.i("tag", "isSwitchOn: $checked")
    }
}