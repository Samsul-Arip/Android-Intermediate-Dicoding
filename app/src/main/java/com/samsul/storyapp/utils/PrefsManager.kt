package com.samsul.storyapp.utils

import android.content.Context
import android.content.SharedPreferences
import hu.autsoft.krate.Krate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.stringPref

class PrefsManager(context: Context) : Krate {
    override val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences("prefs_manager", Context.MODE_PRIVATE)

    var exampleBoolean by booleanPref().withDefault(false)
    var token by stringPref().withDefault("")

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}