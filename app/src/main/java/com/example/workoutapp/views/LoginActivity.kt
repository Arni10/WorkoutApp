package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener{
            loginUser()
        }

        tvGoToRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun loginUser() {
        email = etLoginEmail.text.toString()
        password = etLoginPassword.text.toString()

        if (email.isEmpty()) {
            etLoginEmail.error = getString(R.string.errorEmail)
            etLoginEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etLoginEmail.error = getString(R.string.errorEmailPattern)
            etLoginEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            etLoginPassword.error = getString(R.string.errorPassword)
            etLoginPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(etLoginEmail.text.toString(), etLoginPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(this@LoginActivity, getString(R.string.errorLogin), Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}