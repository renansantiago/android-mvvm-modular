package br.com.renansantiago.domain.model

/**
 * Created by Renan Santiago on 31/01/19.
 */
data class Movie(
    val id: Long,
    val title: String,
    val rate: Int,
    val posterPath: String,
    val favorite: Boolean
) {

    fun getImageUrl() = "https://image.tmdb.org/t/p/w500$posterPath"
}