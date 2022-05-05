package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.adapter.DaftarAjuanAdapter
import com.aryaenrico.dynamicview.databinding.ActivityTambahKategoriBinding
import com.aryaenrico.dynamicview.databinding.ActivityVerifikasiBinding
import com.aryaenrico.dynamicview.model.DaftarAjuan
import com.aryaenrico.dynamicview.viewmodel.DaftarAjuanViewModel
import com.aryaenrico.dynamicview.viewmodel.InputKategoriViewmodel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryDaftarAjuan
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryInputKategori

class Verifikasi : AppCompatActivity() {
    private lateinit var model: DaftarAjuanViewModel
    private lateinit var binding: ActivityVerifikasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactoryDaftarAjuan = ViewModelFactoryDaftarAjuan.getInstance()
        model =ViewModelProvider(this@Verifikasi,factory).get(DaftarAjuanViewModel::class.java)

        model.getAjuan()

        model.ajuan.observe(this){
            if (!it.isEmpty()){
                showData(it)
            }else{
                showToast("Tidak ada data")
            }

        }


    }

    private fun showData(value: ArrayList<DaftarAjuan>) {
        binding.rvPengajuan.layoutManager = LinearLayoutManager(this)
        val adapter = DaftarAjuanAdapter(this@Verifikasi)
        adapter.setData(value)
        binding.rvPengajuan.adapter = adapter

    }
    private fun showToast(message:String){
        Toast.makeText(this@Verifikasi ,message, Toast.LENGTH_SHORT).show()
    }
}