package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.MutasiTransaksiData
import com.aryaenrico.dynamicview.repository.MutasiTransaksiRepository
import kotlinx.coroutines.launch

class MutasiTransaksiViewModel(private val mutasiTransaksiRepository: MutasiTransaksiRepository):ViewModel() {

    private var _data = MutableLiveData<ArrayList<MutasiTransaksiData>>()
    val data : LiveData<ArrayList<MutasiTransaksiData>> =_data

    fun getMutasiTransaksi(awal:String,akhir:String){
        viewModelScope.launch {
           _data.value = mutasiTransaksiRepository.getMutasiTransaksi(awal, akhir)
        }
    }
}
class ViewModelFactoryMutasiTransaksi(private val mutasiTransaksiRepository: MutasiTransaksiRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MutasiTransaksiViewModel::class.java) -> {
                return MutasiTransaksiViewModel(mutasiTransaksiRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryMutasiTransaksi? = null
        fun getInstance(mutasiTransaksiRepository: MutasiTransaksiRepository =Injection.provideRepositoryMutasiTransaksi()): ViewModelFactoryMutasiTransaksi = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryMutasiTransaksi(mutasiTransaksiRepository) }.also { instance = it }
    }
}