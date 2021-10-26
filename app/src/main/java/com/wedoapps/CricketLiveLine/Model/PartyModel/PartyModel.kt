package com.wedoapps.CricketLiveLine.Model.PartyModel

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "PartyModel"
)

data class PartyModel(
    @PrimaryKey(autoGenerate = true)
    val partyID: Int? = null,
    val partyName: String? = "",
    var partyDate: String = getDateAndTime()
) {
    companion object {
        @SuppressLint("SimpleDateFormat")
        private fun getDateAndTime(): String {
            val currentTime: Date = Calendar.getInstance().time
            val simpleFormat = SimpleDateFormat("dd MMM hh:mm")
            return simpleFormat.format(currentTime)
        }
    }
}