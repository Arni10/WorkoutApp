package com.example.workoutapp.controllers

import com.example.workoutapp.WorkoutAdapter
import com.example.workoutapp.models.Workout
import com.example.workoutapp.views.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*

class HomeController(val view: HomeActivity)  {

    private val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
    private val database = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID)

    fun getWorkouts() {
        val workoutArrayList = arrayListOf<Workout>()
        database.child("Workouts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (workoutSnapshot in snapshot.children) {
                        workoutSnapshot.key
                        val workout: Workout? = workoutSnapshot.getValue(Workout::class.java)
                        workout?.let {
                            workoutArrayList.add(workout)
                        }
                    }
                    view.showWorkouts(workoutArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}