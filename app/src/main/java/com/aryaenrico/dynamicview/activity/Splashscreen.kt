package com.aryaenrico.dynamicview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.viewmodel.LoginViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryLogin

class Splashscreen : AppCompatActivity() {
    private lateinit var model: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        model = ViewModelProvider(
            this,
            ViewModelFactoryLogin.getInstance(profileadmin = profileAdmin.getInstance(datastore))
        ).get(LoginViewModel::class.java)


        Handler(Looper.getMainLooper()).postDelayed({
            model.getDataAdmin().observe(this){
                if (it.is_login){
                    val intent = Intent(this, Dashboard::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }else{
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }

        }, 3000)
    }
}