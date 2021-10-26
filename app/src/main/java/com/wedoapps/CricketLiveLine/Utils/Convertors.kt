package com.wedoapps.CricketLiveLine.Utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet

class Convertors {
    @TypeConverter
    fun toBet(json: String): List<MatchBet> {
        val type = object : TypeToken<List<MatchBet>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: List<MatchBet>): String {
        val type = object: TypeToken<List<MatchBet>>() {}.type
        return Gson().toJson(torrent, type)
    }
}