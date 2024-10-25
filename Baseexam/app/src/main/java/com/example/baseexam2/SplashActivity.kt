package com.example.baseexam2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        // Esperar 3 segundos antes de ir a la siguiente actividad (puedes cambiar el tiempo)
        Handler(Looper.getMainLooper()).postDelayed({
            // Intent para iniciar la actividad principal (MainActivity)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la SplashActivity para que no pueda volver con el botón atrás
        }, 3000) // Tiempo en milisegundos (3000 ms = 3 segundos)
    }
}
