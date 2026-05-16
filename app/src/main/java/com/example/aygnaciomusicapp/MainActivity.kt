package com.example.aygnaciomusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aygnaciomusicapp.ui.theme.AYgnacioMusicAppTheme
import androidx.compose.material3.Surface
import com.example.aygnaciomusicapp.navigation.MusicAppNavigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AYgnacioMusicAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    MusicAppNavigation()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AYgnacioMusicAppTheme {
        Greeting("Android")
    }
}