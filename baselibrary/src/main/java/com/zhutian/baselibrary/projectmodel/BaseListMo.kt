package com.zhutian.baselibrary.projectmodel
import java.io.Serializable

class BaseListMo<T> : Serializable {
    var total:Int?=0
    var items:MutableList<T>?=null
}