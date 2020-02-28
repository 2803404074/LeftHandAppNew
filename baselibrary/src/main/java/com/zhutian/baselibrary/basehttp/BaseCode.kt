package com.zhutian.baselibrary.basehttp

class BaseCode {
    companion object{
        val SUCCESS = 200//响应成功
        val ERR = 401//响应失败
        val AUTH_FAIL = 1000//鉴权失败(Token无效或未传递)
        val LACK_HEAD = 1001//请求头参数缺少(需要签名加密的请求头参数)
        val SIGNATURE_ERR = 200//签名效验失败
    }

}