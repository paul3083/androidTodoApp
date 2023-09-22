package com.example.todoapplication.todo.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.data.Task

class TodoAdapter(
    private val list: List<Task>,
    private val onItemClick: (Task) -> Unit,
    private val onItemLongClick: (Task) -> Unit,
    private val onCheckedChange: (Task) -> Unit
    ) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder(
        private val view: View,
        val onItemClick: (Task) -> Unit,
        val onItemLongClick: (Task) -> Unit,
        val onCheckedChange: (Task) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title_text)
        private val isCompleted: CheckBox = view.findViewById(R.id.complete_checkbox)
        fun bind(task: Task) {
            title.text  = task.title
            isCompleted.isChecked = task.isCompleted
            view.setOnClickListener{ onItemClick(task)}
            view.setOnLongClickListener {
                onItemLongClick(task)
                true
            }
            isCompleted.setOnCheckedChangeListener { _, boolean ->
                onCheckedChange(task.copy(isCompleted = boolean))
            }
        }
        companion object {
            fun createViewHolder(
                parent: ViewGroup,
                onItemClick: (Task) -> Unit,
                onItemLongClick: (Task) -> Unit,
                onCheckedChange: (Task) -> Unit
            ): TodoViewHolder {
                val layout =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.task_item, parent, false)
                return TodoViewHolder(layout, onItemClick, onItemLongClick, onCheckedChange)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.createViewHolder(
            parent,
            onItemClick,
            onItemLongClick,
            onCheckedChange
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
