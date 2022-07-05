package com.aryaenrico.dynamicview.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityUbahHargaBinding
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.util.FormatAngka
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.UbahSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahHargaSampah
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UbahHarga : AppCompatActivity() {
    private lateinit var binding: ActivityUbahHargaBinding
    private lateinit var model: UbahSampahViewModel
    private var dataSampah = ArrayList<String>()
    private var mapSampah = HashMap<String, Sampah>()
    private var tanggalBerlaku = ""
    private val calendar1 = Calendar.getInstance()
    private var cleanStringPengepul =""
    private var formatPengepul =""
    private var currentPengepul =""
    private var cleanStringNasabah =""
    private var formatNasabah =""
    private var currentNasabah =""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahHargaBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        disabledOldPrice()
        model = ViewModelProvider(this@UbahHarga, ViewModelFactoryUbahHargaSampah.getInstance()).get(UbahSampahViewModel::class.java)
        model.setLoading(true)
        model.getData(Utils.getTanggalLengkap())
        model.loading.observe(this){
            showLoading(it)
        }
        binding.ettglBerlaku.setText(Utils.getTanggalBulanShow(calendar1))
        tanggalBerlaku = Utils.getTanggalBulanSend(calendar1)
        binding.ettglBerlaku.setOnClickListener {
            DatePickerDialog(
                this,
                setDate(),
                calendar1.get(Calendar.YEAR),
                calendar1.get(Calendar.MONTH),
                calendar1.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        model.data.observe(this) { data ->
            if (data.isNotEmpty()) {
                for (i in data.indices) {
                    this.dataSampah.add(data[i].nama_sampah)
                    this.mapSampah.set(data[i].nama_sampah, data[i])
                }
            } else {
                showToast("Terdapat kesalahan")
                finish()
            }
            model.pesan.observe(this) {
                showToast(it.pesan)
                if (it.pesan.contains("Berhasil")){
                    finish()
                }
            }
        }

        val arrayAdapter = ArrayAdapter(this, R.layout.dropdownitem, this.dataSampah)
        binding.namaSampah.setAdapter(arrayAdapter)
        binding.namaSampah.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (!binding.namaSampah.text.toString().equals("Nama Sampah")) {
                    binding.etHargaNasabahLama.setText(
                        FormatAngka.getCurrency(
                            mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?: 0
                        )
                    )
                    binding.etHargaPengepulLama.setText(
                        FormatAngka.getCurrency(
                            mapSampah[
                                binding.namaSampah.text.toString()
                            ]?.harga_pengepul ?: 0
                        )
                    )
                }
            }
        })
         binding.etHargaPengepul.addTextChangedListener(object :TextWatcher{
             override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
             }

             override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                 var data = sequence.toString()
                 if (!data.equals(currentPengepul)){
                     binding.etHargaPengepul.removeTextChangedListener(this)
                     cleanStringPengepul = data.replace("[Rp. ]".toRegex(), "")
                     if (cleanStringPengepul.isNotEmpty()){
                         formatPengepul  = FormatAngka.token(FormatAngka.getCurrency(cleanStringPengepul.toInt()))
                         currentPengepul = formatPengepul
                         binding.etHargaPengepul.setText(formatPengepul)
                     }else{
                         currentPengepul=""
                         binding.etHargaPengepul.setText("")
                     }

                     binding.etHargaPengepul.setSelection(currentPengepul.length)
                     binding.etHargaPengepul.addTextChangedListener(this)
                 }
             }

             override fun afterTextChanged(p0: Editable?) {

             }

         }
         )

        binding.etHargaNasabah.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var data = sequence.toString()
                if (!data.equals(currentNasabah)){
                    binding.etHargaNasabah.removeTextChangedListener(this)
                    cleanStringNasabah = data.replace("[Rp. ]".toRegex(), "")
                    if (cleanStringNasabah.isNotEmpty()){
                        formatNasabah  = FormatAngka.token(FormatAngka.getCurrency(cleanStringNasabah.toInt()))
                        currentNasabah = formatNasabah
                        binding.etHargaNasabah.setText(formatNasabah)
                    }else{
                        currentNasabah=""
                        binding.etHargaNasabah.setText("")
                    }

                    binding.etHargaNasabah.setSelection(currentNasabah.length)
                    binding.etHargaNasabah.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        )


        binding.findUser.setOnClickListener {
            model.setLoading(true)
            sendChangedPrice()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@UbahHarga, message, Toast.LENGTH_SHORT).show()
    }
    private fun disabledOldPrice() {
        binding.etHargaPengepulLama.setFocusable(false)
        binding.etHargaNasabahLama.setFocusable(false)
    }
    private fun setDate():DatePickerDialog.OnDateSetListener{
       val date2 = DatePickerDialog.OnDateSetListener { _, year, month, dayofmonth ->
            calendar1.set(Calendar.YEAR, year)
            calendar1.set(Calendar.MONTH, month)
            calendar1.set(Calendar.DAY_OF_MONTH, dayofmonth)
            binding.ettglBerlaku.setText(Utils.getTanggalBulanShow(calendar1))
            tanggalBerlaku = Utils.getTanggalBulanSend(calendar1)
        }
        return date2
    }
    private fun sendChangedPrice(){

            val idSampah  = this.mapSampah[binding.namaSampah.text.toString()]?.id_sampah ?:"kosong"
            val nasabah   = cleanStringNasabah
            val pengepul   =cleanStringPengepul
            val admin = "1"
            val tanggal =tanggalBerlaku
            val nasabahLama =  this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0
            val pengepulLama = this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0
            if (idSampah.equals("kosong")){
                showToast("Pilih jenis sampah terlebih dahulu")
            }else if (nasabah.isEmpty()){
                showToast("masukan harga nasabah baru")
            }else if (pengepul.isEmpty()){
                showToast("masukan harga pengepul  baru")
            }else {
                model.updateHargaSampah(idSampah,nasabah.toInt(),pengepul.toInt(),tanggal,admin,nasabahLama,pengepulLama)
            }
    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }




}