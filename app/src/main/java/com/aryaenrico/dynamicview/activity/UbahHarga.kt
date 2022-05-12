package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityUbahHargaBinding
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.UbahSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahHargaSampah

class UbahHarga : AppCompatActivity() {

    private lateinit var binding:ActivityUbahHargaBinding
    private lateinit var model: UbahSampahViewModel
    private var dataSampah = ArrayList<String>()
    private var mapSampah = HashMap<String, Sampah>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_harga)
        binding = ActivityUbahHargaBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        model = ViewModelProvider(this@UbahHarga, ViewModelFactoryUbahHargaSampah.getInstance()).get(UbahSampahViewModel::class.java)

        model.getData()
        model.data.observe(this){data->
            if (data.isNotEmpty()){
                for (i in 0..data.size-1){
                    this.dataSampah.add(data[i].nama_sampah)
                    this.mapSampah.set(data[i].nama_sampah,data[i])
                }
            }else{
                showToast("Terdapat kesalahan")
                finish()
            }

            model.pesan.observe(this){
                showToast(it.pesan)
            }

        }

        val arrayAdapter = ArrayAdapter(this,R.layout.dropdownitem,this.dataSampah)
        binding.namaSampah.setAdapter(arrayAdapter)

        binding.findUser.setOnClickListener {
            val idSampah  = this.mapSampah.get(binding.namaSampah.text.toString())?.id_sampah ?:"kosong"
            val nasabah   =binding.etHargaNasabah.text.toString()
            val pengepul   =binding.etHargaPengepul.text.toString()
            val admin ="a001"
            val tanggal =Utils.getTanggalLengkap()
            val nasabahLama =  this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0
            val pengepulLama = this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0

            model.updateHargaSampah(idSampah,nasabah.toInt(),pengepul.toInt(),tanggal,admin,nasabahLama,pengepulLama)
        }
    }



private fun showToast(message:String){
    Toast.makeText(this@UbahHarga,message, Toast.LENGTH_SHORT).show()
}

}