package com.wedoapps.CricketLiveLine.Ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wedoapps.CricketLiveLine.Model.*
import com.wedoapps.CricketLiveLine.Model.Info.Info
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchData
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionData
import com.wedoapps.CricketLiveLine.Repository.CricketGuruRepository
import kotlinx.coroutines.Dispatchers
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

    private fun saveMatchBet(
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

    fun getMatchByName(id: String, playerName: String, matchBet: MatchBet) =
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getMatchByName(playerName)
//            if (data.matchId == id || data.matchId!!.isNotEmpty()) {
            if (data == null) {
                saveMatchBet(id, playerName, mutableListOf(matchBet))
            } else {
                data.matchBet?.add(matchBet)
                updateMatchBet(
                    data.id!!,
                    data.matchId.toString(),
                    data.playerName.toString(),
                    data.matchBet!!
                )
            }
            /*} else if (data.matchId.isNullOrEmpty()) {
                saveMatchBet(id, playerName, mutableListOf(matchBet))
            }*/
        }

    fun updateMatchBetItem(id: String, playerName: String, matchBet: MatchBet) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getMatchByName(playerName)
            val bet = data.matchBet!!
            for (i in bet.indices) {
                if (bet[i].id == id) {
                    data.matchBet!![i] = matchBet
                    updateMatchBet(
                        data.id!!,
                        data.matchId!!,
                        data.playerName!!,
                        data.matchBet!!
                    )
                }
            }
        }
    }

    private fun saveSession(
        matchId: String,
        playerName: String,
        sessionBet: MutableList<SessionBet>
    ) = viewModelScope.launch {
        val sessionData = SessionData(
            null,
            matchId,
            playerName,
            sessionBet
        )
        repository.insertSession(sessionData)
    }

    fun deleteSession(sessionData: SessionData) = viewModelScope.launch {
        repository.deleteSession(sessionData)
    }

    @SuppressLint("NewApi")
    fun deleteItemMatch(id: String, playerName: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getMatchByName(playerName)
            val bet = data.matchBet!!
            bet.removeIf { k -> k.id == id }
            updateMatchBet(data.id!!, data.matchId!!, data.playerName!!, bet)
        }
    }

    private fun updateSession(
        id: Int,
        matchId: String,
        playerName: String,
        sessionBet: MutableList<SessionBet>
    ) = viewModelScope.launch {
        val sessionData = SessionData(
            id,
            matchId,
            playerName,
            sessionBet
        )
        repository.updateSession(sessionData)
    }

    fun getAllSessions(sessionID: String) = repository.getAllSessionBet(sessionID)

    fun getSessionByName(id: String, playerName: String, sessionBet: SessionBet) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getSessionByName(playerName)
//            if (data.matchId == id) {
            if (data == null) {
                saveSession(id, playerName, mutableListOf(sessionBet))
            } else {
                data.sessionBet?.add(sessionBet)
                updateSession(data.id!!, data.sessionID!!, data.playerName!!, data.sessionBet!!)
            }
            /* } else {
                 saveSession(id, playerName, mutableListOf(sessionBet))
             }*/
        }
    }


    fun updateSessionItem(id: String, playerName: String, sessionBet: SessionBet) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getSessionByName(playerName)
            val bet = data.sessionBet!!
            for (i in bet.indices) {
                if (bet[i].id == id) {
                    data.sessionBet!![i] = sessionBet
                    updateSession(
                        data.id!!,
                        data.sessionID!!,
                        data.playerName!!,
                        data.sessionBet!!
                    )
                }
            }
        }
    }

    @SuppressLint("NewApi")
    fun deleteItemSession(id: String, playerName: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getSessionByName(playerName)
            val bet = data.sessionBet!!
            bet.removeIf { k -> k.id == id }
            updateSession(data.id!!, data.sessionID!!, data.playerName!!, bet)
        }
    }

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
}
