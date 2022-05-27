package com.michal.technicaltask.presentation.utils

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.internal.disposables.SequentialDisposable

operator fun SequentialDisposable.plusAssign(disposable: Disposable) {
    update(disposable)
}

fun sequentialDisposable(compositeDisposable: CompositeDisposable): Lazy<SequentialDisposable> = lazy {
    SequentialDisposable().also(compositeDisposable::add)
}
