package com.xiaofu.commons

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Gson解析List时的扩展
 */
inline fun <reified T> Gson.fromJsonArray(json: String, clazz: Class<T>): ArrayList<T> {
    val listType = ParameterizedTypeImpl(ArrayList::class.java, arrayOf(clazz))
    return this.fromJson(json, listType)
}

class ParameterizedTypeImpl(private val raw: Class<*>, private val args: Array<Class<*>>) :
    ParameterizedType {

    override fun getActualTypeArguments(): Array<Class<*>> {
        return args
    }

    override fun getRawType(): Type {
        return raw
    }

    override fun getOwnerType(): Type? {
        return null
    }
}