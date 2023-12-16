package com.example.apiresttest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apiresttest.ui.theme.APIRestActivity
import com.example.apiresttest.ui.theme.ApiRestTestTheme

class MainActivity : ComponentActivity() {
    private lateinit var buttonApi : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        buttonApi = findViewById(R.id.buttonAPI)
        buttonApi.setOnClickListener{
            val intentApi = Intent(this, APIRestActivity::class.java)
            startActivity(intentApi)
        }
    }
}
