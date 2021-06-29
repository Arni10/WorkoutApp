package com.example.workoutapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workoutapp.R

class ViewWorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_workout)

        supportActionBar?.hide()
    }
}