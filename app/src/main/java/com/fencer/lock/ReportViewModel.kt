package com.fencer.lock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fencer.lock.date.DateStyle
import com.fencer.lock.date.DateUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**

@author  SZhua
Create at  2022/7/14
Description:

 */
const val  userPhone ="18363030195"
const val  userDeviceId = "271233455"

fun MutableMap<String,String>.addUserInfo(){
    this["userDevBean.telphone"] = userPhone
    this["userDevBean.deviceid"] = userDeviceId
}
fun <T> Observable<T>.transformAndroid() : Observable<T> {
    return  this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

class ReportViewModel :ViewModel(){

    val data = MutableLiveData<List<AutoBean>>()

    private  fun getInitStart() :String {
        val addDay = DateUtil.addDay(Date(), -1)
        val start =  DateUtil.DateToString(addDay, DateStyle.YYYY_MM_DD)
        return "$start 06:00"
    }
    private  fun getInitEnd():String{
        val start =  DateUtil.DateToString(Date(),DateStyle.YYYY_MM_DD)
        return "$start 06:00"
    }

    private val initStart = getInitStart()

    private   val initEnd = getInitEnd()

    fun loadData(){
        val params = mutableMapOf<String,String>()
        params.addUserInfo()
        params["yqHeaderBean.startTime"]= initStart
        //params["yqHeaderBean.startTime"]= "2022-04-30 00:00"
        params["yqHeaderBean.endTime"]= initEnd
        params["zdsx"] = "0"
        val subscribe = HomeMapApi.getInstance().queryZdList(params)
            .transformAndroid()
            .subscribe({
                data.postValue(it.list)
            }, {
                it.printStackTrace()
            })
    }


}