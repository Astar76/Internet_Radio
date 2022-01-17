package com.astar.myapplication.core

sealed class Result<out S, out E> {

    data class Success<out S>(val data: S) : Result<S, Nothing>()
    data class Error<out E>(val ex: E) : Result<Nothing, E>()
}