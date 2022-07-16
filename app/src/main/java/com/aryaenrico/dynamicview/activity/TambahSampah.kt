package com.aryaenrico.dynamicview.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.databinding.ActivityTambahSampahBinding
import com.aryaenrico.dynamicview.model.Kategori
import com.aryaenrico.dynamicview.util.FormatAngka
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
    private var cleanStringPengepul =""
    private var formatPengepul =""
    private var currentPengepul =""
    private var cleanStringNasabah =""
    private var formatNasabah =""
    private var currentNasabah =""
    private lateinit var id_admin:String
    private var satuanMassa = listOf("KG","Liter")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityTambahSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model =ViewModelProvider(this,ViewModelFactoryTambahSampah.getInstance(profileadmin = profileAdmin.getInstance(datastore))).get(TambahSampahViewModel::class.java)
        model.getKategori()
        model.countSampah()
        model.getProfileAdmin().observe(this){
            this.id_admin =it.id_admin
        }

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
        val arrayAdapterSatuanMassa =ArrayAdapter(this,R.layout.dropdownitem,satuanMassa)
        binding.satuanPenimbangan.setAdapter(arrayAdapterSatuanMassa)

        binding.etHargaPengepul.addTextChangedListener(object : TextWatcher {
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

        binding.etHargaNasabah.addTextChangedListener(object : TextWatcher {
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
           // mendapat inputan user
            if  (check()){
                val namaSampah = binding.etNamaSampah.text.toString()
                val kategoriSampah = kategoriKey.get(binding.filledexposed.text.toString())?.id_kategori ?:0
                val nasabah = cleanStringNasabah
                val pengepul = cleanStringPengepul
                val idSampahtemp ="SMP${this.countSampah}${kategoriSampah}"
                val idSampah =idSampahtemp.filter { !it.isWhitespace() }
                showToast(binding.satuanPenimbangan.text.toString())
               // model.tambahSampah(idSampah,namaSampah,nasabah.toInt(),pengepul.toInt(),kategoriSampah,Utils.getTanggalLengkap(),this.id_admin,binding.etsatuan.text.toString())
            }else{
                showToast("Pastikan semua kolom sudah terisi")
            }

        }

        model.pesan.observe(this){
            showToast(it.pesan)
            if (it.pesan.contains("Berhasil")){
                binding.etHargaNasabah.setText("")
                binding.etHargaPengepul.setText("")
                binding.filledexposed.setText("")
                binding.etNamaSampah.setText("")
                //binding.etsatuan.setText("")
            }

        }
    }

    private fun check():Boolean{
     return when{
         binding.etHargaNasabah.text.isBlank() -> false
         binding.etHargaPengepul.text.isBlank()->false
         binding.etNamaSampah.text.isBlank()->false
         binding.filledexposed.text.equals(getString(R.string.kategori_sampah))->false
         //binding.etsatuan.text.isEmpty()->false
         else->true

     }
    }

    private fun showToast(message:String){
        Toast.makeText(this@TambahSampah ,message, Toast.LENGTH_SHORT).show()
    }
}