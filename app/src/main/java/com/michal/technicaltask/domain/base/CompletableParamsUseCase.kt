package com.michal.technicaltask.domain.base

import io.reactivex.rxjava3.core.Completable

interface CompletableParamsUseCase<ParamsType> {
    fun execute(params: ParamsType): Completable
}