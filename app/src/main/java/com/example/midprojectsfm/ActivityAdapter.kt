package com.example.midprojectsfm
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(
    private var dataList: ArrayList<ActivityModel>,
    private val context: Context,
    private val deleteStudent: (ActivityModel) -> Unit,
    private val updateStudent: (ActivityModel) -> Unit
) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = itemView.findViewById<TextView>(R.id.add_student_name)
        val regNo = itemView.findViewById<TextView>(R.id.reg_no)
        val totalFees = itemView.findViewById<TextView>(R.id.total_fees)
        val feesPaid = itemView.findViewById<TextView>(R.id.fees_paid)
        val dueFees = itemView.findViewById<TextView>(R.id.due_fees)
        val scholarship = itemView.findViewById<TextView>(R.id.scholarship)
        val payStatus = itemView.findViewById<TextView>(R.id.pay)
        val deleteBtn = itemView.findViewById<ImageView>(R.id.delete_btn)
        val editBtn = itemView.findViewById<ImageView>(R.id.edit_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = dataList[position]

        // Set text values
        holder.name.text = student.name
        holder.regNo.text = student.regNo.toString()
        holder.totalFees.text = student.totalFees.toString()
        holder.feesPaid.text = student.feesPaid.toString()
        holder.dueFees.text = student.dueFees.toString()
        holder.scholarship.text = student.scholarship

        // Check if feesPaid is equal to totalFees
        if (student.feesPaid >= student.totalFees) {
            holder.payStatus.text = "Paid"
            holder.payStatus.setTextColor(Color.parseColor("#009D06")) // Green color
        } else {
            holder.payStatus.text = "Unpaid"
            holder.payStatus.setTextColor(Color.parseColor("#FF0000")) // Red color
        }

        holder.deleteBtn.setOnClickListener {
            deleteStudent(student) // Call the delete function
        }

        holder.editBtn.setOnClickListener {

            val intent = Intent(context, UpdateStudentActivity::class.java)
            intent.putExtra("student", student)
            context.startActivity(intent)
        }
    }

//    for inserting student data
    fun updateData(newData: List<ActivityModel>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}
