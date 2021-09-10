package com.wedoapps.CricketLiveLine.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wedoapps.CricketLiveLine.Utils.Constants.WICKET
import java.util.*

data class WicketFall(
    var id: String = generateID(),
    @SerializedName("Name")
    @Expose
    var Name: String? = "",
    @SerializedName("Over")
    @Expose
    var Over: String? = "",
    @SerializedName("Score")
    @Expose
    var Score: String? = ""
) {
    companion object {
        private fun generateID(): String {
            return WICKET + UUID.randomUUID().toString()
        }
    }
}
