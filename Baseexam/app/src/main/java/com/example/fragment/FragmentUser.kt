package com.example.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.baseexam2.R
import org.json.JSONException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class FragmentUser : Fragment() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val spinnerCountry = view.findViewById<Spinner>(R.id.spinnerCountry)
        val btnShowUniversities = view.findViewById<Button>(R.id.btnShowUniversities)
        val tvUniversities = view.findViewById<TextView>(R.id.tvUniversities)

        // Crear una lista de países
        val countries = listOf("United States", "Canada", "Dominican Republic", "Mexico", "Spain")

        // Configurar el adaptador del spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = adapter

        // Inicializar RequestQueue
        requestQueue = Volley.newRequestQueue(requireContext())

        btnShowUniversities.setOnClickListener {
            val selectedCountry = spinnerCountry.selectedItem.toString()

            // Llamar a la API para obtener universidades del país seleccionado
            fetchUniversities(selectedCountry, tvUniversities)
        }

        return view
    }

    private fun fetchUniversities(country: String, tvUniversities: TextView) {
        val url = "http://universities.hipolabs.com/search?country=$country"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    // Crear una lista de nombres de universidades
                    val universityList = mutableListOf<String>()

                    for (i in 0 until response.length()) {
                        val university = response.getJSONObject(i)
                        val universityName = university.getString("name")
                        universityList.add(universityName)
                    }

                    // Mostrar la lista en el TextView
                    tvUniversities.text = "Universidades en $country:\n\n" + universityList.joinToString("\n")

                } catch (e: JSONException) {
                    tvUniversities.text = "Error procesando la respuesta."
                }
            },
            {
                tvUniversities.text = "Error al obtener las universidades."
            })

        // Añadir la petición a la cola
        requestQueue.add(jsonArrayRequest)
    }
}


