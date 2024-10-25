package com.example.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.baseexam2.R
import org.json.JSONException
import kotlin.random.Random
class NewsFragment : Fragment() {

    private lateinit var newsContainer: LinearLayout
    private lateinit var logoImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        newsContainer = view.findViewById(R.id.newsContainer)
        logoImageView = view.findViewById(R.id.ivLogo)

        // Cargar logo de la página web (coloca la URL de la imagen de la página que desees)
        Glide.with(this).load("https://techcrunch.com/wp-json/wp/v2/posts").into(logoImageView)

        // Cargar las últimas 3 noticias de la API
        fetchLatestNews()

        return view
    }

    private fun fetchLatestNews() {
        val url = "https://techcrunch.com/wp-json/wp/v2/posts?per_page=3"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    for (i in 0 until response.length()) {
                        val post = response.getJSONObject(i)

                        val title = post.getJSONObject("title").getString("rendered")
                        val excerpt = post.getJSONObject("excerpt").getString("rendered")
                        val link = post.getString("link")

                        // Añadir una nueva vista para cada noticia
                        addNewsItem(title, excerpt, link)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error al obtener las noticias", Toast.LENGTH_SHORT).show()
            })

        // Añadir la petición a la cola de Volley
        Volley.newRequestQueue(requireContext()).add(jsonArrayRequest)
    }

    @SuppressLint("MissingInflatedId")
    private fun addNewsItem(title: String, excerpt: String, link: String) {
        val newsItemView = LayoutInflater.from(context).inflate(R.layout.item_news, newsContainer, false)

        val tvTitle = newsItemView.findViewById<TextView>(R.id.tvTitle)
        val tvExcerpt = newsItemView.findViewById<TextView>(R.id.tvExcerpt)
        val btnLink = newsItemView.findViewById<Button>(R.id.btnLink)

        tvTitle.text = Html.fromHtml(title)  // Título
        tvExcerpt.text = Html.fromHtml(excerpt)  // Resumen o extracto
        btnLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)  // Abre el enlace en el navegador
        }

        newsContainer.addView(newsItemView)  // Añadir la noticia al contenedor
    }
}
