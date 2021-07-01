package com.example.workoutapp.controllers

import android.net.Uri
import com.example.workoutapp.models.Workout
import com.example.workoutapp.views.AddNewWorkoutActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AddNewWorkoutController(val view: AddNewWorkoutActivity) {

    private lateinit var currentUserID: String
    private val database = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var imageUri: Uri
    private lateinit var key: String

    fun saveWorkout(workoutName: String, workoutBurnedCalories: Double, workoutDate: String, workoutDuration: Int) {
        val workoutSaveDate = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        val workoutDateFormatted = simpleDateFormat.parse(workoutDate)
        val workout: Workout
        if (workoutName.isNotEmpty()) {
            workout = Workout(
                workoutName,
                workoutBurnedCalories,
                workoutDateFormatted,
                workoutDuration,
                workoutSaveDate
            )
        } else {
            workout = Workout(
                "workout_currentDate",
                workoutBurnedCalories,
                workoutDateFormatted,
                workoutDuration,
                workoutSaveDate
            )
        }
        currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        key = database.child(currentUserID).child("Workouts").push().key.toString()
        database.child(currentUserID).child("Workouts").child(key).setValue(workout).addOnSuccessListener {
            view.addNewWorkout()
        }.addOnFailureListener {
            view.addNewWorkoutError()
        }
    }

    fun uploadPicture(imageUri: Uri) {
        val formatter = SimpleDateFormat("yyyy.MM.dd")
        val now = Date()
        val fileName = formatter.format(now)

        val storage = FirebaseStorage.getInstance().getReference("images").child(currentUserID)
            .child("workouts").child(key).child(fileName)
        storage.putFile(imageUri).addOnSuccessListener {

        }.addOnFailureListener {
            view.uploadPictureError()
        }
    }

}