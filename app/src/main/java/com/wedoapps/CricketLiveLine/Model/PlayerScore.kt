package com.wedoapps.CricketLiveLine.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wedoapps.CricketLiveLine.Utils.Constants.REPO
import java.util.*

data class PlayerScore(
    var id: String = generateID(),
    @SerializedName("Ball")
    @Expose
    var Ball: String? = "",
    @SerializedName("Run")
    @Expose
    var Run: String? = "",
    @SerializedName("6s")
    @Expose
    var sixes: String? = "",
    @SerializedName("4s")
    @Expose
    var fours: String? = "",
    @SerializedName("OtherInfo")
    @Expose
    var OtherInfo: String? = "",
    @SerializedName("Name")
    @Expose
    var Name: String? = "",
    @SerializedName("SR")
    @Expose
    var SR: String? = ""
) {
    companion object {
        private fun generateID(): String {
            return REPO + UUID.randomUUID().toString()
        }
    }
}