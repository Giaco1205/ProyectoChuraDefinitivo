package com.example.sistema1232

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sistema1232.content.DirectorsActivity
import com.example.sistema1232.content.EmployeesActivity
import com.example.sistema1232.content.LoginActivity
import com.example.sistema1232.content.StoreActivity
import com.example.sistema1232.content.SuppliersActivity
import com.example.sistema1232.ui.theme.Sistema1232Theme

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val etiquetas =
            arrayOf("Proveedores", "Empleados", "Tienda", "Clientes", "Directores", "Salir")
        val iconos = arrayOf(
            R.drawable.suppliers,
            R.drawable.employees,
            R.drawable.store,
            R.drawable.customers,
            R.drawable.moviedirector,
            R.drawable.exit
        )
        enableEdgeToEdge()
        setContent {
            Sistema1232Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color.White
                            ),
                            title = {
                                Text(stringResource(R.string.home))
                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {startActivity(Intent(this@HomeActivity, HomeActivity::class.java)) },
                                    icon = {Icon(imageVector = Icons.Default.Home, contentDescription = null)},
                                    label = {Text("Recursos")}
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {startActivity(Intent(this@HomeActivity, TermsActivity::class.java))},
                                    icon = {Icon(imageVector = Icons.Default.Add, contentDescription = null)},
                                    label = {Text("Recursos")}
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {startActivity(Intent(this@HomeActivity, BeginActivity::class.java))},
                                    icon = {Icon(imageVector = Icons.Default.Close, contentDescription = null)},
                                    label = {Text("Recursos")}
                                )
                            }
                        }
                    }//NavBottom
                ) { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            items(etiquetas.size) { index ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clickable {
                                            mostrarVentana(index)
                                        },
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 4.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = iconos[index]),
                                            contentDescription = null,
                                            modifier = Modifier.size(32.dp),
                                            colorFilter = ColorFilter.tint(Color.Black)
                                        )
                                        Text(etiquetas[index])
                                    }
                                }
                            } //items
                        }//LazyVerticalGrid
                    }
                }
            }
        }
    }

    private fun mostrarVentana(index: Int) {
        Log.d("PRUEBA", "Se ha seleccionado la opción $index")
        when (index) {
            0 -> startActivity(Intent(this, SuppliersActivity::class.java))
            1 -> startActivity(Intent(this, EmployeesActivity::class.java))
            2 -> startActivity(Intent(this, StoreActivity::class.java))
            3 -> startActivity(Intent(this, LoginActivity::class.java))
            4 -> startActivity(Intent(this, DirectorsActivity::class.java))
            else -> finish()
        }
    }
}