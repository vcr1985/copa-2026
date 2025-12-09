package br.com.vandodev.copa_2022_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import br.com.vandodev.copa_2022_android.di.ViewModelFactory

class MainActivity : ComponentActivity() {

    private val container by lazy {
        (application as Copa2026AndroidApp).container
    }

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel)
        }
    }
}
