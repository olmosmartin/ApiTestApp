package com.example.apitestapp.vista.ajustes

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import com.example.apitestapp.R
import com.example.apitestapp.databinding.ActivityAjustesBinding
import androidx.fragment.app.commit
import com.example.apitestapp.constantes.Constantes
import com.example.apitestapp.datastore.SetingsDataStoreSingleton
import com.example.apitestapp.vista.componentes.OptionFragment
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_ICONO
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_ID
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_OPTION
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_SUBTITULO
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_SWITCH_VALUE
import com.example.apitestapp.vista.componentes.OptionFragment.Companion.ARG_TITULO
import com.example.apitestapp.vista.componentes.SliderFragment
import com.example.apitestapp.vista.componentes.SliderFragment.Companion.ARG_ICONO_SLIDER
import com.example.apitestapp.vista.componentes.SliderFragment.Companion.ARG_TITULO_SLIDER
import com.example.apitestapp.vista.componentes.SliderFragment.Companion.ARG_VOLUMEN_LVL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class AjustesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAjustesBinding //nombre de la clase con activity al inicio y binding al final
    private val settingsDataStore = SetingsDataStoreSingleton.getInstance(this)
    private var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //viewBinding
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //traigo datos de mi setting model y los paso al render fragment
        if (savedInstanceState == null){//esta comprobacion es para que no se renderice multiples veces si cambio la orientacion del celular entre horizontal y vertical
            CoroutineScope(Dispatchers.IO).launch {
                settingsDataStore.getSettings().filter{firstTime}.collect { settings ->
                    runOnUiThread {
                        if (settings.darkMode) {
                            enableDarkMode()
                        } else {
                            disableDarkMode()
                        }
                        renderFragments(settings.volumen, settings.bluetooth, settings.vibracion, settings.darkMode)
                        firstTime = !firstTime
                    }
                }
            }

        }

    }

    override fun onStart() {
        super.onStart()
        initUI()
    }

    private fun renderFragments(volumen: Int , bluetooth: Boolean, vibracion: Boolean, darkMode: Boolean) {
        //creo bundle para el fragment de modo oscuro
        val bundleDarkMode = bundleOf(
            ARG_ID to Constantes.DARK_MODE,
            ARG_TITULO to getString(R.string.moTitulo),
            ARG_SUBTITULO to getString(R.string.moSubtitulo),
            ARG_ICONO to R.drawable.ic_dark_mode,
            ARG_OPTION to true,
            ARG_SWITCH_VALUE to darkMode
        )
        //agrego fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<OptionFragment>(R.id.fcOptionDarkMode, args = bundleDarkMode)
        }

        //------------------------------------------------------
        //creo bundle para el fragment de bluetooth
        val bundleBluetooth = bundleOf(
            ARG_ID to Constantes.BLUETOOTH,
            ARG_TITULO to getString(R.string.bTitulo),
            ARG_SUBTITULO to getString(R.string.bSubtitulo),
            ARG_ICONO to R.drawable.ic_bluetooth,
            ARG_OPTION to true,
            ARG_SWITCH_VALUE to bluetooth
        )
        //agrego fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<OptionFragment>(R.id.fcOptionBluetooth, args = bundleBluetooth)
        }

        //------------------------------------------------------
        //creo bundle para el fragment de slider
        val bundleSlider = bundleOf(
            ARG_TITULO_SLIDER to getString(R.string.sTitulo),
            ARG_ICONO_SLIDER to R.drawable.ic_music,
            ARG_VOLUMEN_LVL to volumen.toFloat()
        )
        //agrego fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<SliderFragment>(R.id.fcSlider, args = bundleSlider)
        }

        //------------------------------------------------------
        //creo bundle para el fragment de vibracion
        val bundlevibracion = bundleOf(
            ARG_ID to Constantes.VIBRATION,
            ARG_TITULO to getString(R.string.vTitulo),
            ARG_ICONO to R.drawable.ic_contactless,
            ARG_OPTION to true,
            ARG_SWITCH_VALUE to vibracion
        )
        //agrego fragment
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<OptionFragment>(R.id.fcOptionVibracion, args = bundlevibracion)
        }
    }

    private fun initUI() {
        Log.i("tag", "render")

    }

    fun onSwitchChanged(checked: Boolean, id: String) {

        //podria hacerse sin el when porque al final todas las opciones hacen lo mismo
        //pero voy a dejarlo así para que sirva como ejemplo para acceder a las opciones desde el fragment
        when (id) {
            Constantes.DARK_MODE -> {
                Log.i("tag", "dark")
                CoroutineScope(Dispatchers.IO).launch {
                    settingsDataStore.saveSwitchValues(Constantes.DARK_MODE_DS, checked)
                }
            }
            Constantes.BLUETOOTH -> {
                Log.i("tag", "bluetooth")
                CoroutineScope(Dispatchers.IO).launch {
                    settingsDataStore.saveSwitchValues(Constantes.BLUETOOTH_DS, checked)
                }
            }
            Constantes.VIBRATION -> {
                Log.i("tag", "vibracion")
                CoroutineScope(Dispatchers.IO).launch {
                    settingsDataStore.saveSwitchValues(Constantes.VIBRATION_DS, checked)
                }
            }
            else -> {
                Log.i("tag", "error")
            }
        }
        Log.i("tag", "isSwitchOn: $checked")
    }

    fun onSliderValueChanged(value: Float) {
        Log.i("tag", "value: $value")
        //esto es asincrono con la ui porque estoy usando una corrutina para guardar en la bd
        CoroutineScope(Dispatchers.IO).launch {
            settingsDataStore.saveVolumen(value.toInt())
        }
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