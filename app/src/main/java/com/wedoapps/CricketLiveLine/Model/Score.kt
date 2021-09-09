package com.wedoapps.CricketLiveLine.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    var Over: String? = "",
    var Score: String? = ""
) : Parcelable