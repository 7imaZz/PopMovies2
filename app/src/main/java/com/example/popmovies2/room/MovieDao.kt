package com.example.popmovies2.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.popmovies2.pojo.Result

@Dao
interface MovieDao {

    @Insert
    fun insertMovie(movie: Result)

    @Query("DELETE FROM movies WHERE id=:id")
    fun deleteMovie(id: Int)

    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getAllMovies(): List<Result>
}