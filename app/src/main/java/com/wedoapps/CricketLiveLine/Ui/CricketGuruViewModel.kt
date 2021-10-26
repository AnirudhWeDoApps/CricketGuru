package com.wedoapps.CricketLiveLine.Ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wedoapps.CricketLiveLine.Model.*
import com.wedoapps.CricketLiveLine.Model.Info.Info
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchData
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import kotlinx.coroutines.launch

class CricketGuruViewModel(
    val app: Application,
    private val repository: CricketGuruRepository
) : AndroidViewModel(app) {

    fun getAllMatch(): LiveData<MutableList<HomeMatch>> {
        return repository.getMatch()
    }

    fun getAllTeam1(id: String): LiveData<Score?> {
        return repository.team1(id)
    }

    fun getAllTeam2(id: String): LiveData<Score?> {
        return repository.team2(id)
    }

    fun getTeam1Extras(id: String): LiveData<String> {
        return repository.extrasTeam1(id)
    }

    fun getTeam2Extras(id: String): LiveData<String> {
        return repository.extrasTeam2(id)
    }

    fun getScoreDetails1(id: String): LiveData<MutableList<PlayerScore>> {
        return repository.getScore1(id)
    }

    fun getScoreDetails2(id: String): LiveData<MutableList<PlayerScore>> {
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

    fun getRunRate(id: String): LiveData<String> {
        return repository.getRunRate(id)
    }

    fun getBatsman1(id: String): LiveData<String> {
        return repository.getDisplayBatsman1Info(id)
    }

    fun getBatsman2(id: String): LiveData<String> {
        return repository.getDisplayBatsman2Info(id)
    }

    fun getBowlerInfo(id: String): LiveData<String> {
        return repository.getBowlerInfo(id)
    }

    fun getPartnership(id: String): LiveData<String> {
        return repository.getPartnershipInfo(id)
    }

    fun getBallXRun(id: String): LiveData<String> {
        return repository.getBallXRunInfo(id)
    }

    fun getSessionLambi(id: String): LiveData<String> {
        return repository.getSessionLambi(id)
    }

    fun getLambiBallXRun(id: String): LiveData<String> {
        return repository.getLambiBallXRun(id)
    }

    fun getLiveMatch(id: String): LiveData<String> {
        return repository.getLiveMatch(id)
    }

    fun getSession(id: String): LiveData<String> {
        return repository.getSession(id)
    }

    fun getLastBall(id: String): LiveData<String> {
        return repository.getLastBall(id)
    }

    fun getBallByBall(id: String): LiveData<String> {
        return repository.getBallByBall(id)
    }

    fun getOtherMessage(id: String): LiveData<String> {
        return repository.getOtherMessage(id)
    }

    fun getMatchRate(id: String): LiveData<String> {
        return repository.getMatchRate(id)
    }

    fun getFirstInnings(id: String): LiveData<String> {
        return repository.getFirstInnings(id)
    }

    fun saveMatchBet(
        matchId: String,
        playerName: String,
        matchBet: MutableList<MatchBet>
    ) = viewModelScope.launch {
        val matchData = MatchData(
            null,
            matchId,
            playerName,
            matchBet
        )
        repository.insertMatch(matchData)
    }

    fun deleteMatchBet(matchBet: MatchData) = viewModelScope.launch {
        repository.deleteMatch(matchBet)
    }

    fun updateMatchBet(
        id: Int,
        matchId: String,
        playerName: String,
        matchBet: MutableList<MatchBet>
    ) = viewModelScope.launch {
        val matchData = MatchData(
            id,
            matchId,
            playerName,
            matchBet
        )
        repository.updateMatch(matchData)
    }

    fun getAllMatchBet(matchId: String) = repository.getAllMatchBet(matchId)

    fun getMatchByName(playerName: String) = repository.getMatchByName(playerName)

    fun saveSession(
        matchId: String,
        amount: Int,
        innings: Int,
        over: String,
        fandp: Int,
        yorn: String,
        actualScore: Int,
        playerName: String
    ) = viewModelScope.launch {
        val sessionBet = SessionBet(
            null,
            matchId,
            amount,
            innings,
            over,
            fandp,
            yorn,
            actualScore,
            playerName
        )
        repository.insertSession(sessionBet)
    }

    fun deleteSession(sessionBet: SessionBet) = viewModelScope.launch {
        repository.deleteSession(sessionBet)
    }

    fun updateSession(
        id: Int,
        matchId: String,
        amount: Int,
        innings: Int,
        over: String,
        fandp: Int,
        yorn: String,
        actualScore: Int,
        playerName: String
    ) = viewModelScope.launch {
        val sessionBet = SessionBet(
            id,
            matchId,
            amount,
            innings,
            over,
            fandp,
            yorn,
            actualScore,
            playerName
        )
        repository.updateSession(sessionBet)
    }

    fun getAllSessions(matchId: String) = repository.getAllSessionBet(matchId)


}
