package com.example.baseexam2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.adapter.AdapterViewPager
import com.example.fragment.FragmentFavorite
import com.example.fragment.FragmentHome
import com.example.fragment.FragmentUser
import com.example.fragment.FragmentWeatherInfo
import com.example.fragment.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var pagerMain: ViewPager2
    val fragmentArrayList = ArrayList<Fragment>()
    lateinit var bottonNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Inicializa el ViewPager
        pagerMain = findViewById(R.id.pagerMain)
        bottonNav = findViewById(R.id.bottomNav)

        // Añade fragmentos a la lista
        fragmentArrayList.add(FragmentHome())
        fragmentArrayList.add(FragmentFavorite())
        fragmentArrayList.add(FragmentUser())
        fragmentArrayList.add(NewsFragment())

        // Instancia el AdapterViewPager correctamente
        val adapterViewPager = AdapterViewPager(this, fragmentArrayList)

        // Configura el ViewPager con el adaptador
        pagerMain.adapter = adapterViewPager

        // Registra el callback de cambio de página
        pagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                // Actualiza el item seleccionado en el BottomNavigationView
                when (position) {
                    0 -> bottonNav.selectedItemId = R.id.itHome
                    1 -> bottonNav.selectedItemId = R.id.itFavorite
                    2 -> bottonNav.selectedItemId = R.id.itUser
                    3 -> bottonNav.selectedItemId = R.id.itNews
                }
            }
        })

        bottonNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itHome -> pagerMain.setCurrentItem(0)
                R.id.itFavorite -> pagerMain.setCurrentItem(1)
                R.id.itUser -> pagerMain.setCurrentItem(2)
                R.id.itNews -> pagerMain.setCurrentItem(3)
            }
            true
        }

        // Listener para ajustar insets de sistema (barra de estado, etc.)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar el FragmentWeatherInfo para que siempre esté visible
        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.weatherInfoContainer, FragmentWeatherInfo())
            fragmentTransaction.commit()
        }
    }
}

