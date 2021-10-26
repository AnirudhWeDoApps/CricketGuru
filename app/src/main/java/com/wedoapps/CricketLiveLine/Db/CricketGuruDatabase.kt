package com.wedoapps.CricketLiveLine.Db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wedoapps.CricketLiveLine.Model.MatchBet.MatchData
import com.wedoapps.CricketLiveLine.Model.SessionBet.SessionBet
import com.wedoapps.CricketLiveLine.Utils.Convertors

@Database(
    entities = [MatchData::class, SessionBet::class],
    version = 2
)

@TypeConverters(Convertors::class)
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