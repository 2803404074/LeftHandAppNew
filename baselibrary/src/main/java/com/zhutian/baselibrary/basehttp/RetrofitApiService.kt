package com.zhutian.baselibrary.basehttp

import com.zhutian.baselibrary.projectmodel.BaseListMo
import com.zhutian.baselibrary.projectmodel.MessListMo
import io.reactivex.Observable
import retrofit2.http.*


interface RetrofitApiService {

    //-----------------------------------个人中心--------------------------
    /**
     * 游客和用户注册
     */
    @POST("api/app/auth/register")
    fun touristRegister(@Body map:HashMap<String, Any>): Observable<BaseResponse<Any>>

    /**
     * 发送短信
     */
    @POST("api/app/sms/send")
    @FormUrlEncoded
    fun senCode(@Field("phone")phone:String): Observable<BaseResponse<ArrayList<Any>>>

    /**
     * 用户登录
     */
    @POST("api/app/auth/login")
    fun userLogin(@Body map:HashMap<String, Any>): Observable<BaseResponse<Any>>

    /**
     * 用户退出登录
     */
    @POST("api/app/auth/logout")
    fun userLogOut(): Observable<BaseResponse<Any>>

    /**
     * 重置密码
     */
    @POST("api/app/auth/reset")
    fun resetPass(@Body map:HashMap<String, Any>): Observable<BaseResponse<Any>>


    /**
     * 签到
     */
    @POST("app/user/sign")
    fun sign(): Observable<BaseResponse<Any>>

    /**
     * 消息通知列表
     */
    @POST("api/app/user/message_list")
    @FormUrlEncoded
    fun messList(@Field("page")page:Int,@Field("limit")limit:Int): Observable<BaseResponse<BaseListMo<MessListMo>>>


    /**
     * 系统公告列表
     */
    @POST("api/app/system/notice_list")
    @FormUrlEncoded
    fun noticeList(@Field("page")page:Int,@Field("limit")limit:Int): Observable<BaseResponse<BaseListMo<MessListMo>>>

    /**
     * 系统公告详情
     */
    @POST("api/app/system/notice_info")
    @FormUrlEncoded
    fun noticeDetails(@Field("uuid")uuid:String): Observable<BaseResponse<MessListMo>>

    /**
     * 用户反馈操作
     */
    @POST("api/app/user/feedback_set")
    @FormUrlEncoded
    fun feedBack(@Field("option")option:String,@Field("content")content:String): Observable<BaseResponse<ArrayList<Any>>>
    
    /**
     * 消息通知列表(自己)
     */
    @POST("api/myList")
    fun messListForMyServer(): Observable<BaseResponse<BaseListMo<MessListMo>>>



    //-----------------------------------首页--------------------------

    /**
     * 首页最新影片
     */
    @POST("api/app/home/news")
    fun homeNews(): Observable<BaseResponse<BaseListMo<Any>>>


    /**
     * 首页猜你喜欢
     */
    @POST("api/app/home/prefers")
    fun guessLike(): Observable<BaseResponse<BaseListMo<Any>>>


    /**
     * 首页其他影片列表
     */
    @POST("api/app/home/data")
    fun homeMovies(): Observable<BaseResponse<BaseListMo<Any>>>
}