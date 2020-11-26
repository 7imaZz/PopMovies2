package com.example.popmovies2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.popmovies2.pojo.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class WatchlistDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao?


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WatchlistDb? = null

        fun getDatabase(context: Context): WatchlistDb {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WatchlistDb::class.java,
                    "watchlist_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}