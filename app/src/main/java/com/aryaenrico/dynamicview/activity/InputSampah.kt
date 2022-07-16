package com.aryaenrico.dynamicview.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.adapter.SearchUserAdapter
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.databinding.ActivityInputSampahBinding
import com.aryaenrico.dynamicview.model.*
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.InputSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactory
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class InputSampah : AppCompatActivity() {
    private lateinit var binding: ActivityInputSampahBinding
    private lateinit var model: InputSampahViewModel
    private var mapSampah = HashMap<String, Sampah>()
    private var sampah = ArrayList<String>()
    private lateinit var dataSampah: ArrayList<Sampah>
    private  var tanggalAkhir=""
    private lateinit var calendar: Calendar
    private var id_admin="";

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInputSampahBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(profileadmin = profileAdmin.getInstance(datastore))
        model = ViewModelProvider(this@InputSampah, factory).get(InputSampahViewModel::class.java)

        model.setLoading(true)

        model.loading.observe(this){
            showLoading(it)
        }

        model.getProfileAdmin().observe(this){
           this.id_admin =it.id_admin
        }

        // live data tgl setor
        model.tglSetor.observe(this){tanggal->
            this.calendar =Utils.longToCalendar(tanggal)
            showToast(Utils.getTanggalBulanShow(calendar))
            this.tanggalAkhir =Utils.idSetor(calendar)
            model.getData(Utils.getTanggalBulanSend(calendar))
            binding.cariTanggal.text =Utils.getTanggalBulanShow(calendar)
            model.data.observe(this) {
                this.dataSampah = it
                binding.parentLinearLayout.removeAllViews()
                if (dataSampah[0].id_sampah.equals("null")) {
                    showToast("Terdapat kesalahan pada server")
                    finish()
                } else if (dataSampah[0].id_sampah.equals("Tidak ada data")){
                    addNewView(dataSampah[0].nama_sampah,"0")
                }else {
                    for (i in dataSampah.indices) {
                        this.mapSampah.set(
                            dataSampah[i].id_sampah,
                            Sampah(
                                id_sampah = dataSampah[i].id_sampah,
                                nama_sampah = dataSampah[i].nama_sampah,
                                harga_nasabah = dataSampah[i].harga_nasabah,
                                harga_pengepul = dataSampah[i].harga_pengepul

                            )
                        )
                        this.sampah.add(dataSampah[i].nama_sampah)
                        addNewView(dataSampah[i].nama_sampah,dataSampah[i].id_sampah)
                    }
                }
            }
        }

        // konfigurasi tanggal
        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayofmonth ->
            model.setLoading(true)
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayofmonth)
            this.sampah.clear()
            model.setTglSetor(calendar.timeInMillis)

        }

        binding.cariTanggal.setOnClickListener {
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // saat mencari nama nasabah
        binding.findUser.setOnClickListener {
            val nama = binding.etUsername.text.toString()
            if (nama.isNotBlank()) {
                model.setLoading(true)
                model.getNasabah(nama)
                model.nasabah.observe(this){data ->
                    if (data[0].nama.isNotBlank()){
                        binding.rvUser.visibility = View.VISIBLE
                        showNasabah(data)
                        binding.notFound.visibility = View.GONE
                    }else{
                        binding.rvUser.visibility   = View.GONE
                        binding.notFound.visibility = View.VISIBLE
                        binding.notFound.setText("Username Tidak ditemukan")
                    }

                }
            } else {
                showToast("Nama Nasabah Tidak Boleh Kosong")
            }

        }

        // submit data
        binding.buttonSubmitList.setOnClickListener {
            showData()
        }

        // pesan setelah setor di lakukan
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
                Utils.id_nasabah =""
                binding.etUsername.setText("")
            }
        }
    }

    // tampilan sampah dan bobot untuk input
    private fun addNewView(param: String,sampahId:String) {
        // membuat objek view dari hasil inflate layout xml
        val view: View = layoutInflater.inflate(R.layout.item_sampah, null, false)
        val sampahEditText = view.findViewById<EditText>(R.id.inputNamaSampah)
        val idSampah =view.findViewById<TextView>(R.id.id_sampah)
        val spin = view.findViewById<Spinner>(R.id.spinner_masa)
        var data = listOf("KG","G")
//        if (satuan.contains("liter")){
//            data = listOf("Liter")
//        }
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = arrayAdapter
        idSampah.text =sampahId
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

            val id_sampah: TextView = v.findViewById(R.id.id_sampah)
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
        builder.append(this.tanggalAkhir)
        setoran.id_admin = this.id_admin
        setoran.detil = paramDetilSetor
        setoran.id_nasabah = Utils.id_nasabah
        setoran.id_setor = builder.toString()
        setoran.tgl_setor = Utils.getTanggalLengkap()
        return setoran
    }

    // pengecekan sebelum submit
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

    //proses kirrim
    private fun showData() {
        if (checkEror()) {
            model.setLoading(true)
            val data = process()
            showToast(data.id_setor)
            model.setor(data)
//            for (i in data.detil.indices){
//                showToast(data.detil[i].id_sampah)
//                showToast(data.detil[i].total.toString())
//            }


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