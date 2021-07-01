package com.example.workoutapp.controllers

import android.content.Intent
import android.widget.Toast
import com.example.workoutapp.R
import com.example.workoutapp.views.HomeActivity
import com.example.workoutapp.views.UserProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileController(val view: UserProfileActivity) {

    private var currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID)
    private var changedUsername: String = "1"



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