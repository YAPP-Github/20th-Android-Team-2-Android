package com.yapp.android2.data.local

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.annotation.AnyThread
import androidx.core.content.edit
import com.google.gson.Gson
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.key.SHARED_PREFERENCE_KEY
import com.yapp.android2.domain.key.USER
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BestFriendSharedPreferenceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : SharedPreferenceProvider {

    private val preference: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override var user by SerializablePreference(
        preferences = preference,
        gson = gson,
        name = USER,
        defaultValue = User.EMPTY,
        clazz = User::class.java
    )

    override fun putString(key: String, value: String) {
        preference.edit(true) { putString(key, value) }
    }

    override fun putLong(key: String, value: Long) {
        preference.edit(true) { putLong(key, value) }
    }

    override fun putInt(key: String, value: Int) {
        preference.edit(true) { putInt(key, value) }
    }

    override fun putParcelable(key: String, value: Parcelable) {
        preference.edit(true) { putString(key, gson.toJson(value)) }
    }

    override fun getString(key: String): String {
        return preference.getString(key, "").toString()
    }
}

interface SharedPreferenceProvider {

    var user: User
    fun putString(key: String, value: String)
    fun putLong(key: String, value: Long)
    fun putInt(key: String, value: Int)
    fun putParcelable(key: String, value: Parcelable)

    fun getString(key: String): String
}

class SerializablePreference<T>(
    private val preferences: SharedPreferences,
    private val gson: Gson,
    private val name: String,
    private val defaultValue: T,
    private val clazz: Class<T>
) : ReadWriteProperty<Any, T> {

    @AnyThread
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val json = preferences.getString(name, null)
        return json?.let {
            gson.fromJson(it, clazz)
        } ?: defaultValue
    }

    @AnyThread
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val json = gson.toJson(value, clazz)
        preferences.edit {
            putString(name, json)
        }
    }
}
