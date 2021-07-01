package com.example.workoutapp.views

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.workoutapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_view_workout.*
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ViewWorkoutActivity : AppCompatActivity() {
    //@RequiresApi(Build.VERSION_CODES.O)

    companion object{
        const val workoutName = "workoutName"
        const val workoutBurnedCalories = "burnedCalories"
        const val workoutDate = "workoutDate"
        const val workoutDuration = "workoutDuration"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workout)

        if (intent.hasExtra(workoutName)) {
            tvWorkoutNameDetailDB.text = intent.getStringExtra(workoutName)
        }
        if (intent.hasExtra(workoutBurnedCalories)) {
            tvWorkoutBurnedCaloriesDetailDB.text = intent.getDoubleExtra(workoutBurnedCalories, 0.0).toString()
        }
        if (intent.hasExtra(workoutDate)) {
            tvWorkoutDateDetailDB.text = intent.getSerializableExtra(workoutDate).toString()
        }
        if (intent.hasExtra(workoutDuration)) {
            tvWorkoutDurationDetailDB.text = intent.getIntExtra(workoutDuration, 0).toString()
        }

        /*val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val key = FirebaseStorage.getInstance().getReference("images").child(currentUserID)
            .child("workouts").toString()
        val fileName = FirebaseStorage.getInstance().getReference("images").child(currentUserID)
            .child("workouts").child(key).toString()
        val storage = FirebaseStorage.getInstance().getReference("images").child(currentUserID)
            .child("workouts").child(key).child(fileName)

        val localFile = File.createTempFile("tempImage", "jpg")
        storage.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            ivWorkoutPictureDetailDB.setImageBitmap(bitmap)
        }.addOnCanceledListener {
            Toast.makeText(this@ViewWorkoutActivity, getString(R.string.errorWorkoutPictureDetail), Toast.LENGTH_LONG).show()
        }*/
    }

}