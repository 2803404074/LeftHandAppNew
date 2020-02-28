package com.zhutian.baselibrary.basehttp


import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 统一返回数据处理
 */
abstract class BaseObserver<T>: Observer<BaseResponse<T>> {

    abstract fun onSuccess(result:T?)
    abstract fun onFailure(e: Throwable?,message:String?)

    override fun onNext(t: BaseResponse<T>) {
        when(t.code){
            BaseCode.SUCCESS->onSuccess(t.result)
            BaseCode.ERR->onFailure(null,t.msg)
            BaseCode.AUTH_FAIL->onFailure(null,t.msg)
            BaseCode.LACK_HEAD->onFailure(null,t.msg)
            else->onFailure(null,t.msg)//BaseCode.SIGNATURE_ERR
        }
    }

    override fun onError(e: Throwable) {
        onFailure(e,e.message)
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

}