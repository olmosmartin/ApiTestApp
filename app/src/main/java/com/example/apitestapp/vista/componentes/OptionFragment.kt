package com.example.apitestapp.vista.componentes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import com.example.apitestapp.R
import com.example.apitestapp.databinding.FragmentOptionBinding
import com.example.apitestapp.vista.ajustes.AjustesActivity

class OptionFragment : Fragment() {

    private var id: String? = null
    private var titulo: String? = null
    private var subtitulo: String? = null
    private var icono: Int? = null
    private var opcion: Boolean? = null

    private var _binding: FragmentOptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
            titulo = it.getString(ARG_TITULO)
            subtitulo = it.getString(ARG_SUBTITULO)
            icono = it.getInt(ARG_ICONO)
            opcion = it.getBoolean(ARG_OPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        binding.tvTitulo.text = titulo
        binding.tvSubtitulo.text = subtitulo
        binding.imIcono.setImageResource(icono ?: R.drawable.ic_not_found)
        binding.llOption.visibility = if (opcion == true) View.VISIBLE else View.GONE

        // Establece el listener para el Switch si la opcion de mostrar switch esta en true
        if (opcion == true) {
            binding.swSwitch.setOnCheckedChangeListener { _, isChecked ->
                // Aquí puedes notificar a la Activity del cambio de estado
                (activity as? AjustesActivity)?.onSwitchChanged(isChecked, id?: "")
            }
        }
    }

    companion object {
        const val ARG_ID = "switchID"
        const val ARG_TITULO = "titulo"
        const val ARG_SUBTITULO = "subtitulo"
        const val ARG_ICONO = "icono"
        const val ARG_OPTION = "opcion"

        //antes se usaba el newInstance para pasar parametros
        //pero ahora se usa bundle, igual lo voy a dejar como ejemplo
        //porque en muchos proyectos aun se usa
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            OptionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITULO, param1)
                    putString(ARG_SUBTITULO, param2)
                    putString(ARG_OPTION, param3)
                }
            }
    }

}