package com.example.pendaftaranpondokpesantrenapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pendaftaranpondokpesantrenapp.databinding.ActivityLogin2Binding
import com.example.pendaftaranpondokpesantrenapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityRegisterBinding
    private lateinit var edt_fullname: EditText
    private lateinit var edt_email: EditText
    private lateinit var edt_password: EditText
    private lateinit var edt_confirm_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerButton.setOnClickListener {
            edt_fullname = binding.nameEditTextText;
            edt_email = binding.emailEditTextText;
            edt_password = binding.createEditTextText;
            edt_confirm_password = binding.confirmEditTextText;

            val sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("fullname", edt_fullname.text.toString())
            editor.putString("email", edt_email.text.toString())
            editor.putString("password", edt_password.text.toString())
            editor.putString("confirmpassword", edt_confirm_password.text.toString())
            editor.apply()

            Toast.makeText(this, "Registrasi Sukses! Silahkan Login", Toast.LENGTH_LONG).show()
            //  reset
            edt_fullname.setText("")
            edt_email.setText("")
            edt_password.setText("")
            edt_confirm_password.setText("")
        }


        binding.masukTextView.setOnClickListener{
            startActivity(Intent(this,LoginMainActivity2::class.java))
        }
    }
}