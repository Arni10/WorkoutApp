package com.example.workoutapp.controllers

import com.example.workoutapp.views.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserProfileController(val view: UserProfileActivity) {

    private var currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID)

    fun updateUsername(changeUsername: String) {
        val user = mapOf<String,String>(
            "username" to changeUsername
        )
        database.updateChildren(user).addOnSuccessListener {
            view.updateUser()
        }.addOnFailureListener {
            view.errorUpdateUser()
        }
    }
}