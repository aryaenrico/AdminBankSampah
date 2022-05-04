package com.aryaenrico.dynamicview.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityDetailPengajuanBinding


class DetailPengajuan : AppCompatActivity() {

    private lateinit var binding : ActivityDetailPengajuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pengajuan)
        supportActionBar?.hide()
        binding = ActivityDetailPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val status = listOf("Disetujui","Ditolak")
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdownitem,status)
        binding.dropDownVerifikasi.setAdapter(arrayAdapter)





    }
}