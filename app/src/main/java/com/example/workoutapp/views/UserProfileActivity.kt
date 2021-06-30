package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.example.workoutapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        auth = FirebaseAuth.getInstance()

        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        database = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID)

        getUsername()

        btnSaveUsernameChange.setOnClickListener{
            updateUsername()
        }

        btnSignOut.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this@UserProfileActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun getUsername() {
        database.get().addOnSuccessListener {
            if (it.exists()) {
                username = it.child("username").value.toString()
                etChangeUsername.setText(username)
            }
        }.addOnFailureListener {
            Toast.makeText(this@UserProfileActivity, getString(R.string.errorDisplayUsername), Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUsername() {
        if (etChangeUsername.text.toString().isEmpty()) {
            etChangeUsername.error = getString(R.string.errorUsername)
            etChangeUsername.requestFocus()
            return
        }
        if (etChangeUsername.text.toString() == username) {
            etChangeUsername.error = getString(R.string.errorSameUsername)
            etChangeUsername.requestFocus()
            return
        }
        val user = mapOf<String,String>(
            "username" to etChangeUsername.text.toString()
        )
        //database.child("username").setValue()
        database.updateChildren(user).addOnSuccessListener {
            Toast.makeText(this@UserProfileActivity, getString(R.string.successChangeUsername), Toast.LENGTH_LONG).show()
            val intent = Intent(this@UserProfileActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this@UserProfileActivity, getString(R.string.errorChangeUsername), Toast.LENGTH_LONG).show()
        }
    }
}