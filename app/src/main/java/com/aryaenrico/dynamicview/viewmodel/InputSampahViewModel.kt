package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import com.aryaenrico.dynamicview.retrofit.ApiConfig
import kotlinx.coroutines.launch

class InputSampahViewModel:ViewModel() {

    private var _data = MutableLiveData<ArrayList<Sampah>>()
    val  data:LiveData<ArrayList<Sampah>> =_data

    private var _pesan = MutableLiveData<Message>()
    val pesan :LiveData<Message> =_pesan

    fun getData(){
        viewModelScope.launch {
            _data.value =ApiConfig.getApi().getSampah()
        }
    }

    fun setor(setoran: Setoran){
        viewModelScope.launch {
            _pesan.value =ApiConfig.getApi().setoran(setoran)
        }
    }

}