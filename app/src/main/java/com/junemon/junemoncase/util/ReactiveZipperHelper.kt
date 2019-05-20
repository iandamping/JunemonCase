package com.junemon.junemoncase.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3

/**
 *
Created by Ian Damping on 12/05/2019.
Github = https://github.com/iandamping
 */

/*Cannot accept and return null values, so make sure all source data give values !*/
inline fun <reified T1, reified T2, reified T3> obsWithTripleZip(
        source1: ObservableSource<T1>?,
        source2: ObservableSource<T2>?,
        source3: ObservableSource<T3>?
): Observable<Triple<T1?, T2?, T3?>>? =
        Observable.zip(source1, source2, source3, Function3<T1, T2, T3, Triple<T1, T2, T3>> { t1, t2, t3 ->
            Triple(t1, t2, t3)
        })

/*Cannot accept and return null values, so make sure all source data give values !*/
inline fun <reified T1, reified T2, reified T3> singleWithTripleZip(
        source1: SingleSource<T1>?,
        source2: SingleSource<T2>?,
        source3: SingleSource<T3>?
): Single<Triple<T1?, T2?, T3?>> =
        Single.zip(source1, source2, source3, Function3<T1, T2, T3, Triple<T1, T2, T3>> { t1, t2, t3 ->
            Triple(t1, t2, t3)
        })

/*Cannot accept and return null values, so make sure both source give values !*/
inline fun <reified T, reified U> obsWithSecondZip(
        sources1: ObservableSource<T>?,
        sources2: ObservableSource<U>?
): Observable<Pair<T?, U?>> =
        Observable.zip(sources1, sources2, BiFunction { t1, t2 -> Pair(t1, t2) })

/*Observerable executes helper*/
inline fun <reified T> CompositeDisposable.executes(
        obs: Observable<T>,
        crossinline onFailed: (Throwable) -> Unit,
        crossinline onSuccess: (T?) -> Unit
) {
    this.add(obs.observeOn(AndroidSchedulers.mainThread()).subscribe({
        onSuccess(it)
    }, {
        onFailed(it)
    }))
}


/*Live data only without viewModel and this one cannot accept and return null values, so make sure both source give values !*/
fun <A, B> zipLiveData(a: LiveData<A>?, b: LiveData<B>?): LiveData<Pair<A, B>> {
    return MediatorLiveData<Pair<A, B>>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            if (localLastA != null && localLastB != null)
                this.value = Pair(localLastA, localLastB)
        }

        a?.let { nonNullLiveDataA ->
            addSource(nonNullLiveDataA) {
                it?.let { nonNullA ->
                    lastA = nonNullA
                    update()
                }
            }
            b?.let { nonNullLiveDataB ->
                addSource(nonNullLiveDataB) {
                    it?.let { nonNullB ->
                        lastB = nonNullB
                        update()
                    }

                }
            }

        }

    }
}