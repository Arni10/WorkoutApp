package com.example.workoutapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.WorkoutAdapter
import com.example.workoutapp.models.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var workoutRecyclerView: RecyclerView
    private lateinit var workoutArrayList: ArrayList<Workout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid

        database = FirebaseDatabase.getInstance().getReference("Users").child(currentUserID)

        rvWorkoutHistory.layoutManager = LinearLayoutManager(this)
        rvWorkoutHistory.setHasFixedSize(true)

        workoutArrayList = arrayListOf<Workout>()
        getWorkouts()

        fabNewWorkout.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@HomeActivity, AddNewWorkoutActivity::class.java)
            startActivity(intent)
        })

        btnSignOut.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun getWorkouts() {

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (workoutSnapshot in snapshot.children) {
                        val workout: Workout = workoutSnapshot.getValue(Workout::class.java)!!
                        workoutArrayList.add(workout)
                    }
                    rvWorkoutHistory.adapter = WorkoutAdapter(workoutArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
}