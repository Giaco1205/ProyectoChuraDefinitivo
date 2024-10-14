package com.example.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sistema1232.ui.theme.Sistema1232Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1232Theme {
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.size_3)),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = R.string.phrase),
                        //style = TextStyle(fontSize = 36.sp)
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(text = stringResource(id = R.string.author))
                }
                Column (modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Text(text = stringResource(id = R.string.greeting),
                        //style = TextStyle(fontSize = 72.sp)
                        style = MaterialTheme.typography.displayLarge
                        )

                    ElevatedButton(onClick = {
                        startActivity(Intent(this@MainActivity, BeginActivity::class.java))
                    }) {
                        Text(text = stringResource(id = R.string.begin))
                    }
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = dimensionResource(id = R.dimen.size_2)
                    ),
                    contentAlignment = Alignment.BottomCenter){
                    Text(text = stringResource(id = R.string.rights))
                }
            }
        }
    }
}