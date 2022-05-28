package com.michal.technicaltask.domain.refresh

import com.michal.technicaltask.data.scheduler.SchedulerProvider
import com.michal.technicaltask.domain.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshContentUseCase @Inject constructor(
    private val schedulerProvider: SchedulerProvider
) : ObservableUseCase<Unit> {

    override fun execute(): Observable<Unit> {
        return Observable.interval(0L, INTERVAL_SECONDS, TimeUnit.SECONDS)
            .map {
                // to Unit
            }
            .observeOn(schedulerProvider.main)
    }
}

private const val INTERVAL_SECONDS = 5L