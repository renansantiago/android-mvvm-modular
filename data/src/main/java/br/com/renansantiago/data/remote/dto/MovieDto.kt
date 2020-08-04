package br.com.renansantiago.data.remote.dto

import br.com.renansantiago.domain.model.Movie
import com.google.gson.annotations.SerializedName

/**
 * Created by Renan Santiago on 01/02/19.
 */
sealed class MovieDto {

    data class Response(
        val id: Long,
        val title: String,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("poster_path") val posterPath: String
    ) {

        fun toMovie(favorite: Boolean = false) = Movie(
            id,
            title,
            voteAverage.toInt(),
            posterPath,
            favorite
        )

    }
}