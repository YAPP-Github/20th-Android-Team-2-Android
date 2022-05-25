package com.yapp.android2.domain

interface UseCase<IN, Out> {
    suspend fun execute(params: IN): Out
}
