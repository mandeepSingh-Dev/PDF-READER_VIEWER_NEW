package com.example.pdf_reader_viewer.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pdf_reader_viewer.R
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_activity)

         supportActionBar?.hide()


        CoroutineScope(Dispatchers.IO).launch {
            delay(300)

            startActivity(Intent(applicationContext,
                com.example.pdf_reader_viewer.MainActivity_ViewPagerHolder::class.java))
            finish()
        }
    }
}