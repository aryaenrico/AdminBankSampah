package com.aryaenrico.dynamicview.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityDashboardBinding
import com.aryaenrico.dynamicview.databinding.ActivityTambahSampahBinding

class TambahSampah : AppCompatActivity() {
    private lateinit var binding : ActivityTambahSampahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_sampah)
        supportActionBar?.hide()
        binding = ActivityTambahSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val kategorisampah = listOf("Kertas","Besi","Plastik")
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownitem,kategorisampah)
        binding.filledexposed.setAdapter(arrayAdapter)

        binding.findUser.setOnClickListener {
           // mendapat inputan user
            showToast( binding.filledexposed.text.toString())
        }

    }

    private fun showToast(message:String){
        Toast.makeText(this@TambahSampah ,message, Toast.LENGTH_SHORT).show()
    }



}