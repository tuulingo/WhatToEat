package com.tuulingo.whattoeat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuulingo.whattoeat.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = Firebase.auth

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGotoSignup.setOnClickListener { switchToSignUpActivity() }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                signIn(email, password)
            }else{
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun switchToSignUpActivity() {
        val switchToSignUpActivityIntent = Intent(this, SignUpActivity::class.java)
        startActivity(switchToSignUpActivityIntent)
    }

    private fun switchToMainAppActivity() {
        val switchToMainAppActivityIntent = Intent(this, MainActivity::class.java)
        startActivity(switchToMainAppActivityIntent)
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            switchToMainAppActivity()
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    switchToMainAppActivity()
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {

    }


    companion object {
        private const val TAG = "EmailPassword"
    }

}