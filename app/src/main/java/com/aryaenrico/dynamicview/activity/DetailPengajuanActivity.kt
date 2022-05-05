package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.adapter.DaftarAjuanAdapter
import com.aryaenrico.dynamicview.databinding.ActivityDetailPengajuanBinding
import com.aryaenrico.dynamicview.model.DaftarAjuan

class DetailPengajuanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailPengajuanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val param = intent.getParcelableExtra<DaftarAjuan>(DaftarAjuanAdapter.PARAM) as DaftarAjuan
        data(param)

    }

    private fun data(detail:DaftarAjuan){
        binding.namaPengaju.text =detail.nama_nasabah
        binding.saldo.text =""+detail.jumlah
        binding.tanggalPengajuan.text = detail.tanggal_pengajuan
    }
}