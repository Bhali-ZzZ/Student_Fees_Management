package com.example.midprojectsfm

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class ActivityModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var regNo: Int,
    var totalFees: Int,
    var feesPaid: Int,
    var scholarship: String
) : Parcelable {
    var dueFees: Int = totalFees - feesPaid // Calculate dueFees based on totalFees and feesPaid

    init {
        // Ensure feesPaid does not exceed totalFees
        if (feesPaid > totalFees) {
            feesPaid = totalFees
        }
        dueFees = totalFees - feesPaid // Update dueFees accordingly
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
        dueFees = totalFees - feesPaid // Calculate dueFees from parcel data
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(regNo)
        parcel.writeInt(totalFees)
        parcel.writeInt(feesPaid)
        parcel.writeString(scholarship)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ActivityModel> {
        override fun createFromParcel(parcel: Parcel): ActivityModel {
            return ActivityModel(parcel)
        }

        override fun newArray(size: Int): Array<ActivityModel?> {
            return arrayOfNulls(size)
        }
    }

    constructor() :
            this(0, "", 0, 0, 0, "")
}
