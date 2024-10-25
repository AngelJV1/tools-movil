package com.example.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.baseexam2.R
import org.json.JSONException
import kotlin.random.Random

class FragmentFavorite : Fragment() {

    private lateinit var requestQueue: RequestQueue

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val etName: EditText = view.findViewById(R.id.etName)
        val btnPredict: Button = view.findViewById(R.id.btnPredictGender)
        val tvResult: TextView = view.findViewById(R.id.tvGenderResult)
        val ivAgeImage: ImageView = view.findViewById(R.id.ivAgeImage)

        // Inicializar la cola de peticiones
        requestQueue = Volley.newRequestQueue(context)

        // Acción al presionar el botón de predecir
        btnPredict.setOnClickListener {
            val name = etName.text.toString().trim()

            if (name.isNotEmpty()) {
                predictGenderAndAge(name, tvResult, ivAgeImage, view)
            } else {
                tvResult.text = "Por favor, introduce un nombre."
            }
        }

        return view
    }

    // Función para predecir género y generar edad aleatoria
    @SuppressLint("SetTextI18n")
    private fun predictGenderAndAge(name: String, tvResult: TextView, ivAgeImage: ImageView, view: View) {
        val url = "https://api.genderize.io/?name=$name"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val gender = response.getString("gender")
                    val randomAge = Random.nextInt(5, 100)  // Generar edad aleatoria entre 5 y 100
                    tvResult.text = "Género estimado: $gender\nEdad generada: $randomAge"

                    // Cambiar el color de fondo dependiendo del género
                    if (gender == "male") {
                        view.setBackgroundColor(Color.BLUE)
                    } else if (gender == "female") {
                        view.setBackgroundColor(Color.MAGENTA)
                    }

                    // Mostrar imagen y mensaje basado en la edad generada aleatoriamente
                    when {
                        randomAge < 18 -> {
                            tvResult.append(" (Joven)")
                            ivAgeImage.setImageResource(R.drawable.joven)  // Imagen de joven
                        }
                        randomAge in 18..59 -> {
                            tvResult.append(" (Adulto)")
                            ivAgeImage.setImageResource(R.drawable.adulto)  // Imagen de adulto
                        }
                        randomAge >= 60 -> {
                            tvResult.append(" (Anciano)")
                            ivAgeImage.setImageResource(R.drawable.anciano)  // Imagen de anciano
                        }
                    }

                    // Mostrar la imagen
                    ivAgeImage.visibility = View.VISIBLE

                } catch (e: JSONException) {
                    tvResult.text = "Error procesando la respuesta."
                }
            },
            {
                tvResult.text = "Error al obtener el género."
            })

        // Añadir la petición a la cola
        requestQueue.add(jsonObjectRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requestQueue.cancelAll(this)
    }
}



