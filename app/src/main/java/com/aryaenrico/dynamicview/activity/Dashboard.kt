package com.aryaenrico.dynamicview.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.databinding.ActivityDashboardBinding
import com.aryaenrico.dynamicview.viewmodel.DashboardViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryDashboard

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var model:DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        model = ViewModelProvider(this,ViewModelFactoryDashboard.getInstance(profileAdmin.getInstance(datastore))).get(DashboardViewModel::class.java)

        setContentView(binding.root)
        model.getProfileAdmin().observe(this){
            binding.namaAdmin.text =it.nama
        }

        binding.cvUbahHarga.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    UbahHarga::class.java
                )
            )
        }
        binding.cvInputSampah.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    InputSampah::class.java
                )
            )
        }
        binding.cvUbahTransaksi.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    UbahTransaksi::class.java
                )
            )
        }
        binding.cvTambahUser.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    TambahUser::class.java
                )
            )
        }
        binding.cvTambahSampah.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    TambahSampah::class.java
                )
            )
        }
        binding.cvTambahKategori.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    TambahKategori::class.java
                )
            )
        }
        binding.cvMutasiSampah.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    MutasiTransaksi::class.java
                )
            )
        }
        binding.cvVerifikasi.setOnClickListener {
            startActivity(
                Intent(
                    this@Dashboard,
                    Verifikasi::class.java
                )
            )
        }

    }
}