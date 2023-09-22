package com.example.todoapplication.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.data.Task
import com.example.todoapplication.data.ToDoDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskId = arguments?.getString("taskId")
        val dataSource = ToDoDatabase.getInstance(requireContext()).taskDao()
        val title: TextView = view.findViewById(R.id.add_task_title_edit_text)
        val description: TextView = view.findViewById(R.id.add_task_description_edit_text)
        val saveButton: FloatingActionButton = view.findViewById(R.id.save_task_fab)
        if(taskId != null) {
            val task = dataSource.observeTaskById(taskId).observe(viewLifecycleOwner) {
                title.setText(it.title)
                description.setText(it.description)
            }
        }
        saveButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                if(taskId != null) {
                    dataSource.updateTask(Task(title.text.toString(), description.text.toString(), id = taskId))
                } else {
                    dataSource.insertTask(Task(title.text.toString(), description.text.toString()))
                }
                withContext(Dispatchers.Main) {
                    findNavController().navigateUp()
                }
            }
        }
    }
}