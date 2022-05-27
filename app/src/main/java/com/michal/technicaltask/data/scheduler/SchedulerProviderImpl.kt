package com.michal.technicaltask.data.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {
    override val io: Scheduler = Schedulers.io()
    override val computation: Scheduler = Schedulers.computation()
    override val main: Scheduler = AndroidSchedulers.mainThread()
}
