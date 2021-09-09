package com.wedoapps.CricketLiveLine.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeMatch(
    var id: String = "",
    val MatchDetail: String = "",
    val CurrentDate: String = "",
    val MatchStatus: String = "",
    val Team2: String = "",
    val Team1: String = "",
    val MatchResult: String = "",
) : Parcelable

