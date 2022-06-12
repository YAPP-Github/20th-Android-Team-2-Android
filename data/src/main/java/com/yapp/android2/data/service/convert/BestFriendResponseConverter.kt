package com.yapp.android2.data.service.convert

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.entity.base.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Converter

internal class BestFriendResponseConverter(
    private val gson: Gson,
    private val adapter: TypeAdapter<*>
) : Converter<ResponseBody, BaseResponse<*>> {

    override fun convert(value: ResponseBody): BaseResponse<*> {
        value.use {
            val reader = gson.newJsonReader(value.charStream())

            val result = adapter.read(reader) as BaseResponse<*>

            if (reader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }

            if (result.statusCode in 200..299) {
                return result
            }

            throw Exception()
        }
    }
}
