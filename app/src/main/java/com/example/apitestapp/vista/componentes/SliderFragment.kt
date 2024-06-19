package com.example.apitestapp.vista.componentes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apitestapp.R
import com.example.apitestapp.databinding.FragmentOptionBinding
import com.example.apitestapp.databinding.FragmentSliderBinding
import com.example.apitestapp.vista.ajustes.AjustesActivity

class SliderFragment : Fragment() {
    private var titulo: String? = null
    private var icono: Int? = null

    private var _binding: FragmentSliderBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titulo = it.getString(OptionFragment.ARG_TITULO)
            icono = it.getInt(OptionFragment.ARG_ICONO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSliderBinding.inflate(inflater, container, false)
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
        binding.imIcono.setImageResource(icono ?: R.drawable.ic_not_found)

        // Establece el listener para el Slider
        binding.rsSlider.addOnChangeListener { slider, value, fromUser ->
            // Aquí puedes notificar a la Activity del cambio de valor en el Slider
            (activity as? AjustesActivity)?.onSliderValueChanged(value)
        }
    }

    companion object {
        const val ARG_TITULO_SLIDER = "titulo"
        const val ARG_ICONO_SLIDER = "icono"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITULO_SLIDER, param1)
                    putString(ARG_ICONO_SLIDER, param2)
                }
            }
    }
}