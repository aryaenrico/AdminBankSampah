package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Admin
import com.aryaenrico.dynamicview.model.Login
import com.aryaenrico.dynamicview.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository,private val profileadmin: profileAdmin):ViewModel() {
    private var _loginResult = MutableLiveData<Login>()
    val loginResult : LiveData<Login> =_loginResult
    private var _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> =_loading

    fun login (notelp:String,password:String){
        viewModelScope.launch {
            _loginResult.value = loginRepository.login(notelp,password)
            setLoading(false)
        }
    }
    fun setDataAdmin(admin: Admin){
        viewModelScope.launch {
            profileadmin.setProfile(admin)
        }
    }

    fun getDataAdmin():LiveData<Admin>{
      return  profileadmin.getProfile().asLiveData()
    }
    fun setLoading(param:Boolean){
            _loading.value = param

    }
}
class ViewModelFactoryLogin(private val loginRepository: LoginRepository,private val profileAdmin: profileAdmin) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(loginRepository, profileAdmin) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryLogin? = null

        fun getInstance(
            loginRepository: LoginRepository = Injection.provideLoginRepository(),
            profileadmin: profileAdmin
        ): ViewModelFactoryLogin = ViewModelFactoryLogin.instance
            ?: synchronized(this) {
                ViewModelFactoryLogin.instance
                    ?: ViewModelFactoryLogin(loginRepository, profileadmin)
            }.also { ViewModelFactoryLogin.instance = it }
    }
}