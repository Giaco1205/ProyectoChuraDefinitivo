package com.example.sistema1232.content

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.sistema1232.MainActivity
import com.example.sistema1232.content.ui.theme.Sistema1232Theme
import com.example.sistema1232.utils.UserStore
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showAlertDialog by remember { mutableStateOf(false) }
            Sistema1232Theme {

                AlertDialog(
                    title = { Text("Cerrar sesión") },
                    text = { Text("¿Desea cerrar sesión?") },
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null) },
                    onDismissRequest = {},
                    confirmButton = {
                        ElevatedButton(onClick = {
                            cerrarSesion()
                            finish()
                            startActivity(Intent(this, LoginActivity::class.java))
                        }) {
                            Text("Si")
                        }
                    },
                    dismissButton = {
                        ElevatedButton(onClick = { /*TODO*/ }) {
                            Text("No")
                        }
                    }
                )
                Button(onClick = {
                    showAlertDialog = true
                }) {
                    Text("Cerrar sesión")
                }
            }
        }
    }

    private fun cerrarSesion() {
        val userStore = UserStore(this)
        lifecycleScope.launch {
            userStore.saveUser("")
        }
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}