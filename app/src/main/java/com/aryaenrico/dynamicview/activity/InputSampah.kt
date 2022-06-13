package com.aryaenrico.dynamicview.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.adapter.SearchUserAdapter
import com.aryaenrico.dynamicview.databinding.ActivityInputSampahBinding
import com.aryaenrico.dynamicview.model.*
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.InputSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactory

class InputSampah : AppCompatActivity() {

    private lateinit var binding: ActivityInputSampahBinding
    private lateinit var model: InputSampahViewModel
    private var mapSampah = HashMap<String, Sampah>()
    private var sampah = ArrayList<String>()
    private lateinit var dataSampah: ArrayList<Sampah>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInputSampahBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        showLoading(true)
        val factory: ViewModelFactory = ViewModelFactory.getInstance()
        model = ViewModelProvider(this@InputSampah, factory).get(InputSampahViewModel::class.java)

        model.getData()
        model.data.observe(this) {
            this.dataSampah = it

            if (dataSampah[0].id_sampah.equals("null")) {
                showToast("Terdapat kesalahan pada server")
                finish()
            } else {
                for (i in 0..dataSampah.size - 1) {
                    this.mapSampah.set(
                        dataSampah[i].nama_sampah,
                        Sampah(
                            id_sampah = dataSampah[i].id_sampah,
                            nama_sampah = dataSampah[i].nama_sampah,
                            harga_nasabah = dataSampah[i].harga_nasabah,
                            harga_pengepul = dataSampah[i].harga_pengepul
                        )
                    )
                    this.sampah.add(dataSampah[i].nama_sampah)
                    addNewView(dataSampah[i].nama_sampah)
                }
            }
            showLoading(false)
        }

        binding.findUser.setOnClickListener {
            showLoading(true)
            val nama = binding.etUsername.text.toString()
            if (nama.isNotBlank()) {
                model.getNasabah(nama)
                model.nasabah.observe(this){data ->
                    if (data[0].nama.isNotBlank()){
                        binding.rvUser.visibility = View.VISIBLE
                        showNasabah(data)
                        binding.notFound.visibility = View.GONE
                    }else{
                        binding.rvUser.visibility   = View.GONE
                        binding.notFound.visibility = View.VISIBLE
                        binding.notFound.setText("Tidak Ada Riwayat Transaksi")
                    }
                    showLoading(false)
                }
            } else {
                showToast("Nama Nasabah Tidak Boleh Kosong")
            }

        }

        // This Submit Button is used to store all the
        // data entered by user in arraylist
        binding.buttonSubmitList.setOnClickListener {
            showData()
        }

        model.pesan.observe(this) {
            if (it.pesan.isNotBlank()) {
                showToast(it.pesan)
            }
            if (it.pesan.equals("sukses")) {
                val count = binding.parentLinearLayout.childCount
                var v: View?
                for (i in 0 until count) {
                    v = binding.parentLinearLayout.getChildAt(i)
                    val bobot: EditText = v.findViewById(R.id.inputbobotSampah)
                    bobot.setText("0")
                }
            }
        }
    }

    private fun addNewView(param: String) {
        // membuat objek view dari hasil inflate layout xml
        var view: View = layoutInflater.inflate(R.layout.item_sampah, null, false)
        var sampahEditText = view.findViewById<EditText>(R.id.inputNamaSampah)
        sampahEditText.setText(param)
        binding.parentLinearLayout.addView(view, binding.parentLinearLayout.childCount)
    }

    private fun process(): Setoran {
        val count = binding.parentLinearLayout.childCount
        var v: View?
        val paramDetilSetor = ArrayList<DetilSetor>()
        // objek setoran
        val setoran = Setoran()

        // objek Stringbuilder
        val builder = StringBuilder(Utils.id_nasabah)
        for (i in 0 until count) {
            val detilSetoran = DetilSetor()
            v = binding.parentLinearLayout.getChildAt(i)

            val id_sampah: EditText = v.findViewById(R.id.inputNamaSampah)
            val bobot: EditText = v.findViewById(R.id.inputbobotSampah)

            val paramSampah = id_sampah.text.toString()
            val parambobot1 = bobot.text.toString()
            val parambobot: Float = parambobot1.toFloat()
            if (!parambobot1.equals("0")) {
                val paramNasabah = this.mapSampah.get(paramSampah)?.harga_nasabah ?: 0
                val paramPengepul = this.mapSampah.get(paramSampah)?.harga_pengepul ?: 0

                //val timbangan: Float = _timbangan.toFloat()
                val hargaNasabah = (paramNasabah * parambobot).toInt()
                val hargaPengepul = (paramPengepul * parambobot).toInt()
                val idSampah = this.mapSampah.get(paramSampah)?.id_sampah ?: ""

                detilSetoran.id_sampah = idSampah
                detilSetoran.total = parambobot
                detilSetoran.harga_nasabah = hargaNasabah
                detilSetoran.harga_pengepul = hargaPengepul
                paramDetilSetor.add(detilSetoran)
            } else {
                continue
            }
        }
        builder.append(Utils.getTanggalBulan())
        setoran.id_admin = "a001"
        setoran.detil = paramDetilSetor
        setoran.id_nasabah = Utils.id_nasabah
        setoran.id_setor = builder.toString()
        setoran.tgl_setor = Utils.getTanggalLengkap()
        return setoran
    }

    private fun checkEror(): Boolean {

        if (Utils.id_nasabah.isEmpty()) {
            return false
        }
        val count = binding.parentLinearLayout.childCount
        var v: View?
        var flag = 0
        for (i in 0 until count) {
            v = binding.parentLinearLayout.getChildAt(i)
            val bobot: EditText = v.findViewById(R.id.inputbobotSampah)
            if (bobot.text.toString().equals("0")) {
                continue
            } else {
                flag++
            }
            if (flag > 0) {
                return true
            }
        }
        return false
    }

    // This function is called after user
    // clicks on Show List data button
    private fun showData() {
        if (checkEror()) {
            val data = process()
            model.setor(data)
            //val count = binding.parentLinearLayout.childCount
            for (i in 0 until data.detil.size) {
                Toast.makeText(
                    this,
                    "id_sampah $i is  = ${data.detil[i].id_sampah} harga pengepul =  ${data.detil[i].harga_pengepul} harga nasabah = ${data.detil[i].harga_nasabah} Bobot = ${data.detil[i].total} ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            showToast("Pastikan semua data telah terisi ")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@InputSampah, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNasabah(param: ArrayList<Nasabah>) {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val adapter = SearchUserAdapter()
        adapter.setData(param)
        binding.rvUser.adapter = adapter
        adapter.setItemClickCallback(object : SearchUserAdapter.itemClickCallback {
            override fun onitemClicked(param: Nasabah) {
                binding.etUsername.setText(param.nama)
                binding.rvUser.visibility = View.GONE
                Utils.id_nasabah = param.id_nasabah
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.id_nasabah = ""
    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}