package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.DaftarAjuan
import com.aryaenrico.dynamicview.repository.DaftarAjuanRepository
import kotlinx.coroutines.launch

class DaftarAjuanViewModel(private val daftarAjuanRepository: DaftarAjuanRepository):ViewModel() {
    private var _ajuan = MutableLiveData<ArrayList<DaftarAjuan>>()
    val ajuan : LiveData<ArrayList<DaftarAjuan>> =_ajuan


    fun getAjuan(){
        viewModelScope.launch {
            _ajuan.value = daftarAjuanRepository.getAjuan()
        }
    }
}

class ViewModelFactoryDaftarAjuan(private val daftarAjuanRepository: DaftarAjuanRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DaftarAjuanViewModel::class.java) -> {
                return DaftarAjuanViewModel(daftarAjuanRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryDaftarAjuan? = null

        fun getInstance(daftarAjuanRepository: DaftarAjuanRepository = Injection.provideRepositoryDaftarAjuan()): ViewModelFactoryDaftarAjuan = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryDaftarAjuan(daftarAjuanRepository) }.also { instance = it }
    }


}