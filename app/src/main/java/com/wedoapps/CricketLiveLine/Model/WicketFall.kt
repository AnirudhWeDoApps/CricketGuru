package com.wedoapps.CricketLiveLine.Model

import com.wedoapps.CricketLiveLine.Utils.Constants.WICKET
import java.util.*

data class WicketFall(
    var id: String = generateID(),
    var name: String? = "",
    var over: String? = "",
    var score: String? = ""
) {
    companion object {
        private fun generateID(): String {
            return WICKET + UUID.randomUUID().toString()
        }
    }
}
