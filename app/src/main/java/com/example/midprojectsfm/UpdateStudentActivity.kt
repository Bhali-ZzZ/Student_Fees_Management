package com.example.midprojectsfm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UpdateStudentActivity : AppCompatActivity() {

    private lateinit var student: ActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        val nameEdit = findViewById<EditText>(R.id.update_name)
        val regNoEdit = findViewById<EditText>(R.id.update_reg_no)
        val totalFeesEdit = findViewById<EditText>(R.id.update_total_fees)
        val feesPaidEdit = findViewById<EditText>(R.id.update_fees_paid)
        val scholarshipEdit = findViewById<EditText>(R.id.update_student_scholarship)
        val updateButton = findViewById<Button>(R.id.update_student_btn)

        student = intent.getParcelableExtra("student") ?: return
        nameEdit.setText(student.name)
        regNoEdit.setText(student.regNo.toString())
        totalFeesEdit.setText(student.totalFees.toString())
        feesPaidEdit.setText(student.feesPaid.toString())
        scholarshipEdit.setText(student.scholarship)

        updateButton.setOnClickListener {
            // Validate input fields
            if (nameEdit.text.isEmpty() || regNoEdit.text.isEmpty() || totalFeesEdit.text.isEmpty() ||
                feesPaidEdit.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Parse totalFees and feesPaid to integers
            val totalFeesValue = totalFeesEdit.text.toString().toInt()
            val feesPaidValue = feesPaidEdit.text.toString().toInt()

            // Check if feesPaid exceeds totalFees
            if (feesPaidValue > totalFeesValue) {
                Toast.makeText(this, "Paid fees cannot exceed total fees", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update student fields
            student.name = nameEdit.text.toString()
            student.regNo = regNoEdit.text.toString().toInt()
            student.totalFees = totalFeesValue
            student.feesPaid = feesPaidValue
            // Automatically calculate dueFees
            student.dueFees = student.totalFees - student.feesPaid
            student.scholarship = scholarshipEdit.text.toString()

            // Update in the database
            updateStudent(student)
            Toast.makeText(this, "Information Updated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateStudent(student: ActivityModel) {
        lifecycleScope.launch {
            StudentDatabase.getDatabase(this@UpdateStudentActivity).activityDao()
                .updateStudent(student)

            // Prepare result intent
            val resultIntent = Intent()
            resultIntent.putExtra("updated_student", student)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close this activity and return to MainStudentInfo
        }
    }
}
