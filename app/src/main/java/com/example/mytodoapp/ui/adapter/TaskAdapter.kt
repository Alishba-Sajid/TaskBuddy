
package com.example.mytodoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.R
import com.example.mytodoapp.domain.model.Task

class TaskAdapter(
    private val onEditClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = getItem(position)

        holder.bind(task, onEditClick, onDeleteClick)
    }

    class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val taskTitle = itemView.findViewById<TextView>(
            R.id.tvTaskTitle
        )

        private val taskDescription = itemView.findViewById<TextView>(
            R.id.tvTaskDescription
        )

        private val editButton = itemView.findViewById<ImageButton>(
            R.id.btnEditTask
        )

        private val deleteButton = itemView.findViewById<ImageButton>(
            R.id.btnDeleteTask
        )

        fun bind(
            task: Task,
            onEditClick: (Task) -> Unit,
            onDeleteClick: (Task) -> Unit
        ) {

            taskTitle.text = task.title

            if (task.description.isBlank()) {
                taskDescription.visibility = View.GONE
            } else {
                taskDescription.visibility = View.VISIBLE
                taskDescription.text = task.description
            }

            editButton.setOnClickListener {
                onEditClick(task)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(task)
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean {
            return oldItem == newItem
        }
    }
}
