package com.aryaenrico.dynamicview.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.adapter.DaftarAjuanAdapter
import com.aryaenrico.dynamicview.databinding.ActivityDetailPengajuanBinding
import com.aryaenrico.dynamicview.model.DaftarAjuan
import com.aryaenrico.dynamicview.util.FormatAngka
import com.aryaenrico.dynamicview.viewmodel.AddPengajuanViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryAddPengajuan

class DetailPengajuanActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailPengajuanBinding
    private lateinit var model:AddPengajuanViewModel
    private var cleanString =""
    private var format =""
    private var current =""
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
            model.pengajuan(param.id_pengajuan,binding.filledexposed.text.toString(),cleanString,param.id_nasabah)
        }

        binding.saldo.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var data = sequence.toString()
                if (!data.equals(current)){
                    binding.saldo.removeTextChangedListener(this)
                    cleanString = data.replace("[Rp. ]".toRegex(), "")

                    if (cleanString.isNotEmpty()){
                        format  = FormatAngka.token(FormatAngka.getCurrency(cleanString.toInt()))
                        current = format
                        binding.saldo.setText(format)
                    }else{
                        current=""
                        binding.saldo.setText("")
                    }
                    binding.saldo.setSelection(current.length)
                    binding.saldo.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun data(detail:DaftarAjuan){
        binding.namaPengaju.text =detail.nama_nasabah
        cleanString =detail.jumlah.toString()
        binding.saldo.setText(FormatAngka.token(FormatAngka.getCurrency(detail.jumlah)))
        binding.tanggalPengajuan.text = detail.tanggal_pengajuan
    }
    private fun showToast(pesan:String){
        Toast.makeText(this@DetailPengajuanActivity,pesan,Toast.LENGTH_SHORT).show()
    }
}