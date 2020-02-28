package com.zhutian.baselibrary.basehttp


import com.zhutian.baselibrary.projectmodel.BaseListMo
import com.zhutian.baselibrary.projectmodel.MessListMo


class RequestUtil :BaseNetWork() {

    companion object{

        //发送短信
        fun sendCode(needSing:Boolean,phone:String,observer: BaseObserver<ArrayList<Any>>) {
            setSubscribe(needSing,service.senCode(phone),observer)
        }

        //游客和用户注册，
        fun touristRegister(needSing:Boolean,needHead:Boolean,map:HashMap<String,Any>,observer: BaseObserver<Any>) {
            //游客注册不需要头部
            HttpLogInterceptor.isNeedHead = needHead

            setSubscribe(needSing,service.touristRegister(map),observer)
        }

        //用户登录
        fun userLogin(needSing:Boolean,map:HashMap<String,Any>,observer: BaseObserver<Any>) {
            setSubscribe(needSing,service.userLogin(map),observer)
        }

        //用户退出登录
        fun userLogOut(needSing:Boolean,observer: BaseObserver<Any>) {
            setSubscribe(needSing,service.userLogOut(),observer)
        }

        //重置密码
        fun resetPass(needSing:Boolean,map:HashMap<String,Any>,observer: BaseObserver<Any>) {
            setSubscribe(needSing,service.resetPass(map),observer)
        }

        //签到
        fun sign(needSing:Boolean,observer: BaseObserver<Any>) {
            setSubscribe(needSing,service.sign(),observer)
        }

        //消息通知列表
        fun messList(needSing:Boolean,page:Int,limit:Int,observer: BaseObserver<BaseListMo<MessListMo>>) {
            setSubscribe(needSing,service.messList(page,limit),observer)
        }

        //系统公告列表
        fun noticeList(needSing:Boolean,page:Int,limit:Int,observer: BaseObserver<BaseListMo<MessListMo>>) {
            setSubscribe(needSing,service.noticeList(page,limit),observer)
        }

        //系统公告详情
        fun noticeDetails(needSing:Boolean,uuid:String,observer: BaseObserver<MessListMo>) {
            setSubscribe(needSing,service.noticeDetails(uuid),observer)
        }

        //反馈操作
        fun feedBack(needSing:Boolean,option:String,content:String,observer: BaseObserver<ArrayList<Any>>) {
            setSubscribe(needSing,service.feedBack(option,content),observer)
        }


        //消息通知列表（自己的接口）
        fun messListForMyServer(observer: MyObserver<BaseListMo<MessListMo>>) {
            HttpLogInterceptor.isNeedHead = false
            setSubscribe(false,service.messListForMyServer(),observer)
        }



        //--------------------------首页--------------------------------------
        //首页最新影片
        fun homeNews(observer: BaseObserver<BaseListMo<Any>>) {
            setSubscribe(false,service.homeNews(),observer)
        }

        //首页猜你喜欢
        fun guessLike(observer: BaseObserver<BaseListMo<Any>>) {
            setSubscribe(false,service.guessLike(),observer)
        }

        //首页其他影片列表
        fun homeMovies(observer: BaseObserver<BaseListMo<Any>>) {
            setSubscribe(false,service.homeMovies(),observer)
        }
    }
}