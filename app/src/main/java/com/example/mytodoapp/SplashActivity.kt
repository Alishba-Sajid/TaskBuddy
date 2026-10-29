package com.example.mytodoapp
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


    class SplashActivity : AppCompatActivity() {

        private val handler = Handler(Looper.getMainLooper())

        private val navigateRunnable = Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

            handler.postDelayed(navigateRunnable, 1500)
        }

        override fun onDestroy() {
            handler.removeCallbacks(navigateRunnable)
            super.onDestroy()
        }
    }

