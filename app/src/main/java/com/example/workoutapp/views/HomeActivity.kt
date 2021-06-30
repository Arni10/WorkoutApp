package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.WorkoutAdapter
import com.example.workoutapp.controllers.HomeController
import com.example.workoutapp.models.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val controller: HomeController = HomeController(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        controller.getWorkouts()

        fabNewWorkout.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HomeActivity, AddNewWorkoutActivity::class.java)
            startActivity(intent)
        })

        fabUserProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HomeActivity, UserProfileActivity::class.java)
            startActivity(intent)
        })

    }

    fun showWorkouts(workouts: ArrayList<Workout>) {
        rvWorkoutHistory.layoutManager = LinearLayoutManager(this)
        rvWorkoutHistory.setHasFixedSize(true)
        rvWorkoutHistory.adapter = WorkoutAdapter(workouts)
    }
}