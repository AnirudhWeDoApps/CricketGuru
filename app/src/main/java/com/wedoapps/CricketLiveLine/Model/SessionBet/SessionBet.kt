package com.wedoapps.CricketLiveLine.Model.SessionBet

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "sessionBet"
)
@Parcelize
data class SessionBet(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var matchId: String? = "",
    var amount: Int? = null,
    var innings: Int? = null,
    var over: String? = "",
    var FandP: Int? = null,
    var YorN: String? = "",
    var actualScore: Int? = null,
    var playerName: String? = ""
) : Parcelable
