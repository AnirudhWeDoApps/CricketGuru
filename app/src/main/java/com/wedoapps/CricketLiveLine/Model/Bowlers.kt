package com.wedoapps.CricketLiveLine.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wedoapps.CricketLiveLine.Utils.Constants.BOWLER
import java.util.*

data class Bowlers(
    var id: String = generateID(),
    @SerializedName("Over")
    @Expose
    var Over: String? = "",
    @SerializedName("Wicket")
    @Expose
    var Wicket: String? = "",
    @SerializedName("Maiden")
    @Expose
    var Maiden: String? = "",
    @SerializedName("Run")
    @Expose
    var Run: String? = "",
    @SerializedName("ER")
    @Expose
    var ER: String? = "",
    @SerializedName("Name")
    @Expose
    var Name: String? = ""
) {
    companion object {
        private fun generateID(): String {
            return BOWLER + UUID.randomUUID().toString()
        }
    }
}