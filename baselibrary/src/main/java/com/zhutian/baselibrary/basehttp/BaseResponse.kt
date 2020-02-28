package com.zhutian.baselibrary.basehttp

import java.io.Serializable

class BaseResponse<T>:Serializable {
    var result:T?=null
    var code:Int = 0
    var msg:String?=null

}