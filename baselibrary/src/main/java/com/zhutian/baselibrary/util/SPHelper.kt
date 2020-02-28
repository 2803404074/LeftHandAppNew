package com.zhutian.baselibrary.util

class SPHelper {
    private var token:String?=null
    private var salt:String?=null
    private var phone:String?=null

    companion object {
        private var retrofitManager : SPHelper? = null
            get() {
                if (field == null){
                    field = SPHelper()
                }
                return field
            }
        @Synchronized fun get():SPHelper{
            return retrofitManager!!
        }
    }

    fun setToken(token:String){
        this.token = token
        SPUtil.get().setToken(token)
    }
    fun getToken(): String {
        token?.let {
            return it
        }
        token = SPUtil.get().getToken() as String
        return token as String
    }


    fun setSalt(salt:String){
        this.salt = salt
        SPUtil.get().setSalt(salt)
    }
    fun getSalt(): String {
        salt?.let {
            return it
        }
        salt = SPUtil.get().getSalt() as String
        return salt as String
    }

    fun setPhone(phone:String){
        this.phone = phone
        SPUtil.get().setPhone(phone)
    }
    fun getPhone(): String {
        phone?.let {
            return it
        }
        phone = SPUtil.get().getPhone() as String
        return phone as String
    }

}