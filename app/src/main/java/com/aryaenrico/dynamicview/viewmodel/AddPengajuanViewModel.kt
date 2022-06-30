package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.repository.AddPengajuanRepository
import kotlinx.coroutines.launch

class AddPengajuanViewModel(private val addPengajuanRepository: AddPengajuanRepository):ViewModel() {
    private var _pesan = MutableLiveData<Message>()
    val pesan : LiveData<Message> =_pesan

    fun pengajuan(id:String,status:String,jumlah:String,id_nasabah:String){
        viewModelScope.launch {
            _pesan.value =addPengajuanRepository.addPengajuan(id,status,jumlah,id_nasabah)
        }
    }
}
class ViewModelFactoryAddPengajuan(private val addPengajuanRepository: AddPengajuanRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AddPengajuanViewModel::class.java) -> {
                return AddPengajuanViewModel(addPengajuanRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryAddPengajuan? = null

        fun getInstance(addPengajuanRepository: AddPengajuanRepository = Injection.provideAddPengajuanRepository()): ViewModelFactoryAddPengajuan = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryAddPengajuan(addPengajuanRepository) }.also { instance = it }
    }
}