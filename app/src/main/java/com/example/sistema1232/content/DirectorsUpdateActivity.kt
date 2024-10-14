package com.example.sistema1232.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.content.ui.theme.Sistema1232Theme

class DirectorsUpdateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val viddirector = bundle?.getString("iddirector")
        val vnombres = bundle?.getString("nombres")
        val vpeliculas = bundle?.getString("peliculas")
        enableEdgeToEdge()
        setContent {
            var iddirector by remember { mutableStateOf(viddirector.toString()) }
            var nombres by remember { mutableStateOf(vnombres.toString()) }
            var peliculas by remember { mutableStateOf(vpeliculas.toString()) }
            Sistema1232Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text("Directores")
                    }) { innerPadding ->
                    Column (
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){
                        Text("Actualizar Director", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = iddirector, onValueChange = {},
                            label = { Text("Codigo") })
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = nombres, onValueChange = {
                            nombres = it
                        },
                            label = { Text("Nombre del director") })
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(value = peliculas, onValueChange = {
                            peliculas = it
                        },
                            label = { Text("Peliculas") })
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            updateDirector(iddirector, nombres, peliculas)
                        }) {
                            Text(text = "Registrar")
                        }
                    }
                }
            }
        }
    }

    private fun updateDirector(iddirector: String, nombres: String, peliculas: String) {
        Log.d("VOLLEY", nombres + " - " + peliculas)
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/directoresinsert.php"

        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            { response ->
                Log.d("VOLLEY", response)
                startActivity(Intent(this, DirectorsActivity::class.java))
            },{
                // Manejo de errores (opcional)
                Log.e("VOLLEY", "Error occurred", it)
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["iddirector"] = iddirector
                params["nombres"] = nombres
                params["peliculas"] = peliculas
                return params
            }
        }
        queue.add(stringRequest)
    }
}