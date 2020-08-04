package br.com.renansantiago.data.remote

data class ResponseWrap<T>(val results: List<T> = arrayListOf())