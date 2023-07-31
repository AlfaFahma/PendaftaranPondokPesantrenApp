package com.example.pendaftaranpondokpesantrenapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginMainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLoginListener()
        btnRegister()
    }
    private fun btnLoginListener(){
        val button = findViewById(R.id.loginAwalButton) as Button
        button.setOnClickListener{
            startActivity(Intent(this,LoginMainActivity2::class.java))
        }
    }
    private fun btnRegister(){
        val button = findViewById(R.id.registerAwalButton) as Button
        button.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}