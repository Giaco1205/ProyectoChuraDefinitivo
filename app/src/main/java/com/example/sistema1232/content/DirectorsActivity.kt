package com.example.sistema1232.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.content.ui.theme.Sistema1232Theme
import org.json.JSONArray

class DirectorsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readService()
        enableEdgeToEdge()
    }

    private fun readService() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/directores.php"
        Log.d("URL", url)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("VOLLEY", response)
                fillArray(response) // Llamamos a fillArray para procesar la respuesta
            },
            {
                // Manejo de errores (opcional)
                Log.e("VOLLEY", "Error occurred", it)
            }
        )
        queue.add(stringRequest)
    }

    private fun fillArray(response: String) {
        val jsonArray = JSONArray(response)
        //Se están interpretando o traduciendo los datos, a un JSONArray
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()) {
            val iddirector = jsonArray.getJSONObject(i).getString("iddirector")
            val nombres = jsonArray.getJSONObject(i).getString("nombres")
            val peliculas = jsonArray.getJSONObject(i).getString("peliculas")
            val hashmap = HashMap<String, String>()
            hashmap["iddirector"] = iddirector
            hashmap["nombres"] = nombres
            hashmap["peliculas"] = peliculas
            arrayList.add(hashmap)
        }
        arrayList.sortBy { it["id"]?.toIntOrNull() }
        drawDirectors(arrayList)
    }

    private fun drawDirectors(arrayList: java.util.ArrayList<java.util.HashMap<String, String>>) {
        setContent {
            Sistema1232Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Text("Directores")
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            startActivity(Intent(this, DirectorsInsertActivity::class.java))
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        LazyColumn {
                            items(items = arrayList) { director ->
                                Box (Modifier.clickable {
                                    val bundle = Bundle().apply {
                                        putString("iddirector", director["iddirector"])
                                        putString("nombres", director["nombres"])
                                        putString("peliculas", director["peliculas"])
                                    }
                                    startActivity(Intent(this@DirectorsActivity, DirectorsUpdateActivity::class.java).apply {
                                        putExtras(bundle)
                                    })
                                }){
                                    DrawDirectorItem(director)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DrawDirectorItem(director: java.util.HashMap<String, String>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.dp, color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = director["iddirector"].toString(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = director["nombres"].toString(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = director["peliculas"].toString())
        }
    }
}