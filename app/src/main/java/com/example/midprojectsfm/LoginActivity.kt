package com.example.midprojectsfm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<Button>(R.id.t_login)

        login.setOnClickListener {
            val intent = Intent(this, MainStudentInfo::class.java)
            startActivity(intent)
        }
    }
}
