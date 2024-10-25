package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.baseexam2.R

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWeatherInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWeatherInfo : Fragment() {

    private lateinit var tvWeatherInfo: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weather_info, container, false)

        // Inicializar la vista
        tvWeatherInfo = view.findViewById(R.id.tvWeatherInfo)

        // Aquí iría la lógica para obtener la información del clima (con Retrofit o cualquier librería HTTP)
        // Para este ejemplo, simulamos los datos del clima
        updateWeatherInfo("Soleado, 27°C")

        return view
    }

    private fun updateWeatherInfo(weatherInfo: String) {
        // Actualiza la información del clima
        tvWeatherInfo.text = weatherInfo
    }
}
