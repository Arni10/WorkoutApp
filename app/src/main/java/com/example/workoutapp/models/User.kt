package com.example.workoutapp.models

data class User(
    var email: String?,
    var password: String?,
    var username: String?,
    var workouts: ArrayList<Workout> = arrayListOf()
)
