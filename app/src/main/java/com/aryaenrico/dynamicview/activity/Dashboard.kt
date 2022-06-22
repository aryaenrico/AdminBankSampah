package com.aryaenrico.dynamicview.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

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