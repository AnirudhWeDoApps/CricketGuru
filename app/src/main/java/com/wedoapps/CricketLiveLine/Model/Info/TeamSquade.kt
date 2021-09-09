package com.wedoapps.CricketLiveLine.Model.Info

import com.wedoapps.CricketLiveLine.Utils.Constants
import java.util.*

data class TeamSquade(
    var id: String = generateID(),
    var Team2: String? = null,
    var Team1: String? = null
) {
    companion object {
        private fun generateID(): String {
            return Constants.PLAYER + UUID.randomUUID().toString()
        }
    }
}
