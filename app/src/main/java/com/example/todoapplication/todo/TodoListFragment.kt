package com.example.todoapplication.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.data.ToDoDatabase
import com.example.todoapplication.todo.recycler.TodoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }
    private fun navigatetoDetailFragment(taskId: String) {
        val bundle = bundleOf("taskId" to taskId)
        findNavController().navigate(R.id.action_todoListFragment_to_detailFragment, bundle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSource = ToDoDatabase.getInstance(requireContext()).taskDao()
        val recyclerView = view.findViewById<RecyclerView>(R.id.tasks_list)
        val addButton = view.findViewById<View>(R.id.add_task_fab)
        dataSource.observeTasks().observe(viewLifecycleOwner) { tasks ->
            if(tasks.isNotEmpty()) {
                recyclerView.adapter = TodoAdapter(tasks, {
                    navigatetoDetailFragment(it.id)
                }, {
                    lifecycleScope.launch(Dispatchers.IO) {
                        dataSource.deleteTaskById(it.id)
                    }
                }, {
                    lifecycleScope.launch(Dispatchers.IO) {
                        dataSource.updateCompleted(it.id, it.isCompleted)
                    }
                })
            }
        }
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_addEditFragment)
        }
    }
}