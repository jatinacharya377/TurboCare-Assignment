package com.example.turbocareassignment.repository.sharedpreferences

import android.content.Context

class BackButtonVisibilityPreference(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun isFromVehicleListActivity(): Boolean {

        return sharedPreferences.getBoolean("value", false)
    }

    fun setPreference(value: Boolean) {

        sharedPreferences.edit().putBoolean("value", value).apply()
    }
}