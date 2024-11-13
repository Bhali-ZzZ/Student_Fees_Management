package com.example.midprojectsfm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainStudentInfo : AppCompatActivity() {

    private lateinit var adapter: ActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_student_info)

        val btn = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Initialize the adapter with both delete and edit functions
        adapter = ActivityAdapter(
            ArrayList(),
            this,
            deleteStudent = { student -> deleteStudent(student) },
            updateStudent = { student -> updateStudent(student) }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btn.setOnClickListener {
            val intent = Intent(this, AddStudentFeesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchStudents()
    }

    private fun fetchStudents() {
        lifecycleScope.launch {
            val students = StudentDatabase.getDatabase(this@MainStudentInfo).activityDao().getAllStudents()
            adapter.updateData(students)
        }
    }

    private fun deleteStudent(student: ActivityModel) {
        lifecycleScope.launch {
            StudentDatabase.getDatabase(this@MainStudentInfo).activityDao().deleteStudent(student)
            fetchStudents()
        }
    }

    private fun updateStudent(student: ActivityModel) {
        val intent = Intent(this, UpdateStudentActivity::class.java)
        intent.putExtra("student", student) // Pass the student object to edit
        startActivity(intent)
    }
}
