package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Admin
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.SaldoNasabah
import com.aryaenrico.dynamicview.repository.AddPengajuanRepository
import kotlinx.coroutines.launch

class AddPengajuanViewModel(private val addPengajuanRepository: AddPengajuanRepository,private val profileadmin: profileAdmin):ViewModel() {
    private var _pesan = MutableLiveData<Message>()
    val pesan : LiveData<Message> =_pesan

    private var _saldo = MutableLiveData<SaldoNasabah>()
    val saldo : LiveData<SaldoNasabah> =_saldo

    fun getSaldoNasabah(id_nasabah: String){
        viewModelScope.launch {
            _saldo.value =addPengajuanRepository.saldoUser(id_nasabah)
        }
    }

    fun pengajuan(id:String,status:String,jumlah:String,id_nasabah:String,id_admin:String){
        viewModelScope.launch {
            _pesan.value =addPengajuanRepository.addPengajuan(id,status,jumlah,id_nasabah,id_admin)
        }
    }
    fun getProfileAdmin():LiveData<Admin>{
        return  profileadmin.getProfile().asLiveData()
    }
}
class ViewModelFactoryAddPengajuan(private val addPengajuanRepository: AddPengajuanRepository,private val profileadmin: profileAdmin) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AddPengajuanViewModel::class.java) -> {
                return AddPengajuanViewModel(addPengajuanRepository,profileadmin) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryAddPengajuan? = null

        fun getInstance(addPengajuanRepository: AddPengajuanRepository = Injection.provideAddPengajuanRepository(),profileadmin: profileAdmin): ViewModelFactoryAddPengajuan = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryAddPengajuan(addPengajuanRepository,profileadmin) }.also { instance = it }
    }
}