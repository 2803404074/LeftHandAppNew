package com.zhutian.lefthand.comment.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class HideService :Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}