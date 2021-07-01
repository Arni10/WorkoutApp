package com.example.workoutapp.views

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.workoutapp.R
import com.example.workoutapp.controllers.AddNewWorkoutController
import com.example.workoutapp.models.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new_workout.*
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class AddNewWorkoutActivity : AppCompatActivity() {

    private val controller: AddNewWorkoutController = AddNewWorkoutController(this)
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var imageUri: Uri
    private lateinit var currentUserID: String
    lateinit var workoutName: String
    var workoutBurnedCalories: Double = 0.0
    lateinit var workoutDate: String
    var workoutDuration: Int = 0

    companion object{
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_workout)

        auth = FirebaseAuth.getInstance()
        currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
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
            imageUri = data?.data!!
            ivAddNewWorkoutChoosePicture.setImageURI(imageUri)
        }
    }

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
        workoutName = etAddNewWorkoutName.text.toString()
        workoutBurnedCalories = etAddNewWorkoutBurnedCalories.text.toString().toDouble()
        workoutDate = tvAddNewWorkoutDate.text.toString()
        workoutDuration = etAddNewWorkoutDuration.text.toString().toInt()

        if (workoutBurnedCalories.toString().isEmpty()) {
            etAddNewWorkoutBurnedCalories.error = getString(R.string.errorAddWorkoutBurnedCalories)
            etAddNewWorkoutBurnedCalories.requestFocus()
            return
        }
        if (workoutDate == getString(R.string.workoutDate)) {
            Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.errorAddWorkoutDate), Toast.LENGTH_LONG).show()
            return
        }
        if (workoutDuration.toString().isEmpty()) {
            etAddNewWorkoutDuration.error = getString(R.string.errorAddWorkoutDuration)
            etAddNewWorkoutDuration.requestFocus()
            return
        }
        controller.saveWorkout(workoutName, workoutBurnedCalories, workoutDate, workoutDuration)
        controller.uploadPicture(imageUri)
    }

    fun uploadPictureError() {
        Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.errorWorkoutPicture), Toast.LENGTH_LONG).show()
    }

    fun addNewWorkout() {
        Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.successSave), Toast.LENGTH_SHORT).show()
        val intent = Intent(this@AddNewWorkoutActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun addNewWorkoutError() {
        Toast.makeText(this@AddNewWorkoutActivity, getString(R.string.errorSaveWorkout), Toast.LENGTH_SHORT).show()
    }
}