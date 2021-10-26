package com.wedoapps.CricketLiveLine.Db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchData
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet

@Dao
interface CricketGuruDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMatch(match: MatchData)

    @Update
    suspend fun updateMatch(match: MatchData)

    @Delete
    suspend fun deleteMatch(match: MatchData)

    @Query("SELECT * from matchData WHERE matchID = :matchid")
    fun getAllMatches(matchid: String): LiveData<List<MatchData>>

    @Query("SELECT * from matchData WHERE playerName = :playerName")
    fun getMatchesByName(playerName: String): LiveData<MatchData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSession(sessionBet: SessionBet)

    @Update
    suspend fun updateSession(sessionBet: SessionBet)

    @Delete
    suspend fun deleteSession(sessionBet: SessionBet)

    @Query("SELECT * from sessionBet WHERE matchId =:matchid")
    fun getAllSessions(matchid: String): LiveData<List<SessionBet>>
}