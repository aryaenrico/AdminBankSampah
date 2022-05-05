package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityTambahKategoriBinding
import com.aryaenrico.dynamicview.viewmodel.InputKategoriViewmodel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryInputKategori

class TambahKategori : AppCompatActivity() {
    private lateinit var model:InputKategoriViewmodel
    private lateinit var binding:ActivityTambahKategoriBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahKategoriBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory:ViewModelFactoryInputKategori = ViewModelFactoryInputKategori.getInstance()
        model = ViewModelProvider(this@TambahKategori,factory).get(InputKategoriViewmodel::class.java)

        binding.tambahKategori.setOnClickListener {
            var kategori = binding.etNamaSampah.text.toString().trim()
            when{
                kategori.isBlank()->{
                    binding.etNamaSampah.error ="Kolom Ini tidak boleh kososng"
                }else->{
                    model.inputKategori(kategori)
                }
            }
        }
        model.pesan.observe(this){
            if (it.pesan.isNotBlank()){
                showToast(it.pesan)
            }

            if (it.pesan.equals("Berhasil menambah kategori")){
                binding.etNamaSampah.setText("")
            }
        }
    }

    private fun showToast(message:String){
        Toast.makeText(this@TambahKategori ,message, Toast.LENGTH_SHORT).show()
    }
}