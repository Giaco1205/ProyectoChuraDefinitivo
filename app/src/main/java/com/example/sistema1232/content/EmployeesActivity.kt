package com.example.sistema1232.content

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistema1232.utils.Total
import org.json.JSONArray

class EmployeesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readService()

    }
    private fun readService(){
        val queue = Volley.newRequestQueue(this)
        val url = Total.BASE_URL + "empleados.php"

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
            val nombreempleado = jsonArray.getJSONObject(i).getString("nombres")
            val apellidoempleado = jsonArray.getJSONObject(i).getString("apellidos")
            val cargoempleado = jsonArray.getJSONObject(i).getString("cargo")
            val foto = jsonArray.getJSONObject(i).getString("foto")
            val hashmap = HashMap<String, String>()
            hashmap["nombres"] = nombreempleado
            hashmap["apellidos"] = apellidoempleado
            hashmap["cargo"] = cargoempleado
            hashmap["foto"] = foto
            arrayList.add(hashmap)
        }
        drawSuppliers(arrayList)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    private fun drawSuppliers(arrayList: ArrayList<HashMap<String, String>>) {
        setContent {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp
            val density = LocalDensity.current.density
            val screenHeightPx = screenHeight * density
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = Color.Black,
                            titleContentColor = Color.White,
                        ),
                        title = {
                            Text("Empleados")
                        },
                        navigationIcon = {
                            IconButton(onClick = {finish()}) {
                                androidx.compose.material3.Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack, null,
                                    tint = Color.White
                                )
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    LazyRow {
                        items(items = arrayList) { employee ->
                            Box(
                                modifier = Modifier.fillParentMaxSize()
                            ) {
                                DrawEmployee(employee, screenHeightPx)
                            }
                        }
                    }//LazyColumn
                }//Column
            }
        }
    }
}

@Composable
fun DrawEmployee(employee: java.util.HashMap<String, String>, screenHeightPx: Float) {
    AsyncImage(
        model = Total.BASE_URL + "fotos/" + employee["foto"].toString(),
        contentDescription = "null",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = screenHeightPx * 0.1f,
                    endY = screenHeightPx * 1f
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = employee["nombres"].toString(),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )
        Text(
            text = employee["apellidos"].toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Text(
            text = employee["cargo"].toString(),
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
    }
}