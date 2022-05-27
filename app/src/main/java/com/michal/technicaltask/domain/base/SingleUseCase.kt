package com.michal.technicaltask.domain.base

import io.reactivex.rxjava3.core.Single

interface SingleUseCase<ResultType : Any> {
    fun execute(): Single<ResultType>
}