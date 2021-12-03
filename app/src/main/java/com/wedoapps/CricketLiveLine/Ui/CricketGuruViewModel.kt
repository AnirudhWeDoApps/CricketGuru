package com.wedoapps.CricketLiveLine.Ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wedoapps.CricketLiveLine.Model.*
import com.wedoapps.CricketLiveLine.Model.Info.Info
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import com.wedoapps.CricketLiveLine.Utils.Constants.TAG
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

    fun saveMatchBet(matchBet: MatchBet) = viewModelScope.launch {
        repository.insertMatch(matchBet)
    }

    fun updateMatchBet(
        id: Int,
        matchId: String,
        rate: Int,
        amount: Int,
        type: String,
        team: String,
        default: Boolean,
        playerName: String,
        team1Value: Int,
        team2Value: Int,
        drawValue: Int,
    ) = viewModelScope.launch {
        val matchBet = MatchBet(
            id,
            matchId,
            rate,
            amount,
            type,
            team,
            default,
            playerName,
            team1Value,
            team2Value,
            drawValue
        )
        repository.updateMatch(matchBet)
    }

    fun deleteMatchBet(matchBet: MatchBet) = viewModelScope.launch {
        repository.deleteMatch(matchBet)
    }

    fun getAllMatchBet(matchId: String) = repository.getAllMatchBet(matchId)

    fun getAllSessionsList(sessionID: String) = repository.getAllSessionBetList(sessionID)

    fun matchBetNameList() = repository.matchBetNameList()

    fun sessionNameList() = repository.sessionNameList()


    fun insertMainSession(
        matchId: String,
        sessionName: String,
        selectedTeamName: String,
    ) = viewModelScope.launch {
        val mainSession = MainSession(
            null,
            matchId,
            sessionName,
            selectedTeamName,
        )
        repository.insertMainSession(mainSession)
    }

    fun deleteMainSession(mainSession: MainSession) =
        viewModelScope.launch {
            repository.deleteMainSession(mainSession)
        }

    fun updateMainSession(
        id: Int,
        matchId: String,
        sessionName: String,
        selectedTeamName: String,
    ) = viewModelScope.launch {
        val mainSession = MainSession(
            id,
            matchId,
            sessionName,
            selectedTeamName,
        )
        repository.updateMainSession(mainSession)
    }

    fun getAllMainSession(matchid: String) = repository.getAllMainSession(matchid)

    fun updateSessionBet(
        id: Int,
        sessionID: String,
        amount: Int,
        inning: Int,
        over: String,
        fandp: Int,
        yorn: Int,
        actualScore: Int,
        playerName: String,
        commission: Double
    ) = viewModelScope.launch {
        val sessionBet = SessionBet(
            id,
            sessionID,
            amount,
            inning,
            over,
            fandp,
            yorn,
            actualScore,
            playerName,
            commission
        )
        repository.updateSession(sessionBet)
    }

    fun deleteSessionBet(sessionBet: SessionBet) = viewModelScope.launch {
        repository.deleteSession(sessionBet)
    }

    fun completeSessionBet(sessionBet: SessionBet) = viewModelScope.launch {
        repository.insertSessionBet(sessionBet)
    }

    fun minMax(sessionID: String): ArrayList<Int> {
        var data = arrayListOf<Int>()
        viewModelScope.launch {
            val query =
                "SELECT MAX(actualScore), MIN(actualScore) FROM sessionBet WHERE sessionID = $sessionID"
            data = repository.getMinMaxSessionList(query)
        }
        Log.d(TAG, "minMax: $data")
        return data
    }
}
