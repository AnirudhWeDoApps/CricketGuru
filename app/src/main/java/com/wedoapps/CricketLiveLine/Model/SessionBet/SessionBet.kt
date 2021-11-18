package com.wedoapps.CricketLiveLine.Model.SessionBet

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity
@Parcelize
data class SessionBet(
    @PrimaryKey(autoGenerate = true)
    var id: String? = "",
    var matchID: String? = "",
    var row_type: Int? = 1,
    var amount: Int? = null,
    var innings: Int? = null,
    var over: String? = "",
    var FandP: Int? = null,
    var YorN: String? = "",
    var actualScore: Int? = null,
    var playerName: String? = "",
    var commission: Int? = 0,
    var date: String = getDateAndTime()
) : Parcelable {

    companion object {
        @SuppressLint("SimpleDateFormat")
        private fun getDateAndTime(): String {
            val currentTime: Date = Calendar.getInstance().time
            val simpleFormat = SimpleDateFormat("dd MMM hh:mm")
            return simpleFormat.format(currentTime)
        }
    }
}
