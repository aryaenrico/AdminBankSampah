package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.adapter.DaftarAjuanAdapter
import com.aryaenrico.dynamicview.databinding.ActivityDetailPengajuanBinding
import com.aryaenrico.dynamicview.model.DaftarAjuan
import com.aryaenrico.dynamicview.viewmodel.AddPengajuanViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryAddPengajuan

class DetailPengajuanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailPengajuanBinding
    private lateinit var model:AddPengajuanViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model =ViewModelProvider(this,ViewModelFactoryAddPengajuan.getInstance()).get(AddPengajuanViewModel::class.java)

        val param = intent.getParcelableExtra<DaftarAjuan>(DaftarAjuanAdapter.PARAM) as DaftarAjuan
        data(param)
        Toast.makeText(this,param.id_nasabah,Toast.LENGTH_SHORT).show()

        val status = listOf("Diterima","Ditolak")
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdownitem,status)
        binding.filledexposed.setAdapter(arrayAdapter)

        model.pesan.observe(this){
            if (it.pesan.contains("berhasil")){
                showToast(it.pesan)
                finish()
            }else{
                showToast(it.pesan)
            }
        }

        binding.btnPengajuan.setOnClickListener {
            model.pengajuan(param.id_pengajuan,binding.filledexposed.text.toString())
        }
    }



    private fun data(detail:DaftarAjuan){
        binding.namaPengaju.text =detail.nama_nasabah
        binding.saldo.text =""+detail.jumlah
        binding.tanggalPengajuan.text = detail.tanggal_pengajuan
    }
    private fun showToast(pesan:String){
        Toast.makeText(this@DetailPengajuanActivity,pesan,Toast.LENGTH_SHORT).show()
    }
}