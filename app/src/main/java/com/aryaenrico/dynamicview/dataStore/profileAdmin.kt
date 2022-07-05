package com.aryaenrico.dynamicview.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aryaenrico.dynamicview.model.Admin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class profileAdmin (private  val dataStore: DataStore<Preferences>){
    private val IS_LOGIN = booleanPreferencesKey("islogin")
    private val ID = stringPreferencesKey("id")
    private val ALAMAT = stringPreferencesKey("alamat")
    private val NO_TELP = stringPreferencesKey("notelp")
    private val NAMA = stringPreferencesKey("nama")


    fun getProfile(): Flow<Admin> {
        return dataStore.data.map { preferences->
            Admin(id_admin = preferences[ID]?:"",
                nama = preferences[NAMA]?:"",
                alamat = preferences[ALAMAT]?:"",
                no_telepon = preferences[NO_TELP]?:"",
                is_login = preferences[IS_LOGIN]?:false
                )

        }
    }
    suspend fun setProfile(admin: Admin){
        dataStore.edit {
            it[IS_LOGIN] = admin.is_login
            it[ID] = admin.id_admin
            it[ALAMAT]=admin.alamat
            it[NO_TELP]=admin.no_telepon
            it[NAMA]=admin.nama
        }
    }

    companion object{
        // data tidak akan masuk ke dalam cache memory
        @Volatile
        private var INSTANCE:profileAdmin? = null

        // private val Context.Datastore:DataStore<Preferences> by preferencesDataStore(name="setting")

        // untuk membuat singleton object pada datastore
        fun getInstance (dataStore: DataStore<Preferences>):profileAdmin{
            return INSTANCE?: synchronized(this){
                val instance = profileAdmin(dataStore)
                INSTANCE =instance
                instance
            }
        }
    }
}
