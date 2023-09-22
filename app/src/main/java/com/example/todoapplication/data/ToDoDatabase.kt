package com.example.todoapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao(): TasksDao
    companion object {
        private const val db_name = "Task.db"
        private var instance : ToDoDatabase? = null
        fun getInstance(context: Context): ToDoDatabase {
            synchronized(ToDoDatabase::class) {
                if(instance == null) {
                    instance = databaseBuilder(context, ToDoDatabase::class.java, db_name).build()
                }
                return instance!!
            }
        }
    }
}