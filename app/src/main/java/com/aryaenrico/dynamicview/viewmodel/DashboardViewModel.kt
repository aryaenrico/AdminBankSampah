package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Admin
import com.aryaenrico.dynamicview.repository.DaftarAjuanRepository

class DashboardViewModel(private val profileAdmin: profileAdmin):ViewModel() {
    fun getProfileAdmin(): LiveData<Admin> {
        return profileAdmin.getProfile().asLiveData()
    }
}
class ViewModelFactoryDashboard(private val profileAdmin: profileAdmin) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                return DashboardViewModel(profileAdmin) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryDashboard? = null

        fun getInstance(profileadmin: profileAdmin): ViewModelFactoryDashboard = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryDashboard(profileadmin) }.also { instance = it }
    }


}