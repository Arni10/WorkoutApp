package com.example.workoutapp.controllers

import com.example.workoutapp.views.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginController(val view: LoginActivity) {

    private val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance().getReference("Users")
    private val auth = FirebaseAuth.getInstance()



}