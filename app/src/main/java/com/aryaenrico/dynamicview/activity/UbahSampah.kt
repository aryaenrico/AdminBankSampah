package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityUbahHargaBinding
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.viewmodel.UbahSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryTambahSampah
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahHargaSampah

class UbahSampah : AppCompatActivity() {
    private lateinit var binding: ActivityUbahHargaBinding
    private lateinit var model: UbahSampahViewModel
    private var dataSampah = ArrayList<String>()
    private var mapSampah = HashMap<String, Sampah>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUbahHargaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}