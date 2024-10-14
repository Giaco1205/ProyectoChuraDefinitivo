package com.example.sistema1232.content

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.content.ui.theme.Sistema1232Theme
import org.json.JSONArray

class SuppliersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readService()

    }
    private fun readService(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/proveedores.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response -> //response tiene todos los datos del JSON, funciona como un arreglo del que se puede extraer cada elemento
                Log.d("VOLLEY", response)
                fillArray(response)
            },
            {

            })

        queue.add(stringRequest)
    }

    private fun fillArray(response: String) {
        val jsonArray = JSONArray(response)
        //Se están interpretando o traduciendo los datos, a un JSONArray
        val arrayList = ArrayList<HashMap<String, String>>()
        for (i in 0 until jsonArray.length()){
            val nombreempresa = jsonArray.getJSONObject(i).getString("nombreempresa")
            val nombrecontacto = jsonArray.getJSONObject(i).getString("nombrecontacto")
            val cargocontacto = jsonArray.getJSONObject(i).getString("cargocontacto")
            val ciudad = jsonArray.getJSONObject(i).getString("ciudad")
            val pais = jsonArray.getJSONObject(i).getString("pais")
            val hashmap = HashMap<String, String>()
            hashmap["nombreempresa"] = nombreempresa
            hashmap["nombrecontacto"] = nombrecontacto
            hashmap["cargocontacto"] = cargocontacto
            hashmap["ciudad"] = ciudad
            hashmap["pais"] = pais
            arrayList.add(hashmap)
        }
        arrayList.sortBy { it["nombreempresa"] }
        drawSuppliers(arrayList)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawSuppliers(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White,
                            ),
                            title = {
                                Text("Proveedores")
                            },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack, null,
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    Column (Modifier.padding(innerPadding)) {
                        Column {
                            AsyncImage(
                                model = "https://www.inpulseglobal.com/wp-content/uploads/2020/01/laptop.jpg",
                                contentDescription = "Translated description of what the image contains"
                            )
                            LazyColumn {
                                items(items = arrayList) { proveedor ->
                                    DrawSupplierItem(proveedor)
                                }
                            }
                        }//LazyColumn
                    }//Column
                }
            }
        }
    }
}

@Composable
fun DrawSupplierItem(proveedor: java.util.HashMap<String, String>) {
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
            text = proveedor["nombreempresa"].toString(),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = proveedor["nombrecontacto"].toString(),
            style = MaterialTheme.typography.titleMedium
        )
        Text(text = proveedor["cargocontacto"].toString())
        Text(text = proveedor["ciudad"].toString() + "/" + proveedor["pais"].toString())
    }
}