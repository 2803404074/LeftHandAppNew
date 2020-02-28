package com.zhutian.baselibrary.projectmodel

class FeedBackMo {
    var isCheck = false
    var mTitle:String?=null

    constructor(isCheck:Boolean,mTitle:String){
        this.isCheck = isCheck
        this.mTitle = mTitle
    }
}