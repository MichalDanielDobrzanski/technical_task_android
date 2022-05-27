package com.michal.technicaltask.utils

import com.michal.technicaltask.data.scheduler.SchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulerProvider : SchedulerProvider {
    override val io: Scheduler
        get() = Schedulers.trampoline()
    override val computation: Scheduler
        get() = Schedulers.trampoline()
    override val main: Scheduler
        get() = Schedulers.trampoline()
}