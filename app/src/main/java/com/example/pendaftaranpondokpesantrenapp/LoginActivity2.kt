package com.example.pendaftaranpondokpesantrenapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pendaftaranpondokpesantrenapp.databinding.ActivityLogin2Binding

class LoginMainActivity2 : AppCompatActivity()  {
    private  lateinit var binding : ActivityLogin2Binding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var edt_email: EditText
    private lateinit var edt_confirm_password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityLogin2Binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        edt_email =  binding.emailLoginEditTextText
        edt_confirm_password = binding.confirmLoginditTextText
        val btnLogin1: Button = binding.loginMasukButton

        btnLogin1.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE)
            val emailSaved: String? = sharedPreferences.getString("email", "")
            val passwordSaved: String? = sharedPreferences.getString("password", "")

            //kondisi ketika memasukkan email dan password
            if(edt_email.text.toString() == emailSaved && edt_confirm_password.text.toString() == passwordSaved){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.daftarTextView.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}