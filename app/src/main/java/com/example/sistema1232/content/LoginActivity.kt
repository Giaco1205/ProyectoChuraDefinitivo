package com.example.sistema1232.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.content.ui.theme.Sistema1232Theme
import com.example.sistema1232.utils.UserStore
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    var saveSession : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var usuario by remember { mutableStateOf("") }
            var clave by remember { mutableStateOf("") }
            var checkBoxState by remember { mutableStateOf(false) }
            Sistema1232Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text("Area de clientes")
                    }) { innerPadding ->
                    Column (
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = usuario, onValueChange = {
                            usuario = it
                        },
                            label = { Text("Nombre de usuario") })
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = clave, onValueChange = {
                            clave = it
                        },
                            label = { Text("Contraseña") },
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = "¿Desea guardar el inicio de sesión?")
                            Checkbox(checked = checkBoxState, onCheckedChange = {
                                checkBoxState = it
                                saveSession = it
                            })
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            checkLogin(usuario, clave)
                        }) {
                            Text(text = "Iniciar sesión")
                        }
                    }
                }
            }
        }
    }

    private fun checkLogin(usuario: String, clave: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/iniciarsesion.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("VOLLEY", response)
                //startActivity(Intent(this, DirectorsActivity::class.java))
                verifyLogin(response)
            }, {
                // Manejo de errores (opcional)
                Log.e("VOLLEY", "Error occurred", it)
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["usuario"] = usuario
                params["clave"] = clave
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun verifyLogin(response: String) {
        when (response) {
            "-1" -> Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
            "-2" -> Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
            else -> {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                verifySaveSession(response)
            }
        }
    }

    private fun verifySaveSession(response: String) {
        if (saveSession){
            val userStore = UserStore(this)
            lifecycleScope.launch {
                userStore.saveUser(response)
            }
        }
    }
}