package com.co.wewin.utilities

import android.content.Context
import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.WeWinApplication.WeWinApplication

object StorageUtils2 {

    private val sharedPref=WeWinApplication.getInstance().getSharedPreferences(AppConstants.StorageUtilKeys.PREFERENCE_KEY,Context.MODE_PRIVATE)

    fun storeString(key: String, value: String){
        sharedPref.edit().putString(key,value).apply()
    }

    fun fetchString(key: String,defaultValue:String) : String? {
        return sharedPref.getString(key, defaultValue)
    }

    fun removeKey(key: String){
        sharedPref.edit().remove(key).apply()
    }
}