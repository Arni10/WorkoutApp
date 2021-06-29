package com.example.workoutapp.views

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workoutapp.R
import com.example.workoutapp.models.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new_workout.*
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class AddNewWorkoutActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_workout)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance().getReference("Users")

        btnAddNewWorkoutDate.setOnClickListener {
            showDatePickerDialog()
        }

        btnAddNewWorkoutChoosePicture.setOnClickListener {
            choosePictureFromGallery()
        }

        btnSaveWorkout.setOnClickListener {
            checkWorkout()
        }
    }

    private fun choosePictureFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            ivAddNewWorkoutChoosePicture.setImageURI(data?.data)
        }
    }

    //@SuppressLint("ResourceType")
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)



        val datePickerDialog = DatePickerDialog(
            this@AddNewWorkoutActivity,
                   R.style.WorkoutDatePickerDialog,
                   { _, mYear, mMonth, mDay ->
                       val date = "" + mYear + "." + (mMonth + 1) + "." + mDay
                       tvAddNewWorkoutDate.text = date
                   }, year, month, day)
        datePickerDialog.show()
    }

    private fun checkWorkout() {
        if (etAddNewWorkoutBurnedCalories.text.toString().isNullOrEmpty()) {
            etAddNewWorkoutBurnedCalories.error = getString(R.string.errorAddWorkoutBurnedCalories)
            etAddNewWorkoutBurnedCalories.requestFocus()
            return
        }
        if (tvAddNewWorkoutDate.text.toString() == getString(R.string.workoutDate)) {
            Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.errorAddWorkoutDate), Toast.LENGTH_LONG).show()
            return
        }
        if (etAddNewWorkoutDuration.text.toString().isNullOrEmpty()) {
            etAddNewWorkoutDuration.error = getString(R.string.errorAddWorkoutDuration)
            etAddNewWorkoutDuration.requestFocus()
            return
        }
        saveWorkout()
    }

    private fun saveWorkout() {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val workoutSaveDate = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
        val workoutDate = simpleDateFormat.parse(tvAddNewWorkoutDate.text.toString())
        val workout: Workout
        if (etAddNewWorkoutName.text.toString().isNotEmpty()) {
                workout = Workout(
                    etAddNewWorkoutName.text.toString(),
                    etAddNewWorkoutBurnedCalories.text.toString().toDouble(),
                    workoutDate,
                    etAddNewWorkoutDuration.text.toString().toInt(),
                    workoutSaveDate
                )
        } else {
                workout = Workout(
                    getString(R.string.workoutNameDefault),
                    etAddNewWorkoutBurnedCalories.text.toString().toDouble(),
                    workoutDate,
                    etAddNewWorkoutDuration.text.toString().toInt(),
                    workoutSaveDate
                )
        }
        val key = database.child(currentUserID).child("Workouts").push().key
        database.child(currentUserID).child("Workouts").child(key.toString()).setValue(workout).addOnSuccessListener {
            Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.successSave), Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AddNewWorkoutActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }.addOnFailureListener {
            Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.errorSaveWorkout), Toast.LENGTH_SHORT).show()
        }
    }
}