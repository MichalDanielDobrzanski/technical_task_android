package com.michal.technicaltask.domain.base

interface ParamsUseCase<ParamsType, ResultType : Any> {
    suspend fun execute(params: ParamsType): ResultType
}