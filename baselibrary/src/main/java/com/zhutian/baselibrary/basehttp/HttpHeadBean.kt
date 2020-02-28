package com.zhutian.baselibrary.basehttp

import okhttp3.Headers
import okhttp3.RequestBody

class HttpHeadBean :Comparable<HttpHeadBean>{
    override fun compareTo(other: HttpHeadBean): Int {
        return 1
    }

    var heads:Headers?=null
    var body: RequestBody?=null
    constructor(heads:Headers,body: RequestBody){
        this.heads = heads
        this.body = body
    }
}