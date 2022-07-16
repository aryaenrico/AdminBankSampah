package com.aryaenrico.dynamicview.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.databinding.ActivityLoginBinding
import com.aryaenrico.dynamicview.model.Admin
import com.aryaenrico.dynamicview.viewmodel.LoginViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryLogin


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name="profile")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var model: LoginViewModel
    private lateinit var admin: Admin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model =ViewModelProvider(this,ViewModelFactoryLogin.getInstance(profileadmin = profileAdmin.getInstance(datastore))).get(LoginViewModel::class.java)
        model.loading.observe(this){
            showLoading(true)
        }
        model.loginResult.observe(this){
            if (it.pesanEror.equals("berhasil")){
                showToast("Selamat Anda Berhasil Login")
                admin = Admin(id_admin = it.id_admin, alamat = it.alamat, no_telepon = it.no_telepon, nama = it.nama, is_login = true)
                model.setDataAdmin(admin)
            }else{
                showToast(it.pesanEror)
            }
        }

        model.getDataAdmin().observe(this){
            if (it.is_login){
                val move = Intent(this, Dashboard::class.java)
                move.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(move)
            }
        }

        binding.btnlogin.setOnClickListener {
            if (checkError()){
                model.setLoading(true)
                model.login(binding.username.text.toString(),binding.password.text.toString())
            }

        }
    }
    private fun showToast(pesan:String){
        Toast.makeText(this,pesan, Toast.LENGTH_SHORT).show()
    }

    private fun checkError():Boolean{
        val notelp   = binding.username.text.toString().trim()
        val password = binding.password.text.toString()
        return when{
            notelp.isEmpty()->{
                binding.username.error = "kolom ini wajib di isi"
                false
            }
            password.isEmpty()->{
                binding.password.error ="kolom ini wajib di isi"
                false
            }else-> true
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