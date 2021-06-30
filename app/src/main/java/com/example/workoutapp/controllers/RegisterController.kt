package com.example.workoutapp.controllers

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.workoutapp.R
import com.example.workoutapp.models.User
import com.example.workoutapp.views.HomeActivity
import com.example.workoutapp.views.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterController(val view: RegisterActivity) {

    private val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance().getReference("Users")

    fun saveUsername(username: String) {

        val user = User(username)
        database.child(currentUserID).setValue(user).addOnSuccessListener {
            view.registerUser()
        }.addOnFailureListener {
            view.registerError()
        }
    }
}