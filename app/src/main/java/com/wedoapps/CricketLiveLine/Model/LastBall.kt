package com.wedoapps.CricketLiveLine.Model

import com.wedoapps.CricketLiveLine.Utils.Constants.BALLS
import java.util.*


data class LastBall(
    val id: String = generateId(),
    val number: String? = ""
) {
    companion object {
        private fun generateId(): String {
            return BALLS + UUID.randomUUID().toString()
        }
    }
}
