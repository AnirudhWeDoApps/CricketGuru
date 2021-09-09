package com.wedoapps.CricketLiveLine.Model

import com.wedoapps.CricketLiveLine.Utils.Constants.REPO
import java.util.*

data class PlayerScore(
    var id: String? = generateID(),
    var ball: String? = "",
    var run: String? = "",
    var sixes: String? = "",
    var fours: String? = "",
    var otherInfo: String? = "",
    var name: String? = "",
    var sr: String? = ""
) {
    companion object {
        private fun generateID(): String {
            return REPO + UUID.randomUUID().toString()
        }
    }
}