package com.example.smartalarmapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest

class MainViewModel : ViewModel() {
    fun getTimezonesList(): MutableList<String?> {
        val timeZonesList = TimeZonesList()
        return timeZonesList.getTimeZoneList(TimeZonesList.OffsetBase.UTC)?.toMutableList()
            ?: mutableListOf()
    }

    fun setAlarm(tz: String, h: Int, m: Int, context: Context) {
        val timeWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<SunRiseWorker>()
            .setInputData(createInputDataForWorker(tz, h, m)).build()
        WorkManager
            .getInstance(context)
            .enqueue(timeWorkRequest)
    }

    private fun createInputDataForWorker(
        tz: String,
        h: Int,
        m: Int
    ): Data {
        val builder = Data.Builder()
        builder.putString(SunRiseWorker.TZ_KEY, tz)
        builder.putInt(SunRiseWorker.HOURS_KEY, h)
        builder.putInt(SunRiseWorker.MINUTES_KEY, m)

        return builder.build()
    }
}