package com.zhutian.baselibrary.basehttp
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.zhutian.baselibrary.app.MyApplication
import com.zhutian.baselibrary.util.SPHelper
import okhttp3.*
import okio.Buffer
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.zhutian.baselibrary.util.DeviceUtil


class HttpLogInterceptor : Interceptor{
    /**
     * 签名规则：
     * 请求头和请求体合成一个对象，
     * 对合成后的对象字典排序，
     * 排序后的对象累加字符串，
     * 然后再累加时间戳和随机数
     * 最后md5(base64(hmac_sha1('排序累加后的字符串',salt)))
     */
    companion object{
        var isNeedS = false//是否需要签名
        var isNeedHead = true//是否需要头部
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (isNeedS){//需要头部，并且需要签名
            //解析请求头
            val g = Gson()

            //存放请求体和头部属性的集合
            var map = HashMap<String, String>()

            //获取请求体
            val requestBody = request.body
            val buffer = Buffer()
            requestBody?.let {
                it.writeTo(buffer)
                var charset = Charset.forName("UTF-8")
                val contentType = it.contentType()
                contentType?.let {
                    charset = it.charset(charset)
                }
                //获取请求体 --- 字符串的方式
                val paramsStr = buffer.readString(charset)
                val newsStr = paramsStr.replace("&",",")
                try {
                    map= g.fromJson("{$newsStr}",map.javaClass)
                }catch (e:JsonSyntaxException){

                }

            }

            //组装请求头
            map["token"] = SPHelper.get().getToken()
            map["version"] = DeviceUtil.getVersionName(MyApplication.instance)
            map["timestamp"] = System.currentTimeMillis().toString()
            map["nonce"] = getRandom(6)
            map["platform"] = "Android"


            //将map字典排序
            val keyset = map.keys
            val list = ArrayList(keyset)
            list.sort()

            //循环map的value，并进行拼接成一个字符串
            var str=""
            for (i in list.indices) {
                str+=map[list[i]].toString()
            }

            //再将str拼接时间戳和随机数,得到一个需要加密的字符串
            val needEncryptionStr = str+System.currentTimeMillis()+getRandom(6)


            //md5(base64(hmac_sha1('排序累加后的字符串',salt)))
            val hmacSha1Str = JiaMiUtil.hmacSha1Encrypt(needEncryptionStr,SPHelper.get().getSalt())
            val base64Str = JiaMiUtil.encodeBase64(hmacSha1Str)
            val md5Str = JiaMiUtil.getMD5(base64Str) //最终的签名
            map["signature"] = md5Str

            //开始添加头部
            val lastRequest = request.newBuilder()
            lastRequest.addHeader("token",map["token"]!! )
            lastRequest.addHeader("version",map["version"]!! )
            lastRequest.addHeader("timestamp",map["timestamp"]!! )
            lastRequest.addHeader("nonce",map["nonce"]!! )
            lastRequest.addHeader("platform",map["platform"]!! )
            lastRequest.addHeader("signature",map["signature"]!! )
            val requestBuild = lastRequest.build()

            val response = chain.proceed(requestBuild)
            return response

        }else if (isNeedHead){//需要头部
            //统一添加请求头
            val updateRequest = request.newBuilder()
                .header("token",SPHelper.get().getToken())
               /* .addHeader("version","api123")//Api版本
                .addHeader("timestamp","timestamp123")//时间戳
                .addHeader("nonce","nonce123")//6位随机数
                .addHeader("platform","platform123")//客户端平台*/
                .build()
            return chain.proceed(updateRequest)
        }else{
            isNeedHead = true
        }
        return chain.proceed(request)
    }

    private fun getRandom(len:Int):String{
        val r = Random()
        val sb =StringBuilder()
        for (i in 0 until len) {
            sb.append(r.nextInt(10))
        }
        return sb.toString()
    }

}