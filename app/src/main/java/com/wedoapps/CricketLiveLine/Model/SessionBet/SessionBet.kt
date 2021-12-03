package com.wedoapps.CricketLiveLine.Model.SessionBet

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Entity(
    tableName = "SessionBet"
)
@Parcelize
data class SessionBet(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var sessionID: String? = "",
    var amount: Int? = null,
    var rate: Int? = null,
    var over: String? = "",
    var FandP: Int? = null,
    var YorN: Int? = 0,
    @ColumnInfo(name = "actualScore")
    var actualScore: Int? = null,

    var playerName: String? = "",
    var commission: Double? = 0.0,
    var date: String? = getDateAndTime()
) : Parcelable {

    constructor(
        id: Int?,
        sessionID: String?,
        amount: Int?,
        rate: Int?,
        over: String?,
        FandP: Int?,
        YorN: Int?,
        actualScore: Int?,
        commission: Double?,
        playerName: String?,
        date: String?
    ) : this() {
        this.id = id
        this.sessionID = sessionID
        this.amount = amount
        this.rate = rate
        this.over = over
        this.FandP = FandP
        this.YorN = YorN
        this.actualScore = actualScore
        this.commission = commission
        this.playerName = playerName
        this.date = date
    }

    /*constructor() : this(
        id = null,
        sessionID = "",
        amount = null,
        rate = null,
        over = "",
        FandP = null,
        YorN = null,
        actualScore = null,
        commission = null,
        playerName = "",
        date = ""
    )*/

    companion object {
        @SuppressLint("SimpleDateFormat")
        private fun getDateAndTime(): String {
            val currentTime: Date = Calendar.getInstance().time
            val simpleFormat = SimpleDateFormat("dd MMM hh:mm")
            return simpleFormat.format(currentTime)
        }
    }
}
