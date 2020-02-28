package com.zhutian.baselibrary.basehttp


import android.content.Context

import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragmentActivity

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 调度类
 */
object RxHelper {

    fun <T> observableIO2Main(context: Context): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            val observable = upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            composeContext(context, observable)
        }
    }
    private fun <T> composeContext(context: Context, observable: Observable<T>): ObservableSource<T> {
        return if (context is RxActivity) {
            observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
        } else if (context is RxFragmentActivity) {
            observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
        } else if (context is RxAppCompatActivity) {
            observable.compose(context.bindUntilEvent(ActivityEvent.DESTROY))
        } else {
            observable
        }
    }
}
