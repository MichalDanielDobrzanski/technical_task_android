package com.michal.technicaltask.domain.base

interface UseCase<ResultType : Any> {
    suspend fun execute(): ResultType
}