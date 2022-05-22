package com.yapp.android2.data.service.convert

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class BestFriendResponseConvertFactory private constructor(
    private val gson: Gson
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return BestFriendResponseConverter(gson, gson.getAdapter(TypeToken.get(type)))
    }

    companion object {
        fun create(gson: Gson = Gson()): BestFriendResponseConvertFactory {
            return BestFriendResponseConvertFactory(gson)
        }
    }
}
