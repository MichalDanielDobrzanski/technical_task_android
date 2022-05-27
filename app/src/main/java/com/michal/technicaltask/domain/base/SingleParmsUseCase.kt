package com.michal.technicaltask.domain.base

import io.reactivex.rxjava3.core.Single

interface SingleParamsUseCase<ParamsType, ResultType : Any> {
    fun execute(params: ParamsType): Single<ResultType>
}