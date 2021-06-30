package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R
import com.example.workoutapp.controllers.RegisterController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val controller: RegisterController = RegisterController(this)
    lateinit var email: String
    lateinit var password: String
    lateinit var passwordConfirmation: String
    lateinit var username: String
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener{
            checkUser()
        }

        tvGoToLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            }
        )
    }

    private fun checkUser() {
        email = etRegisterEmail.text.toString()
        password = etRegisterPassword.text.toString()
        passwordConfirmation = etRegisterPasswordConfirmation.text.toString()
        username = etRegisterUsername.text.toString()

        if (email.isEmpty()) {
            etRegisterEmail.error = getString(R.string.errorEmail)
            etRegisterEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etRegisterEmail.error = getString(R.string.errorEmailPattern)
            etRegisterEmail.requestFocus()
            return
        }
        if (password.isEmpty()) {
            etRegisterPassword.error = getString(R.string.errorPassword)
            etRegisterPassword.requestFocus()
            return
        }
        if (passwordConfirmation.isEmpty()) {
            etRegisterPasswordConfirmation.error = getString(R.string.errorPasswordConfirmation)
            etRegisterPasswordConfirmation.requestFocus()
            return
        }
        if (password != passwordConfirmation) {
            etRegisterPasswordConfirmation.error = getString(R.string.errorPasswordMatch)
            etRegisterPasswordConfirmation.requestFocus()
            return
        }
        if (username.isEmpty()) {
            etRegisterUsername.error = getString(R.string.errorUsername)
            etRegisterUsername.requestFocus()
            return
        }
        if (!cbTermsAndConditions.isChecked) {
            Toast.makeText(this@RegisterActivity, getString(R.string.errorTermsAndConditions), Toast.LENGTH_LONG).show()
            return
        }

        createUser()

    }

    private fun createUser() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    controller.saveUsername(username)
                } else {
                    Toast.makeText(this@RegisterActivity, getString(R.string.errorRegister), Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun registerUser() {
        Toast.makeText(this@RegisterActivity, getString(R.string.successRegister), Toast.LENGTH_SHORT).show()
        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()

    }
    fun registerError() {
        Toast.makeText(this@RegisterActivity, getString(R.string.errorRegister), Toast.LENGTH_SHORT).show()
    }

}