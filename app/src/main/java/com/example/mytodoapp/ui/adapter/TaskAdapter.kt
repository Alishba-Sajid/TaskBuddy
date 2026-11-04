package com.example.mytodoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodoapp.databinding.ItemTaskBinding
import com.example.mytodoapp.domain.model.Task

class TaskAdapter(
    private val onEditClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {

        val binding = ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        val task = getItem(position)

        holder.bind(task, onEditClick, onDeleteClick)
    }

    class TaskViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            task: Task,
            onEditClick: (Task) -> Unit,
            onDeleteClick: (Task) -> Unit
        ) {
            binding.tvTaskTitle.text = task.title

            if (task.description.isBlank()) {
                binding.tvTaskDescription.visibility = View.GONE
            } else {
                binding.tvTaskDescription.visibility = View.VISIBLE
                binding.tvTaskDescription.text = task.description
            }

            binding.btnEditTask.setOnClickListener {
                onEditClick(task)
            }

            binding.btnDeleteTask.setOnClickListener {
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