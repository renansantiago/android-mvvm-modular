package br.com.renansantiago.domain.exception

/**
 * Created by Renan Santiago on 31/01/19.
 */
class DefaultException(
    val code: String = "",
    override val message: String = "Unexpected Error"
) : Exception()