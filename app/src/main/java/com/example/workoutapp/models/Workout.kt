package com.example.workoutapp.models

import java.util.*

data class Workout(
    var workoutName: String? = null,
    var burnedCalories: Double? = null,
    var workoutDate: Date? = null,
    var workoutDuration: Int? = null,
    var workoutAddingDate: Date? = null
)

