package br.com.renansantiago.data.local.db.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovieEntity(
    @PrimaryKey val id: Long
)