package com.example.workoutapp.models

data class User(
    var username: String?,
    var workouts: ArrayList<Workout> = arrayListOf()
)
