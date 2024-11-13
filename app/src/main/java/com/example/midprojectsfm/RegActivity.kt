package com.example.midprojectsfm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegActivity : AppCompatActivity() {

    private lateinit var db: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        val register = findViewById<Button>(R.id.t_reg)

        register.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

