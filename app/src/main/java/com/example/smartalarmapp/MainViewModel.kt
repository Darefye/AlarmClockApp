package com.example.smartalarmapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.work.Data

class MainViewModel(var timeZonesList: TimeZonesList) : ViewModel() {

    fun getTimezonesList(): ArrayList<String> {
        return timeZonesList.get()
    }
//    fun setAlarm(tz:String,h:Int,m:Int){
//
//        val timeWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<SunRiseWorker>()
//            .setInputData(createInputDataForWorker(tz,h,m)).build()
//        WorkManager
//            .getInstance(context)
//            .enqueue(timeWorkRequest)
//    }
//    private fun createInputDataForWorker(tz:String,
//                                         h:Int,
//                                         m:Int): Data {
//        val builder = Data.Builder()
//        builder.putString(SunRiseWorker.TZ_KEY,tz)
//        builder.putInt(SunRiseWorker.HOURS_KEY, h)
//        builder.putInt(SunRiseWorker.MINUTES_KEY, m)
//
//        return builder.build()
//    }
}