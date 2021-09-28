package com.wedoapps.CricketLiveLine.Model.MatchBet

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "matchBet"
)

data class MatchBet(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var matchID: String? = "",
    var rate: Int? = null,
    var amount: Int? = null,
    var type: String? = "",
    var team: String? = "",
    var default: Boolean? = false,
    var playerName: String? = "",
    var team1Value: Int? = null,
    var team2Value: Int? = null,
    var date: String = getDateAndTime()
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
