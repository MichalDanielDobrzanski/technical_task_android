package com.michal.technicaltask.domain.base

import io.reactivex.rxjava3.core.Observable

interface ObservableUseCase<ResultType : Any> {
    fun execute(): Observable<ResultType>
}