package com.example.todoapplication.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapplication.R
import com.example.todoapplication.data.ToDoDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
    private fun navigateToAddEditFragment(taskId: String) {
        val bundle = bundleOf("taskId" to taskId)
        findNavController().navigate(R.id.action_detailFragment_to_addEditFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskId: String? = arguments?.getString("taskId")
        val dataSouce = ToDoDatabase.getInstance(requireContext()).taskDao()
        val title: TextView = view.findViewById(R.id.task_detail_title_text)
        val description: TextView = view.findViewById(R.id.task_detail_description_text)
        val complete: CheckBox = view.findViewById(R.id.task_detail_complete_checkbox)
        val editButton: FloatingActionButton = view.findViewById(R.id.edit_task_fab)
        if(taskId != null) {
            dataSouce.observeTaskById(taskId).observe(viewLifecycleOwner) {
                title.text = it.title
                description.text = it.description
                complete.isChecked = it.isCompleted
            }
            editButton.setOnClickListener {
                navigateToAddEditFragment(taskId)
            }
        }
    }
}