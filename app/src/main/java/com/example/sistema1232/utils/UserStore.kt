package com.example.sistema1232.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore (private val context: Context){
    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datos")
        val Datos_usuario = stringPreferencesKey("datos_usuario")
    }

    //Guardar datos
    suspend fun saveUser(datosUsuario: String){
        context.dataStore.edit { preferences ->
            preferences[Datos_usuario] = datosUsuario
        }
    }

    //Obtener datos
    val getUser: Flow<String> = context.dataStore.data
        .map { preferences ->
        preferences[Datos_usuario] ?: ""
    }
}