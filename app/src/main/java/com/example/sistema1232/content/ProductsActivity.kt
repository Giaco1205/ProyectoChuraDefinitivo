package com.example.sistema1232.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Obtener el id de la categoría desde el intent
        val bundle = intent.extras
        val idcategoria = bundle?.getString("idcategoria")
        Log.d("ID CATEGORIA", idcategoria ?: "ID CATEGORIA ES NULL")

        readService(idcategoria) // Llamamos al servicio para obtener los productos
    }

    private fun readService(idcategoria: String?) {
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/productos.php?idcategoria=$idcategoria"
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
        Log.d("JSON RESPONSE", response)
        val jsonArray = JSONArray(response)
        val arrayList = ArrayList<HashMap<String, String>>()

        for (i in 0 until jsonArray.length()) {
            val product = jsonArray.getJSONObject(i)
            val idProducto = product.getString("idproducto")
            val nombreProducto = product.getString("nombre")
            val precio = product.getString("precio")
            val imagenChica = product.getString("imagenchica")
            val precioRebajado = product.getString("preciorebajado")

            val productMap = HashMap<String, String>()
            productMap["idproducto"] = idProducto
            productMap["nombre"] = nombreProducto
            productMap["precio"] = precio
            productMap["imagenchica"] = imagenChica
            productMap["preciorebajado"] = precioRebajado

            Log.d("PRODUCTO", productMap.toString()) // Verifica que los datos son correctos
            arrayList.add(productMap)
        }

        drawProducts(arrayList)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawProducts(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            com.example.sistema1232.content.ui.theme.Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White,
                            ),
                            title = {
                                Text("Productos")
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
                        LazyVerticalGrid(
                            modifier = Modifier.padding(8.dp),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items = arrayList) { product ->
                                Card (
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    modifier = Modifier
                                        .clickable {
                                            selectProduct(product["idproducto"])
                                        }
                                        .padding(5.dp)
                                        .height(320.dp)
                                ) {
                                    DrawProductItem(product)
                                }//Box
                            }//item
                        }//LazyColumn
                    }//Column
                }
            }
        }
    }

    private fun selectProduct(idproducto: String?) {
        startActivity(Intent(this, ProductDetailsActivity::class.java).apply{
            putExtra("idproducto", idproducto)
        })
    }


    @Composable
    fun DrawProductItem(product: HashMap<String, String>) {
        Box {
            val precioRebajado = product["preciorebajado"].toString().toFloat()
            val precio = product["precio"].toString().toFloat()
            val porcentajedescuento = (1 - precioRebajado / precio) * 100
            Column (
                modifier = Modifier.padding(4.dp)
            ){
                // Imagen del producto
                AsyncImage(
                    model = "https://servicios.campus.pe/" + product["imagenchica"],
                    contentDescription = "Imagen del producto",
                    modifier = Modifier.fillMaxWidth()
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
                    // Nombre del producto
                    Text(
                        text = product["nombre"].toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                    // Precio del producto
                    Text(
                        text = "Precio: ${product["precio"]} soles",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    // Precio rebajado, si está disponible
                    if (precioRebajado.toInt() != 0) {
                        Text(
                            text = "Precio Rebajado: $precioRebajado soles",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }
            }
            if(precioRebajado != 0f) {
                Text(
                    "%.0f".format(porcentajedescuento) + "%",
                    modifier = Modifier.background(Color.Red)
                        .padding(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),
                    color = Color.White)
            }
        }
    }
}
