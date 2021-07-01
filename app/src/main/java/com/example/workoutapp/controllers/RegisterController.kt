package com.example.workoutapp.controllers

import com.example.workoutapp.models.User
import com.example.workoutapp.views.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterController(private val view: RegisterActivity) {

    private lateinit var currentUserID: String
    private val database = FirebaseDatabase.getInstance().getReference("Users")

    fun saveUsername(username: String) {
        currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val user = User(username)
        database.child(currentUserID).setValue(user).addOnSuccessListener {
            view.registerUser()
        }.addOnFailureListener {
            view.registerError()
        }
    }
}