package com.example.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sistema1232.ui.theme.Sistema1232Theme

class BeginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1232Theme {
                Column {
                    Box (contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.height(300.dp)){
                        Image(
                            modifier = Modifier.height(320.dp),
                            contentScale = ContentScale.Crop,
                            painter = painterResource(id = R.drawable.begin_image),
                            contentDescription = stringResource(id = R.string.begin_image_descripcion)
                        )
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f))
                        )
                        Text(text = stringResource(id = R.string.begin),
                            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.size_2)),
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White)
                    }
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = dimensionResource(id = R.dimen.size_3)),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = stringResource(id = R.string.begin),
                            style = MaterialTheme.typography.displayLarge)
                        Text(text = stringResource(id = R.string.welcome_text))
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = {
                                startActivity(Intent(this@BeginActivity, TermsActivity::class.java))
                            }) {
                                Text(text = stringResource(id = R.string.terms))
                            }
                            Button(onClick = {
                                startActivity(Intent(this@BeginActivity, HomeActivity::class.java))
                            }) {
                                Text(text = stringResource(id = R.string.home))
                            }
                        }
                    }
                }
            }
        }
    }
}