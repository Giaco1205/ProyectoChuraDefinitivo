package com.example.sistema1232.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.content.ui.theme.Sistema1232Theme
import org.json.JSONArray

class StoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
                readService()
    }
    private fun readService(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/categorias.php"

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
            val idCategoria = jsonArray.getJSONObject(i).getString("idcategoria")
            val nombre = jsonArray.getJSONObject(i).getString("nombre")
            val descripcion = jsonArray.getJSONObject(i).getString("descripcion")
            val foto = jsonArray.getJSONObject(i).getString("foto")
            val total = jsonArray.getJSONObject(i).getString("total")
            val hashmap = HashMap<String, String>()
            hashmap["idcategoria"] = idCategoria
            hashmap["nombre"] = nombre
            hashmap["descripcion"] = descripcion
            hashmap["foto"] = foto
            hashmap["total"] = total

            arrayList.sortBy { it["idcategoria"] }
            arrayList.add(hashmap)
        }
        drawCategories(arrayList)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawCategories(arrayList: ArrayList<HashMap<String, String>>) {
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
                                Text("Tienda")
                            },
                            navigationIcon = {
                                IconButton(onClick = {finish()}) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack, null,
                                        tint = Color.White)
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    Column (Modifier.padding(innerPadding)){
                        LazyColumn {
                            items(items = arrayList) { category ->
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .border(BorderStroke(1.dp, Color.Gray))
                                        .clickable {
                                            selectCategory(category)
                                        }) {
                                    DrawCategoryItem(category)
                                }
                            }
                        }//LazyColumn
                    }//Column
                }
            }
        }
    }
    private fun selectCategory(category: HashMap<String, String>) {
        val bundle = Bundle().apply { //Empaqueta los datos que se enviarán
            //a un nuevo activity
            putString("idcategoria", category["idcategoria"])
            putString("nombre", category["nombre"])
            putString("description", category["description"])
        }
        val intent = Intent(this, ProductsActivity::class.java)
        intent.putExtras(bundle) //Asi se envian los datos encapsulados en la clase
        startActivity(intent)
    }
}

@Composable
fun DrawCategoryItem(category: HashMap<String, String>) {
    Box (
        modifier = Modifier.fillMaxSize()
    ){
        AsyncImage(
            model = "https://servicios.campus.pe/" + category["foto"],
            contentDescription = "Imagen de la categoría",
            modifier = Modifier.height(190.dp).fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
                .height(190.dp)
                .background(Color(0x80000000))
        ) {
            Row (
                modifier = Modifier.padding(32.dp)
                    .fillMaxSize()
            ){
                Text(
                    text = category["idcategoria"].toString(),
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                    modifier = Modifier.width(60.dp)
                )
                Column{
                    Text(
                        text = category["nombre"].toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = category["descripcion"].toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Text(
                        text = "Total de productos: ${category["total"]}",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}