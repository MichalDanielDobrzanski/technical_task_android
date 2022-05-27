package com.michal.technicaltask.domain.base

import io.reactivex.rxjava3.core.Completable

interface CompletableUseCase<ParamsType> {
    fun execute(params: ParamsType): Completable
}
