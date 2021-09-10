package com.wedoapps.CricketLiveLine.Ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wedoapps.CricketLiveLine.Model.*
import com.wedoapps.CricketLiveLine.Model.Info.Info
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository

class CricketGuruViewModel(
    val app: Application,
    private val repository: CricketGuruRepository
) : ViewModel() {

    private var allMatch: LiveData<MutableList<HomeMatch>>? = null


    init {
        allMatch = repository.getMatch()
    }

    fun getAllMatch(): LiveData<MutableList<HomeMatch>>? {
        return allMatch
    }

    fun getAllTeam1(id: String): LiveData<Score> {
        return repository.team1(id)
    }

    fun getAllTeam2(id: String): LiveData<Score> {
        return repository.team2(id)
    }

    fun getTeam1Extras(id: String): LiveData<String> {
        return repository.extrasTeam1(id)
    }

    fun getTeam2Extras(id: String): LiveData<String> {
        return repository.extrasTeam2(id)
    }

    fun getScoreDetails1(id: String): LiveData<TeamScore> {
        return repository.getScore1(id)
    }

    fun getScoreDetails2(id: String): LiveData<TeamScore> {
        return repository.getScore2(id)
    }

    fun getSpecificIdDetail(id: String): LiveData<HomeMatch> {
        return repository.getSpecificMatchData(id)
    }

    fun getBowlerList1(id: String): LiveData<BowlerList> {
        return repository.getBowlerList1(id)
    }

    fun getBowlerList2(id: String): LiveData<BowlerList> {
        return repository.getBowlerList2(id)
    }

    fun getWicketList1(id: String): LiveData<AllWicketList> {
        return repository.getWicketList1(id)
    }

    fun getWicketList2(id: String): LiveData<AllWicketList> {
        return repository.getWicketList2(id)
    }

    fun getInfo(id: String): LiveData<Info> {
        return repository.getInfo(id)
    }
}