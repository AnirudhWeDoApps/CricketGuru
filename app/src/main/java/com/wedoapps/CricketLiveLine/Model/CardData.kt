package com.wedoapps.CricketLiveLine.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardData(
    val id: String,
    val homeMatch: HomeMatch,
    val team1: Score,
    val team2: Score
) : Parcelable
