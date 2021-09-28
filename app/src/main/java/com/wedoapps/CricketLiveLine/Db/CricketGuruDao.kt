package com.wedoapps.CricketLiveLine.Db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet

@Dao
interface CricketGuruDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMatch(match: MatchBet)

    @Update
    suspend fun updateMatch(match: MatchBet)

    @Delete
    suspend fun deleteMatch(match: MatchBet)

    @Query("SELECT * from matchBet")
    fun getAllMatches(): LiveData<List<MatchBet>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSession(sessionBet: SessionBet)

    @Update
    suspend fun updateSession(sessionBet: SessionBet)

    @Delete
    suspend fun deleteSession(sessionBet: SessionBet)

    @Query("SELECT * from sessionBet")
    fun getAllSessions(): LiveData<List<SessionBet>>
}