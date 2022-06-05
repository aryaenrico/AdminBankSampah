package com.aryaenrico.dynamicview.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityUbahHargaBinding
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.UbahSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahHargaSampah
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UbahHarga : AppCompatActivity() {

    private lateinit var binding:ActivityUbahHargaBinding
    private lateinit var model: UbahSampahViewModel
    private var dataSampah = ArrayList<String>()
    private var mapSampah = HashMap<String, Sampah>()
    private var tanggalAkhir =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_harga)
        binding = ActivityUbahHargaBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        model = ViewModelProvider(this@UbahHarga, ViewModelFactoryUbahHargaSampah.getInstance()).get(UbahSampahViewModel::class.java)


        val calendar1 = Calendar.getInstance()
        binding.ettglBerlaku.setText(Utils.getTanggalBulanShow(calendar1))
        tanggalAkhir =Utils.getTanggalBulanSend(calendar1)

        val datePicker2 = DatePickerDialog.OnDateSetListener{ _, year, month, dayofmonth ->
            calendar1.set(Calendar.YEAR,year)
            calendar1.set(Calendar.MONTH,month)
            calendar1.set(Calendar.DAY_OF_MONTH,dayofmonth)
            binding.ettglBerlaku.setText(Utils.getTanggalBulanShow(calendar1))
            tanggalAkhir =Utils.getTanggalBulanSend(calendar1)
        }

        binding.ettglBerlaku.setOnClickListener {
            DatePickerDialog(this,datePicker2,calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH)).show()
        }
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
        binding.namaSampah.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (!binding.namaSampah.text.toString().equals("Nama Sampah")){
                    showToast(mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah.toString())
                    showToast(mapSampah.get(binding.namaSampah.text.toString())?.harga_pengepul.toString())
                }
            }
        })


        binding.findUser.setOnClickListener {
            showToast(tanggalAkhir)

//          val idSampah  = this.mapSampah.get(binding.namaSampah.text.toString())?.id_sampah ?:"kosong"
//            val nasabah   =binding.etHargaNasabah.text.toString()
//            val pengepul   =binding.etHargaPengepul.text.toString()
//            val admin ="a001"
//            val tanggal =Utils.getTanggalLengkap()
//            val nasabahLama =  this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0
//            val pengepulLama = this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0
//
//            model.updateHargaSampah(idSampah,nasabah.toInt(),pengepul.toInt(),tanggal,admin,nasabahLama,pengepulLama)
        }
    }



private fun showToast(message:String){
    Toast.makeText(this@UbahHarga,message, Toast.LENGTH_SHORT).show()
}

}