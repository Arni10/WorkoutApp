package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R
import com.example.workoutapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")

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
        if (etRegisterEmail.text.toString().isEmpty()) {
            etRegisterEmail.error = getString(R.string.errorEmail)
            etRegisterEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etRegisterEmail.text.toString()).matches()) {
            etRegisterEmail.error = getString(R.string.errorEmailPattern)
            etRegisterEmail.requestFocus()
            return
        }
        if (etRegisterPassword.text.toString().isEmpty()) {
            etRegisterPassword.error = getString(R.string.errorPassword)
            etRegisterPassword.requestFocus()
            return
        }
        if (etRegisterPasswordConfirmation.text.toString().isEmpty()) {
            etRegisterPasswordConfirmation.error = getString(R.string.errorPasswordConfirmation)
            etRegisterPasswordConfirmation.requestFocus()
            return
        }
        if (etRegisterPassword.text.toString() != etRegisterPasswordConfirmation.text.toString()) {
            etRegisterPasswordConfirmation.error = getString(R.string.errorPasswordMatch)
            etRegisterPasswordConfirmation.requestFocus()
            return
        }
        if (etRegisterUsername.text.toString().isEmpty()) {
            etRegisterUsername.error = getString(R.string.errorUsername)
            etRegisterUsername.requestFocus()
            return
        }
        if (!cbTermsAndConditions.isChecked) {
            Toast.makeText(this@RegisterActivity, getString(R.string.errorTermsAndConditions), Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(etRegisterEmail.text.toString(), etRegisterPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    registerUser()
                } else {
                    Toast.makeText(this@RegisterActivity, getString(R.string.errorRegister), Toast.LENGTH_SHORT).show()
                }
            }

    }
    
    private fun registerUser() {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val user = User(etRegisterEmail.text.toString(), etRegisterPassword.text.toString(), etRegisterUsername.text.toString())
        database.child(currentUserID).setValue(user).addOnSuccessListener {
            Toast.makeText(this@RegisterActivity, getString(R.string.successRegister), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this@RegisterActivity, getString(R.string.errorRegister), Toast.LENGTH_SHORT).show()
        }
    }
}