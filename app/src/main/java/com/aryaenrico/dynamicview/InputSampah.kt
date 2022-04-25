package com.aryaenrico.dynamicview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.aryaenrico.dynamicview.databinding.ActivityMainBinding



class InputSampah : AppCompatActivity() {

  private  lateinit var binding:ActivityMainBinding
  private var languageList = ArrayList<Language>()
    var otherStrings = listOf<String>("a", "b", "c","d")

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.buttonAdd.setOnClickListener {
            addNewView()
        }

        // This Submit Button is used to store all the
        // data entered by user in arraylist
        binding.buttonSubmitList.setOnClickListener {
            saveData()
        }

        // This Show button is used to show data
        // stored in the arraylist.

    }

    // This function is called after
    // user clicks on addButton
    private fun addNewView() {
        // this method inflates the single item layout
        // inside the parent linear layout

        // membuat objek view dari hasil inflate layout xml
        var view:View = layoutInflater.inflate(R.layout.row_add_language,null,false)
        var spin = view.findViewById<Spinner>(R.id.exp_spinner)
        val arrayAdapter = ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, otherStrings)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter =arrayAdapter

        //binding.parentLinearLayout.addView(inflate, binding.parentLinearLayout.childCount)
        binding.parentLinearLayout.addView(view,binding.parentLinearLayout.childCount)


    }


    // This function is called after user
    // clicks on Submit Button
    private fun saveData() {
        languageList.clear()

        // this counts the no of child layout
        // inside the parent Linear layout
        val count = binding.parentLinearLayout.childCount
        var v: View?


        for (i in 0 until count) {
            v = binding.parentLinearLayout.getChildAt(i)

            val languageName: EditText = v.findViewById(R.id.et_name)
            val experience: Spinner = v.findViewById(R.id.exp_spinner)
            val masa: Spinner = v.findViewById(R.id.exp_spinner_masa)

            // create an object of Language class
            val language = Language()
            language.name = languageName.text.toString()
            language.exp = experience.selectedItemId.toString()
            language.massa =masa.selectedItemId.toString()

            // add the data to arraylist
            languageList.add(language)
        }
    }

    // This function is called after user
    // clicks on Show List data button
    private fun showData() {
        val count = binding.parentLinearLayout.childCount
        for (i in 0 until count) {
            Toast.makeText(this, "Language at $i is ${languageList[i].name} and experience is ${languageList[i].exp}  ${languageList[i].massa} ", Toast.LENGTH_SHORT).show()
        }
    }



}