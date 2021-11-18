package com.wedoapps.CricketLiveLine.Model.SessionBet

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "SessionData"
)

@Parcelize
data class SessionData(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var sessionID: String? = null,
    var playerName: String? = null,
    @ColumnInfo(name = "sessionBet")
    var sessionBet: MutableList<SessionBet>? = null
) : Parcelable