package com.example.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;


public class AdapterViewPager extends FragmentStateAdapter {
    ArrayList<Fragment> arr;

    // Constructor que recibe FragmentActivity y la lista de fragmentos
    public AdapterViewPager(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> arr) {
        super(fragmentActivity);
        this.arr = arr; // Inicialización correcta de la lista de fragmentos
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arr.get(position); // Devuelve el fragmento correspondiente a la posición
    }

    @Override
    public int getItemCount() {
        return arr.size(); // Número de fragmentos en la lista
    }
}

