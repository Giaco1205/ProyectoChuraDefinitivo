package com.example.sistema1232.content

import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.content.ui.theme.Sistema1232Theme
import org.json.JSONArray

class ProductDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = intent.extras
        val idproducto = bundle?.getString("idproducto")
        // Llamamos al servicio para obtener los detalles del producto
        val queue = Volley.newRequestQueue(this)
        val url = "https://servicios.campus.pe/productos.php?idproducto=$idproducto"
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
    //Fin del llamado al servicio

    // Fill Array
    @OptIn(ExperimentalMaterial3Api::class)
    private fun fillArray(response: String) {
        val jsonArray = JSONArray(response)
        val idproducto = jsonArray.getJSONObject(0).getString("idproducto")
        val nombre = jsonArray.getJSONObject(0).getString("nombre")
        val descripcion = jsonArray.getJSONObject(0).getString("descripcion")
        val precio = jsonArray.getJSONObject(0).getString("precio")
        val preciorebajado = jsonArray.getJSONObject(0).getString("preciorebajado")
        val imagengrande = jsonArray.getJSONObject(0).getString("imagengrande")
        val detalle = jsonArray.getJSONObject(0).getString("detalle")
        val categoria = jsonArray.getJSONObject(0).getString("categoria")
        val proveedor = jsonArray.getJSONObject(0).getString("proveedor")
        val unidadesenexistencia = jsonArray.getJSONObject(0).getString("unidadesenexistencia")
        val pais = jsonArray.getJSONObject(0).getString("pais")
        val telefono = jsonArray.getJSONObject(0).getString("telefono")
        //Fin del FillArray

        setContent {
            Sistema1232Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White,
                            ),
                            title = {
                                Text(nombre)
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
                    }) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally, // Centrar horizontalmente
                        verticalArrangement = Arrangement.Center // Centrar verticalmente
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.8f) // Ajusta el ancho al 80% del tamaño de la pantalla
                                .height(500.dp), // Aumenta la altura para dar espacio al botón
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Sombra
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp), // Margen interno del Card
                                verticalArrangement = Arrangement.SpaceBetween // Distribuye el espacio
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f), // Le da peso para ajustar el espacio entre los elementos
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = nombre,
                                        style = MaterialTheme.typography.headlineLarge // Aplica un estilo de texto grande
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    AsyncImage(
                                        model = "https://servicios.campus.pe/$imagengrande",
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth() // Imagen ocupará todo el ancho disponible
                                            .weight(1f) // Distribuye el espacio en la columna
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = HtmlCompat.fromHtml(
                                            detalle,
                                            HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_DIV
                                        ).toString(),
                                        modifier = Modifier
                                            .padding(top = 8.dp), // Añade espacio entre la imagen y el texto
                                        style = MaterialTheme.typography.headlineMedium // Aplica un estilo de texto visible
                                    )
                                    Text(
                                        text = "Precio: $precio soles",
                                        style = MaterialTheme.typography.titleMedium // Aplica un estilo de texto grande
                                    )
                                }

                                // Botón en la parte inferior
                                Button(
                                    onClick = { /* Acciones de compra */ },
                                    modifier = Modifier.fillMaxWidth() // Botón ocupará todo el ancho
                                ) {
                                    Text(text = "Compra")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}