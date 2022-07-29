package com.tuulingo.whattoeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val btn_SignUp: Button = findViewById(R.id.btn_signup)
        btn_SignUp.setOnClickListener { switchToSignUpActivity() }
    }

    private fun switchToSignUpActivity() {
        val switchActivityIntent = Intent(this, SignUpActivity::class.java)
        startActivity(switchActivityIntent)
    }
}