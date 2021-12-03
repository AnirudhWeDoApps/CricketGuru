package com.wedoapps.CricketLiveLine.Db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.MainSession
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet

@Dao
interface CricketGuruDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMatch(match: MatchBet)

    @Update
    suspend fun updateMatch(match: MatchBet)

    @Delete
    suspend fun deleteMatch(match: MatchBet)

    @Query("SELECT * from matchbet WHERE matchID = :matchid")
    fun getAllMatches(matchid: String): LiveData<List<MatchBet>>

    @Query("SELECT playerName from matchbet")
    fun getMatchNameList(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSessionBet(sessionBet: SessionBet)

    @Query("SELECT * FROM sessionbet where sessionID = :sessionID")
    fun getSessionBetList(sessionID: String): LiveData<List<SessionBet>>

    @Query("SELECT playerName FROM sessionbet")
    fun getSessionNameList(): LiveData<List<String>>

    @RawQuery
    fun getMinMaxSessionList(query: SupportSQLiteQuery): Cursor

    @Update
    suspend fun updateSession(sessionBet: SessionBet)

    @Delete
    suspend fun deleteSession(sessionBet: SessionBet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMainSession(mainSession: MainSession)

    @Update
    suspend fun updateMainSession(mainSession: MainSession)

    @Delete
    suspend fun deleteMainSession(mainSession: MainSession)

    @Query("SELECT * FROM mainSession where matchID = :matchID")
    fun getMainSession(matchID: String): LiveData<List<MainSession>>

}