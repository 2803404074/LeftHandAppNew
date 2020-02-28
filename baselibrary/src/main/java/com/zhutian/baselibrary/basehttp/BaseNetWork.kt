package com.zhutian.baselibrary.basehttp

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


open class BaseNetWork{
    companion object{
        var service = RetrofitManager.get().createService(RetrofitApiService::class.java)
        fun <T> setSubscribe(needSing:Boolean,observable: Observable<T>, observer: Observer<T>) {
            HttpLogInterceptor.isNeedS = needSing
            observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer)
        }
    }
}