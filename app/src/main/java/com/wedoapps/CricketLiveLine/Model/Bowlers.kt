package com.wedoapps.CricketLiveLine.Model

import com.wedoapps.CricketLiveLine.Utils.Constants.BOWLER
import java.util.*

data class Bowlers(
    var id: String = generateID(),
    var over: String? = "",
    var wicket: String? = "",
    var maiden: String? = "",
    var run: String? = "",
    var er: String? = "",
    var name: String? = ""
) {
    companion object {
        private fun generateID(): String {
            return BOWLER + UUID.randomUUID().toString()
        }
    }
}