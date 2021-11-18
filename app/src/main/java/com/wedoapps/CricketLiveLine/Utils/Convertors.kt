package com.wedoapps.CricketLiveLine.Utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionData

class Convertors {
    @TypeConverter
    fun toBet(json: String): List<MatchBet> {
        val type = object : TypeToken<List<MatchBet>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(data: List<MatchBet>): String {
        val type = object : TypeToken<List<MatchBet>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toSessionBet(json: String): List<SessionBet> {
        val type = object : TypeToken<List<SessionBet>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toSessionJson(data: List<SessionBet>): String {
        val type = object : TypeToken<List<SessionBet>>() {}.type
        return Gson().toJson(data, type)
    }

    @TypeConverter
    fun toSessionData(json: String): List<SessionData> {
        val type = object : TypeToken<List<SessionData>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toSessionDataJson(data: List<SessionData>): String {
        val type = object : TypeToken<List<SessionData>>() {}.type
        return Gson().toJson(data, type)
    }
}