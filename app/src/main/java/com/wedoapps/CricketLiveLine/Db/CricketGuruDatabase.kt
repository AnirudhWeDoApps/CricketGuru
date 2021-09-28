package com.wedoapps.CricketLiveLine.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchBet
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet

@Database(
    entities = [MatchBet::class, SessionBet::class],
    version = 5
)

abstract class CricketGuruDatabase : RoomDatabase() {

    abstract fun getCricketGuruDao(): CricketGuruDao

    companion object {

        @Volatile
        private var instance: CricketGuruDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CricketGuruDatabase::class.java,
                "CricketGuru.db"
            ).fallbackToDestructiveMigration()
                .build()


    }

}