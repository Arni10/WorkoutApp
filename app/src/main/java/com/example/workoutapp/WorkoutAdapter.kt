package com.example.workoutapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.models.Workout
import com.example.workoutapp.views.ViewWorkoutActivity
import kotlinx.android.synthetic.main.item_workout_preview.view.*

class WorkoutAdapter(private val workoutList: ArrayList<Workout>) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_workout_preview, parent, false)
        return WorkoutViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {

        val currentItem = workoutList[position]
        holder.workoutName.text = currentItem.workoutName
        holder.workoutDate.text = currentItem.workoutDate.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ViewWorkoutActivity::class.java)
            intent.putExtra(ViewWorkoutActivity.workoutName, currentItem.workoutName)
            intent.putExtra(ViewWorkoutActivity.workoutBurnedCalories, currentItem.burnedCalories)
            intent.putExtra(ViewWorkoutActivity.workoutDate, currentItem.workoutDate)
            intent.putExtra(ViewWorkoutActivity.workoutDuration, currentItem.workoutDuration)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return workoutList.size
    }

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val workoutName = itemView.tvWorkoutNamePreview!!
        val workoutDate = itemView.tvWorkoutDatePreview!!

    }
}