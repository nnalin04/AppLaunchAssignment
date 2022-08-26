package com.example.applaunchassignment.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.applaunchassignment.data.model.Admin

class Utils {

    companion object {
        private fun getSharedPreference(context: Context): SharedPreferences? {
            return context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        }

        fun addUserData(context: Context, admin: Admin) {
            val pref = getSharedPreference(context)
            val editor: SharedPreferences.Editor = pref!!.edit()
            editor.putString(Constants.USER_NAME_KEY, admin.userName)
            editor.putString(Constants.PASSWORD_KEY, admin.password)
            editor.putBoolean(Constants.LOGIN_STATUS_KEY, admin.loginStatus)
            editor.apply()
        }

        fun getCurrentUserStatus(context: Context): Admin {
            val pref = getSharedPreference(context)!!
            val userName = pref.getString(Constants.USER_NAME_KEY, null)
            val password = pref.getString(Constants.PASSWORD_KEY, null)
            val loginStatus = pref.getBoolean(Constants.LOGIN_STATUS_KEY, false)
            return Admin(userName, password, loginStatus)
        }

        fun editLoginStatus(loginStatus: Boolean, context: Context) {
            val pref = getSharedPreference(context)
            val editor: SharedPreferences.Editor = pref!!.edit()
            editor.putBoolean(Constants.LOGIN_STATUS_KEY, loginStatus)
            editor.apply()
        }
    }
}