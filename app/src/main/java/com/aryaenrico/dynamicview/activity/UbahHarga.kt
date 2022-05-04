package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityUbahHargaBinding

class UbahHarga : AppCompatActivity() {
    private lateinit var binding : ActivityUbahHargaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_harga)
        binding = ActivityUbahHargaBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        val kategorisampah = listOf("Kertas","Besi","Plastik")
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownitem,kategorisampah)
        binding.namaSampah.setAdapter(arrayAdapter)


    }
}