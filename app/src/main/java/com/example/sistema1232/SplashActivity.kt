package com.example.sistema1232

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sistema1232.ui.theme.Sistema1232Theme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var startAnimation by remember { mutableStateOf(false) }
            val offsetY by animateDpAsState(
                targetValue = if (startAnimation) 0.dp else 1500.dp,
                animationSpec = tween(durationMillis = 1000)
            )
            val alpha by animateFloatAsState(
                targetValue = if (startAnimation) 1f else 0f,
                animationSpec = tween(durationMillis = 3000)
            )
            val scale by animateFloatAsState(
                targetValue = if (startAnimation) 1.5f else 3f,
                animationSpec = tween(durationMillis = 2000)
            )
            Sistema1232Theme {
                LaunchedEffect(key1 = true) {
                    startAnimation = true
                    delay(4000)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
                Box (modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Image(painter = painterResource(id = R.drawable.logoo),
                        contentDescription = null,
                        modifier = Modifier.offset(y = offsetY)
                            .graphicsLayer (
                                alpha = alpha,
                                scaleX = scale,
                                scaleY = scale
                            )
                    )
                }
            }
        }
    }
}