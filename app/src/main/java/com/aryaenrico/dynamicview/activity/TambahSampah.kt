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
    private var countSampah="0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTambahSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model =ViewModelProvider(this,ViewModelFactoryTambahSampah.getInstance()).get(TambahSampahViewModel::class.java)
        model.getKategori()

        model.countSampah()

        model.count.observe(this){total->
           val data = total.pesan.toInt()
           val temp = data+1
           this.countSampah =temp.toString()
        }
        model.data.observe(this){data->
            for (i in data.indices){
                this.kategoriSampah.add(data[i].deskripsi)
                this.kategoriKey.set(data[i].deskripsi,data[i])
            }
        }

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownitem,kategoriSampah)
        binding.filledexposed.setAdapter(arrayAdapter)

        binding.findUser.setOnClickListener {
           // mendapat inputan user
            if  (check()){
                val namaSampah = binding.etNamaSampah.text.toString()
                val kategoriSampah = kategoriKey.get(binding.filledexposed.text.toString())?.id_kategori ?:0
                val nasabah = binding.etHargaNasabah.text.toString()
                val pengepul = binding.etHargaPengepul.text.toString()
                val idSampahtemp ="SMP${this.countSampah}${kategoriSampah}"
                val idSampah =idSampahtemp.filter { !it.isWhitespace() }
                showToast(idSampah)
                model.tambahSampah(idSampah,namaSampah,nasabah.toInt(),pengepul.toInt(),kategoriSampah,Utils.getTanggalLengkap(),"a001")
            }else{
                showToast("Pastikan semua kolom sudah terisi")
            }

        }

        model.pesan.observe(this){
            showToast(it.pesan)
            if (it.pesan.contains("Berhasil"))
                binding.etHargaNasabah.setText("")
            binding.etHargaPengepul.setText("")
            binding.filledexposed.setText("")
            binding.etNamaSampah.setText("")
        }
    }

    private fun check():Boolean{
     return when{
         binding.etHargaNasabah.text.isBlank() -> false
         binding.etHargaPengepul.text.isBlank()->false
         binding.etNamaSampah.text.isBlank()->false
         binding.filledexposed.text.equals(getString(R.string.kategori_sampah))->false
         else->true

     }
    }

    private fun showToast(message:String){
        Toast.makeText(this@TambahSampah ,message, Toast.LENGTH_SHORT).show()
    }
}