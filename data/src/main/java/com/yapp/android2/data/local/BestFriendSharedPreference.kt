package com.yapp.android2.data.local

import android.content.Context
import androidx.core.content.edit
import com.yapp.android2.domain.key.SHARED_PREFERENCE_KEY

class BestFriendSharedPreferenceProviderImpl(context: Context): SharedPreferenceProvider {

    private val preference = context.getSharedPreferences(SHARED_PREFERENCE_KEY ,Context.MODE_PRIVATE)

    override fun putString(key: String, value: String) {
        preference.edit(true) { putString(key ,value) }
    }

    override fun putLong(key: String, value: Long) {
        preference.edit(true) { putLong(key ,value) }
    }

    override fun putInt(key: String, value: Int) {
        preference.edit(true) { putInt(key ,value) }
    }

}

interface SharedPreferenceProvider {

    fun putString(key: String, value: String)
    fun putLong(key: String, value: Long)
    fun putInt(key: String, value: Int)

}
