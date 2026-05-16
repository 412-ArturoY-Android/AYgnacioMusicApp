package com.example.aygnaciomusicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aygnaciomusicapp.ui.theme.AYgnacioMusicAppTheme
import androidx.compose.material3.Surface
import com.example.aygnaciomusicapp.navigation.MusicAppNavigation
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.Coil




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient {
                okhttp3.OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .header("User-Agent", "Mozilla/5.0 (Linux; Android 10) AYgnacioMusicApp/1.0")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)
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


