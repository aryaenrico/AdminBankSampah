package com.aryaenrico.dynamicview.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.adapter.DaftarMutasiAdapter
import com.aryaenrico.dynamicview.databinding.ActivityMutasiTransaksiBinding
import com.aryaenrico.dynamicview.model.MutasiTransaksiData
import com.aryaenrico.dynamicview.viewmodel.MutasiTransaksiViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryMutasiTransaksi
import java.text.SimpleDateFormat
import java.util.*

class MutasiTransaksi : AppCompatActivity() {
    private lateinit var binding : ActivityMutasiTransaksiBinding
    private lateinit var model:MutasiTransaksiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMutasiTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val factory:ViewModelFactoryMutasiTransaksi = ViewModelFactoryMutasiTransaksi.getInstance()
        model =ViewModelProvider(this@MutasiTransaksi,factory).get(MutasiTransaksiViewModel::class.java)

        val calendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{_,year,month,dayofmonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayofmonth)
            formatDateAwal(calendar)

        }

        val datePicker2 = DatePickerDialog.OnDateSetListener{_,year,month,dayofmonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayofmonth)
            formatDateAkhir(calendar)
        }

        binding.tanggalawal.setOnClickListener {
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.tanggalakhir.setOnClickListener {
            DatePickerDialog(this,datePicker2,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.cariRiwayat.setOnClickListener {
            val tanggalAwal  = binding.tanggalawal.text.toString().trim()
            val tanggalAkhir = binding.tanggalakhir.text.toString().trim()


            if (!tanggalAkhir.contains("Akhir") and !tanggalAwal.contains("Awal")){
                val tanggalAwalInt = dateTomilis(tanggalAwal)
                val tanggalAkhirInt = dateTomilis(tanggalAkhir)
                if (tanggalAwalInt > tanggalAkhirInt){
                    showToast("Format Tanggal salah")
                }else{
                    model.getMutasiTransaksi(tanggalAwal,tanggalAkhir)
                }

            }else{
                showToast("Harap Masukan tanggal awal dan akhir terlebih dahulu ")
            }
        }

        model.data.observe(this){data->
            if (!data.isEmpty()){
                showData(data)
            }
            if (data.isEmpty()){
                showToast("data tidak ada")
            }
        }
    }

    private fun formatDateAwal(calendar: Calendar) {
        val myFormat ="yyyy-MM-dd"
        val sdf =SimpleDateFormat(myFormat)
        binding.tanggalawal.text = sdf.format(calendar.time)

    }
    private fun formatDateAkhir(calendar: Calendar) {
        val myFormat ="yyyy-MM-dd"
        val sdf =SimpleDateFormat(myFormat)
        binding.tanggalakhir.text = sdf.format(calendar.time)
    }

    private fun dateTomilis(message: String):Long{
        val myFormat ="yyyy-MM-dd"
        val sdf =SimpleDateFormat(myFormat)
        val date: Date = sdf.parse(message)

        val calendar = Calendar.getInstance()

        calendar.time = date

        return calendar.timeInMillis
    }
    private fun showToast(message:String){
        Toast.makeText(this@MutasiTransaksi,message,Toast.LENGTH_SHORT).show()
    }

    private fun showData(value: ArrayList<MutasiTransaksiData>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DaftarMutasiAdapter(this@MutasiTransaksi)
        adapter.setData(value)
        binding.recyclerView.adapter = adapter

    }
}