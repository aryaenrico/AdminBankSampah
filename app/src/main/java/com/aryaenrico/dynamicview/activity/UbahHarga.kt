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
import com.aryaenrico.dynamicview.dataStore.profileAdmin
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
    private var tanggalBerlakuAwal = ""
    private var tanggalBerlakuAkhir = ""
    private var calendarAkhir = Calendar.getInstance()
    private var calendarAwal = Calendar.getInstance()
    private var cleanStringPengepul = ""
    private var formatPengepul = ""
    private var currentPengepul = ""
    private var cleanStringNasabah = ""
    private var formatNasabah = ""
    private var currentNasabah = ""
    private lateinit var id_admin: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahHargaBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        disabledOldPrice()
        model = ViewModelProvider(
            this@UbahHarga,
            ViewModelFactoryUbahHargaSampah.getInstance(
                profileadmin = profileAdmin.getInstance(
                    datastore
                )
            )
        ).get(UbahSampahViewModel::class.java)
        model.setLoading(true)
        model.getData(Utils.getTanggalLengkap())
        model.loading.observe(this) {
            showLoading(it)
        }
        binding.ettglBerlaku.text=Utils.getTanggalBulanShow(calendarAwal)
        setCalendarAkhir()
        tanggalBerlakuAwal = Utils.getTanggalBulanSend(calendarAwal)
        tanggalBerlakuAkhir = Utils.getTanggalBulanSend(calendarAkhir)
        binding.ettglBerlaku.setOnClickListener {
            DatePickerDialog(
                this,
                setDate(),
                calendarAwal.get(Calendar.YEAR),
                calendarAwal.get(Calendar.MONTH),
                calendarAwal.get(Calendar.DAY_OF_MONTH)
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
        }

        model.pesan.observe(this) {
            showToast(it.pesan)
            if (it.pesan.contains("Berhasil")) {
                finish()
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
        binding.etHargaPengepul.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var data = sequence.toString()
                if (!data.equals(currentPengepul)) {
                    binding.etHargaPengepul.removeTextChangedListener(this)
                    cleanStringPengepul = data.replace("[Rp. ]".toRegex(), "")
                    if (cleanStringPengepul.isNotEmpty()) {
                        formatPengepul =
                            FormatAngka.token(FormatAngka.getCurrency(cleanStringPengepul.toInt()))
                        currentPengepul = formatPengepul
                        binding.etHargaPengepul.setText(formatPengepul)
                    } else {
                        currentPengepul = ""
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
                if (!data.equals(currentNasabah)) {
                    binding.etHargaNasabah.removeTextChangedListener(this)
                    cleanStringNasabah = data.replace("[Rp. ]".toRegex(), "")
                    if (cleanStringNasabah.isNotEmpty()) {
                        formatNasabah =
                            FormatAngka.token(FormatAngka.getCurrency(cleanStringNasabah.toInt()))
                        currentNasabah = formatNasabah
                        binding.etHargaNasabah.setText(formatNasabah)
                    } else {
                        currentNasabah = ""
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
            showToast(tanggalBerlakuAwal)
            showToast(tanggalBerlakuAkhir)
            sendChangedPrice()

        }

        model.getProfileAdmin().observe(this) {
            this.id_admin = it.id_admin
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@UbahHarga, message, Toast.LENGTH_SHORT).show()
    }

    private fun disabledOldPrice() {
        binding.etHargaPengepulLama.setFocusable(false)
        binding.etHargaNasabahLama.setFocusable(false)
    }

    private fun setDate(): DatePickerDialog.OnDateSetListener {
        val date2 = DatePickerDialog.OnDateSetListener { _, year, month, dayofmonth ->
            calendarAwal.set(Calendar.YEAR, year)
            calendarAwal.set(Calendar.MONTH, month)
            calendarAwal.set(Calendar.DAY_OF_MONTH, dayofmonth)

            calendarAkhir.set(Calendar.YEAR, year)
            calendarAkhir.set(Calendar.MONTH, month)
            calendarAkhir.set(Calendar.DAY_OF_MONTH, calendarAwal.getActualMaximum(Calendar.DATE))

            binding.ettglBerlaku.setText(Utils.getTanggalBulanShow(calendarAwal))
            tanggalBerlakuAwal = Utils.getTanggalBulanSend(calendarAwal)
            tanggalBerlakuAkhir = Utils.getTanggalBulanSend(calendarAkhir)
        }
        return date2
    }

    private fun sendChangedPrice() {
        val idSampah = this.mapSampah[binding.namaSampah.text.toString()]?.id_sampah ?: "kosong"
        val nasabah = cleanStringNasabah
        val pengepul = cleanStringPengepul

        val nasabahLama = this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?: 0
        val pengepulLama = this.mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?: 0
        when {
            idSampah.equals("kosong") -> showToast("Pilih jenis sampah terlebih dahulu")
            nasabah.isEmpty() -> showToast("masukan harga nasabah baru")
            pengepul.isEmpty() -> showToast("masukan harga pengepul  baru")
            else ->

                model.updateHargaSampah(
                idSampah,
                nasabah.toInt(),
                pengepul.toInt(),
                tanggalBerlakuAwal,
                this.id_admin,
                nasabahLama,
                pengepulLama,
                tanggalBerlakuAkhir
            )
        }
    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun setCalendarAkhir() {
        calendarAkhir.set(Calendar.YEAR, calendarAwal.get(Calendar.YEAR))
        calendarAkhir.set(Calendar.YEAR, calendarAwal.get(Calendar.YEAR))
        calendarAkhir.set(Calendar.DAY_OF_MONTH, calendarAwal.getActualMaximum(Calendar.DATE))
    }
}