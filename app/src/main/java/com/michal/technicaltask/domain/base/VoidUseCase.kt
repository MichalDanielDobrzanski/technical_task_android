package com.michal.technicaltask.domain.base

interface VoidUseCase<ParamsType> {
    suspend fun execute(params: ParamsType)
}