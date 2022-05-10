package com.aryaenrico.dynamicview.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityTambahSampahBinding
import com.aryaenrico.dynamicview.model.Kategori
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.TambahSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryTambahSampah
import java.util.ArrayList

class TambahSampah : AppCompatActivity() {
    private lateinit var binding : ActivityTambahSampahBinding
    private lateinit var model:TambahSampahViewModel
    private var kategoriSampah =ArrayList<String>()
    private var kategoriKey =HashMap<String,Kategori>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_sampah)
        supportActionBar?.hide()
        binding = ActivityTambahSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model =ViewModelProvider(this,ViewModelFactoryTambahSampah.getInstance()).get(TambahSampahViewModel::class.java)
        model.getKategori()

        model.data.observe(this){data->
            for (i in 0..data.size-1){
                this.kategoriSampah.add(data[i].deskripsi)
                this.kategoriKey.set(data[i].deskripsi,data[i])
            }
        }

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownitem,kategoriSampah)
        binding.filledexposed.setAdapter(arrayAdapter)

        binding.findUser.setOnClickListener {
           // mendapat inputan user
            val namaSampah = kategoriKey.get(binding.filledexposed.text.toString())?.deskripsi?:""
            val kategoriSampah = kategoriKey.get(binding.filledexposed.text.toString())?.id_kategori?.toInt()?:0
            val nasabah = binding.etHargaNasabah.text.toString()
            val pengepul = binding.etHargaPengepul.text.toString()
            showToast(kategoriKey.get(binding.filledexposed.text.toString())?.id_kategori.toString())
            model.tambahSampah("S001",namaSampah,nasabah.toInt(),pengepul.toInt(),kategoriSampah,Utils.getTanggalLengkap(),"a001")
        }

        model.pesan.observe(this){
            showToast(it.pesan)
        }

    }

    private fun showToast(message:String){
        Toast.makeText(this@TambahSampah ,message, Toast.LENGTH_SHORT).show()
    }



}