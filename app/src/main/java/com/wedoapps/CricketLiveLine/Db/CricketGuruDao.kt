package com.wedoapps.CricketLiveLine.Db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchData
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionData

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
    suspend fun getMatchesByName(playerName: String): MatchData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSession(sessionData: SessionData)

    @Update
    suspend fun updateSession(sessionData: SessionData)

    @Delete
    suspend fun deleteSession(sessionData: SessionData)

    @Query("DELETE from sessiondata where sessionBet LIKE '%' || :id || '%'")
    suspend fun deleteSessionItem(id: String)

    @Query("SELECT * from sessiondata WHERE sessionID =:sessionID")
    fun getAllSessions(sessionID: String): LiveData<List<SessionData>>

    @Query("SELECT * from sessiondata WHERE playerName = :playerName")
    suspend fun getSessionByName(playerName: String): SessionData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMainSession(mainSession: MainSession)

    @Update
    suspend fun updateMainSession(mainSession: MainSession)

    @Delete
    suspend fun deleteMainSession(mainSession: MainSession)

    @Query("SELECT * FROM mainSession where matchID = :matchID")
    fun getMainSession(matchID: String): LiveData<List<MainSession>>
}