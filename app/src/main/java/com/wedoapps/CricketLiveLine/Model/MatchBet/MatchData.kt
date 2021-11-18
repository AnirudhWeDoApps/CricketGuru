package com.wedoapps.CricketLiveLine.Model.MatchBet

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "MatchData"
)
@Parcelize
data class MatchData(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var matchId: String? = null,
    var playerName: String? = null,
    var matchBet: MutableList<MatchBet>? = null
) : Parcelable