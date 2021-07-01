package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R
import com.example.workoutapp.controllers.UserProfileController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    private val controller: UserProfileController = UserProfileController(this)
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var username: String
    private lateinit var changeUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        auth = FirebaseAuth.getInstance()

        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID)

        getUsername()

        btnSaveUsernameChange.setOnClickListener{
            checkUsername()
        }

        btnSignOut.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this@UserProfileActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun getUsername(){
        database.get().addOnSuccessListener {
            if (it.exists()) {
                username = it.child("username").value.toString()
                etChangeUsername.setText(username)
            }
        }.addOnFailureListener {
            Toast.makeText(this@UserProfileActivity, getString(R.string.errorDisplayUsername), Toast.LENGTH_LONG).show()
        }
    }

    private fun checkUsername() {
        changeUsername = etChangeUsername.text.toString()

        if (changeUsername.isEmpty()) {
            etChangeUsername.error = getString(R.string.errorUsername)
            etChangeUsername.requestFocus()
            return
        }
        if (changeUsername == username) {
            etChangeUsername.error = getString(R.string.errorSameUsername)
            etChangeUsername.requestFocus()
            return
        }

        controller.updateUsername(changeUsername)

    }

    fun updateUser() {
        Toast.makeText(this@UserProfileActivity, getString(R.string.successChangeUsername), Toast.LENGTH_LONG).show()
        val intent = Intent(this@UserProfileActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun errorUpdateUser() {
        Toast.makeText(this@UserProfileActivity, getString(R.string.errorChangeUsername), Toast.LENGTH_LONG).show()
    }
}