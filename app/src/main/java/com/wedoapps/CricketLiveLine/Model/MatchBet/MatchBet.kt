package com.wedoapps.CricketLiveLine.Model.MatchBet

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "MatchBet"
)

@Parcelize
data class  MatchBet(
    @PrimaryKey(autoGenerate = true)
    var id: String? = "",
    var matchID: String? = "",
    var row_type: Int? = 1,
    var rate: Int? = null,
    var amount: Int? = null,
    var type: String? = "",
    var team: String? = "",
    var default: Boolean? = false,
    var playerName: String? = "",
    var team1Value: Int? = null,
    var team2Value: Int? = null,
    var drawValue: Int? = null,
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
