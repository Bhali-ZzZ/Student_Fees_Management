package com.example.midprojectsfm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddStudentFeesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student_fees)

        val name = findViewById<EditText>(R.id.update_name)
        val regNo = findViewById<EditText>(R.id.update_reg_no)
        val totalFees = findViewById<EditText>(R.id.update_total_fees)
        val feesPaid = findViewById<EditText>(R.id.update_fees_paid)
        val scholarship = findViewById<EditText>(R.id.update_student_scholarship)
        val btn = findViewById<Button>(R.id.update_student_btn)

        btn.setOnClickListener {
            // Validate input
            if (name.text.isEmpty() || regNo.text.isEmpty() || totalFees.text.isEmpty() ||
                feesPaid.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Parse totalFees and feesPaid to integers
            val totalFeesValue = totalFees.text.toString().toInt()
            val feesPaidValue = feesPaid.text.toString().toInt()

            // Check if feesPaid exceeds totalFees
            if (feesPaidValue > totalFeesValue) {
                Toast.makeText(this, "Paid fees cannot exceed total fees", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a new student data object
            val studentData = ActivityModel(
                name = name.text.toString(),
                regNo = regNo.text.toString().toInt(),
                totalFees = totalFeesValue,
                feesPaid = feesPaidValue,
                scholarship = scholarship.text.toString()
            )

            // Calculate dueFees automatically
            studentData.dueFees = studentData.totalFees - studentData.feesPaid

            val studentDatabase = StudentDatabase.getDatabase(this)

            lifecycleScope.launch {
                studentDatabase.activityDao().insertStudent(studentData)

                Toast.makeText(this@AddStudentFeesActivity, "Saved student data", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
