package com.zhutian.baselibrary.model

import androidx.lifecycle.LiveData
import java.io.Serializable

class DemoMo :Serializable, LiveData<DemoMo>(){
    var desc:String?=null
    var imagePath:String?=null
    var title:String?=null
}