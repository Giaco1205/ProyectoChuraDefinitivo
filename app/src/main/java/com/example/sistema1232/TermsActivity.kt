package com.example.sistema1232

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.sistema1232.ui.theme.Sistema1232Theme

class TermsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sistema1232Theme {
                Column (
                    modifier = Modifier.padding(all = dimensionResource(id = R.dimen.size_3))
                ){
                    Text(text = stringResource(id = R.string.terms_conditions),
                        style = MaterialTheme.typography.headlineLarge)
                    Column (
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        Text(text = stringResource(id = R.string.terms_conditions_text))
                        Button(onClick = {
                            finish() //Cerrar el activity
                        }) {
                            Text(text = stringResource(id = R.string.close))
                        }
                    }

                }
            }
        }
    }
}
